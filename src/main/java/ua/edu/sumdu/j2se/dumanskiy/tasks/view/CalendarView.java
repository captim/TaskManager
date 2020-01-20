package ua.edu.sumdu.j2se.dumanskiy.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Task;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class CalendarView {
    final static Logger logger = Logger.getLogger(CalendarView.class);
    public LocalDateTime getStartTime() {
        System.out.println("Start:");
        return new TaskView().setLocalDateTime();
    }

    public LocalDateTime getEndTime() {
        System.out.println("End:");
        return new TaskView().setLocalDateTime();
    }
    public void printCalendar(SortedMap<LocalDateTime, Set<Task>> map) {
        new TaskListView().printSortedMap(map);
        logger.info("Calendar was printed");
    }
}