package ua.edu.sumdu.j2se.dumanskiy.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

public class NotificationView {
    final static Logger logger = Logger.getLogger(NotificationView.class);
    public void printNotification(SortedMap<LocalDateTime, Set<Task>> tasks) {
        Iterator<Set<Task>> setIterator = tasks.values().iterator();
        Iterator<LocalDateTime> timeIterator = tasks.keySet().iterator();
        LocalDateTime current = timeIterator.next();
        Iterator<Task> iterator = setIterator.next().iterator();
        do {
            System.out.print("Notification!!! At "
                    + current.format(DateTimeFormatter.ofPattern("HH:mm"))
                    + " starts ");
            String title = iterator.next().getTitle();
            System.out.println(title);
            logger.info("Notification about " + title + " was printed");
        } while (iterator.hasNext());
    }
}
