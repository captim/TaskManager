package ua.edu.sumdu.j2se.dumanskiy.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Tasks;
import ua.edu.sumdu.j2se.dumanskiy.tasks.view.CalendarView;
import ua.edu.sumdu.j2se.dumanskiy.tasks.view.TaskListView;
import java.time.LocalDateTime;
import java.util.SortedMap;

public class CalendarController {
    private AbstractTaskList model;
    private CalendarView view;
    final static Logger logger = Logger.getLogger(CalendarController.class);
    public CalendarController(AbstractTaskList model) {
        this.model = model;
        view = new CalendarView();
    }

    public AbstractTaskList getModel() {
        return model;
    }

    public void setModel(AbstractTaskList model) {
        this.model = model;
    }

    public CalendarView getView() {
        return view;
    }

    public void setView(CalendarView view) {
        this.view = view;
    }

    public void calendar() {
        logger.debug("User going to get calendar");
        if (model.size() == 0) {
            new TaskListView().modelHasNotTasks();
            return;
        }
        LocalDateTime start = view.getStartTime();
        logger.debug("Got start time for calendar");
        LocalDateTime end = view.getEndTime();
        logger.debug("Got end time for calendar");
        SortedMap sortedMap
                = Tasks.calendar(model, start, end);
        logger.debug("Calendar data was successfully received");
        view.printCalendar(sortedMap);
    }
}
