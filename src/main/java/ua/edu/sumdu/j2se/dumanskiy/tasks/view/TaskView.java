package ua.edu.sumdu.j2se.dumanskiy.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.Main;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Task;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskView {
    private Scanner scanner = new Scanner(System.in);
    final static Logger logger = Logger.getLogger(TaskView.class);

    public Task createTask() {
        String title = setTitle(null);
        boolean isRepeat = setIsRepeats(null);
        Task task;
        LocalDateTime start;
        LocalDateTime end;
        if (isRepeat) {
            do {
                start = setStartTime(null);
                end = setEndTime(null);
                if (start.isAfter(end)) {
                    logger.error(Main.invalidData
                            + "Start time is after end time;");
                }
            } while (start.isAfter(end));
            int interval = setInterval(null);
            task = new Task(title, start, end, interval);
        } else {
            LocalDateTime time = setStartTime(null);
            task = new Task(title, time);
        }
        logger.info("Created new task (title = \"" + task.getTitle() + "\")");
        return task;
    }

    public void taskInfo(Task task) {
        System.out.println("Title: " + task.getTitle()
                + "\nActive: " + task.isActive()
                + "\nRepeat: " + task.isRepeated());
        if (task.isRepeated()) {
            System.out.print("Start: ");
            localDateTimeInfo(task.getStartTime());
            System.out.print("End: ");
            localDateTimeInfo(task.getEndTime());
            System.out.println("Interval(in minutes): "
                    + task.getRepeatInterval());
        } else {
            System.out.print("Start: ");
            localDateTimeInfo(task.getTime());
        }
        logger.debug("Info about task was printed (title = \""
                + task.getTitle() + "\")");
    }

    public void localDateTimeInfo(LocalDateTime time) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println(time.format(formatter));
    }

    public String setTitle(Task task) {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        if (task != null) {
            task.setTitle(title);
        }
        return title;
    }

    public boolean setIsRepeats(Task task) {
        Boolean isRepeats = null;
        do {
            System.out.println("Is task repeats (print \"+\" or \"-\")");
            String isRepeatsStr = scanner.nextLine();
            if (isRepeatsStr.equals("+")) {
                isRepeats = true;
                if (task != null) {
                    task.setTime(setStartTime(task),
                            setEndTime(task), setInterval(task));
                }
            } else if (isRepeatsStr.equals("-")) {
                isRepeats = false;
                if (task != null) {
                    task.setTime(setStartTime(task));
                }
            }
            if (isRepeats == null) {
                logger.error(Main.invalidData
                        + "Expected: + or -; Actual: " + isRepeatsStr + ";");
            }
        } while (isRepeats == null);
        return isRepeats;
    }

    public void changeActivity(Task task) {
        if (task.isActive()) {
            task.setActive(false);
        } else {
            task.setActive(true);
        }
        logger.info("Changed activity for " + task.getTitle());
    }

    public void changeIsRepeat(Task task) {
        if (!task.isRepeated()) {
            LocalDateTime start = setStartTime(null);
            LocalDateTime end = setEndTime(null);
            int interval = setInterval(null);
            if (start.isAfter(end)) {
                logger.error(Main.invalidData
                        + "Start time is after end time;");
                changeIsRepeat(task);
            } else {
                task.setTime(start, end, interval);
            }
        } else {
            task.setTime(setStartTime(task));
        }
        logger.info("Repeat data was changed for " + task.getTitle());
    }

    public LocalDateTime setStartTime(Task task) {
        System.out.println("When does task start:");
        LocalDateTime startTime;
        do {
            startTime = setLocalDateTime();
            if (startTime.isBefore(LocalDateTime.now())) {
                logger.error(Main.invalidData
                        + "Cannot create task with this start time;");
            }
        } while (startTime.isBefore(LocalDateTime.now()));
        if (task != null) {
            if (task.isRepeated()) {
                task.setStartTime(startTime);
            } else {
                task.setTime(startTime);
            }
        }
        return startTime;
    }

    public LocalDateTime setEndTime(Task task) {
        System.out.println("When does task end:");
        LocalDateTime endTime;
        do {
            endTime = setLocalDateTime();
            if (endTime.isBefore(LocalDateTime.now())) {
                logger.error(Main.invalidData
                        + "Cannot create task with this start time;");
            }
        } while (endTime.isBefore(LocalDateTime.now()));
        if (task != null) {
            if (task.isRepeated() && task.getStartTime().isAfter(endTime)) {
                logger.error(Main.invalidData
                        + "Start time is after end time;");
                endTime = setEndTime(task);
            } else if (task.isRepeated()) {
                task.setEndTime(endTime);
            }
        }
        return endTime;
    }

    public int setInterval(Task task) {
        System.out.print("Which does interval(in minutes): ");
        int interval = scanner.nextInt();
        if (interval < 1) {
            logger.error(Main.invalidData + "Interval less then 1;");
            return setInterval(task);
        }
        if (task != null) {
            if (task.isRepeated()) {
                task.setRepeatInterval(interval);
            }
        }
        return interval;
    }

    public void setActivity(Task task) {
        Boolean isActive = null;
        do {
            System.out.println("Does task active (print \"+\" or \"-\"):");
            String isActiveStr = scanner.nextLine();
            if (isActiveStr.equals("+")) {
                isActive = true;
            } else if (isActiveStr.equals("-")) {
                isActive = false;
            }
        } while (isActive == null);
        task.setActive(isActive);
    }

    public LocalDateTime setLocalDateTime() {
        int year;
        int month;
        int day;
        int hour;
        int minute;
        try {
            System.out.print("Year: ");
            year = scanner.nextInt();
            System.out.print("Month: ");
            month = scanner.nextInt();
            System.out.print("Day: ");
            day = scanner.nextInt();
            System.out.print("Hour: ");
            hour = scanner.nextInt();
            System.out.print("Minute: ");
            minute = scanner.nextInt();
            return LocalDateTime.of(year, month, day, hour, minute);
        } catch (InputMismatchException e) {
            logger.error(Main.invalidData
                    + "Expected integer; Actual: " + scanner.nextLine() + ";");
            return setLocalDateTime();
        } catch (DateTimeException e) {
            logger.error(Main.invalidData + "Cannot create this date;");
            return setLocalDateTime();
        }
    }
}
