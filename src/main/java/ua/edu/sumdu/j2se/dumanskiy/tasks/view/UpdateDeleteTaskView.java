package ua.edu.sumdu.j2se.dumanskiy.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.Main;
import ua.edu.sumdu.j2se.dumanskiy.tasks.controller.ListActions;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Task;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UpdateDeleteTaskView {
    Logger logger = Logger.getLogger(UpdateDeleteTaskView.class);
    public ListActions.actions selectAction() {
        ListActions.actions action;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("What do you want to do:\n1) Update\n2) Delete");
            System.out.println("Select action: ");
            int intAction = scanner.nextInt();
            if (intAction == 1) {
                action = ListActions.actions.UPDATE_TASK;
            } else if (intAction == 2) {
                action = ListActions.actions.DELETE_TASK;
            } else {
                logger.error(Main.invalidData + "Expected: 1 or 2; Actual: "
                        + intAction + ";");
                return selectAction();
            }
        } catch (InputMismatchException e) {
            logger.error(Main.invalidData + "Expected: 1 or 2; Actual: "
                    + scanner.nextLine() + ";");
            return selectAction();
        }
        logger.debug("User choose action: " + action);
        return action;
    }
    public int selectTaskFor(AbstractTaskList taskList) {
        Scanner scanner = new Scanner(System.in);
        try {
            new TaskListView().taskListInfo(taskList);
            System.out.println("Select task (index): ");
            int index = scanner.nextInt();
            if (taskList.getTask(index - 1) == null) {
                logger.error(Main.invalidData + "Task list does not have "
                        + index + "-th task;");
                return selectTaskFor(taskList);
            }
            logger.debug("User choose index of task");
            return index;
        } catch (IndexOutOfBoundsException
                | InputMismatchException | NullPointerException e) {
            logger.error(Main.invalidData
                    + "Expected integer; Actual: " + scanner.nextLine() + ";");
            return selectTaskFor(taskList);
        }
    }
    public int selectAttributeToUpdate(Task task) {
        new TaskView().taskInfo(task);
        Scanner scanner = new Scanner(System.in);
        int attribute;
        if (task.isRepeated()) {
            do {
                System.out.println("Select number of attribute: ");
                attribute = scanner.nextInt();
                if (attribute <= 0 || attribute > 6) {
                    logger.error(Main.invalidData + "Task does not have "
                            + attribute + "-th attribute;");
                }
            } while (attribute <= 0 || attribute > 6);
        } else {
            do {
                System.out.println("Select number of attribute: ");
                attribute = scanner.nextInt();
                if (attribute <= 0 || attribute > 4) {
                    logger.error(Main.invalidData + "Task does not have "
                            + attribute + "-th attribute;");
                }
            } while (attribute <= 0 || attribute > 4);
        }
        logger.debug("User choose number of attribute (attribute = "
                + attribute + ")");
        return attribute;
    }
    public void updateTask(Task task, int attribute) {
        Scanner scanner = new Scanner(System.in);
        TaskView taskView = new TaskView();
        if (attribute == 1) {
            taskView.setTitle(task);
        } else if (attribute == 2) {
            taskView.changeActivity(task);
        } else if (attribute == 3) {
            taskView.changeIsRepeat(task);
        } else if (task.isRepeated()) {
            if (attribute == 4) {
                taskView.setStartTime(task);
            } else if (attribute == 5) {
                taskView.setEndTime(task);
            } else if (attribute == 6) {
                taskView.setInterval(task);
            }
        } else {
            task.setTime(taskView.setStartTime(task));
        }
        logger.debug("Attribute was updated");
    }

    public void deleteTask(Task task) {
        new TaskView().taskInfo(task);
        logger.info("Task was removed");
    }
}
