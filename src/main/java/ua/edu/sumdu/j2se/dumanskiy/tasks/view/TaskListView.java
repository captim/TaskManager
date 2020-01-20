package ua.edu.sumdu.j2se.dumanskiy.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Task;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public class TaskListView {
    Logger logger = Logger.getLogger(TaskListView.class);
    public void taskListInfo(AbstractTaskList taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            System.out.print((i + 1) + ") ");
            new TaskView().taskInfo(taskList.getTask(i));
            System.out.println();
        }
        logger.debug("Info about task list was printed");
    }

    public void modelHasNotTasks() {
        logger.debug("Task list is empty");
        System.out.println("Task list is empty!");
    }

    public void printSortedMap(SortedMap<LocalDateTime,
            Set<Task>> map) {
        Iterator<Set<Task>> viterator = map.values().iterator();
        try {
            do {
                Set<Task> tasks = viterator.next();
                Iterator<Task> taskIterator = tasks.iterator();
                do {
                    new TaskView().taskInfo(taskIterator.next());
                } while (taskIterator.hasNext());
            } while (viterator.hasNext());
            logger.debug("Sorted map was printed");
        } catch (NoSuchElementException e) {
            logger.debug("There are no tasks");
            System.out.println("There are no tasks!");
        }
    }
}
