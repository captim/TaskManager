package ua.edu.sumdu.j2se.dumanskiy.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Tasks;
import ua.edu.sumdu.j2se.dumanskiy.tasks.view.NotificationView;
import java.time.LocalDateTime;
import java.util.*;

public class NotificationController extends Thread {

    AbstractTaskList model;
    NotificationView view;
    final static Logger logger = Logger.getLogger(NotificationController.class);
    public NotificationController(AbstractTaskList model) {
        this.model = model;
        view = new NotificationView();
    }

    public AbstractTaskList getModel() {
        return model;
    }

    public void setModel(AbstractTaskList model) {
        this.model = model;
    }

    public NotificationView getView() {
        return view;
    }

    public void setView(NotificationView view) {
        this.view = view;
    }

    @Override
    public void run() {

        do {
            while (model.size() == 0) {
                logger.debug("Tasks for notification have not been found");
                try {
                    sleep(1000 * 55);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.debug("Searching tasks for notification");
            SortedMap tasks =
                    Tasks.calendar(model, LocalDateTime.now(),
                            LocalDateTime.now().plusMinutes(1));
            if (!tasks.isEmpty()) {
                logger.debug("Tasks for notification were found");
                try {
                    logger.debug("Waiting for start of next minute");
                    sleep((60 - LocalDateTime.now().getSecond()) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                view.printNotification(tasks);
                tasks = null;
            } else {
                logger.debug("Tasks for notification was not found");
                try {
                    sleep(50 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (true);
    }
}