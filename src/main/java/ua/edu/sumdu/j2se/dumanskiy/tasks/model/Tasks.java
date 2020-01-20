package ua.edu.sumdu.j2se.dumanskiy.tasks.model;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {

    public static Iterable<Task> incoming(
                    Iterable<Task> tasks, LocalDateTime start,
                    LocalDateTime end) {
        Iterator<Task> iterator = tasks.iterator();
        do {
            try {
                if (iterator.next().nextTimeAfter(start).isAfter(end)) {
                    iterator.remove();
                }
            } catch (NullPointerException e) {
                iterator.remove();
            }
        } while (iterator.hasNext());
        return tasks;
    }

    public static SortedMap<LocalDateTime,
            Set<Task>> calendar(
                    Iterable<Task> tasks, LocalDateTime start,
                    LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> dateSetSortedMap = new TreeMap<>();
        ArrayTaskList arrayTaskList = new ArrayTaskList();
        Iterator<Task> iterator = tasks.iterator();
        do {
            arrayTaskList.add(iterator.next());
        } while (iterator.hasNext());
        for (LocalDateTime i = start; i.isBefore(end); i = i.plusMinutes(1)) {
            Set<Task> taskSet = new HashSet<>();
            ArrayTaskList newArrayTaskList =
                    (ArrayTaskList) arrayTaskList.incoming(i, i.plusMinutes(1));
            for (int j = 0; j < newArrayTaskList.size(); j++) {
                taskSet.add(newArrayTaskList.getTask(j));
            }
            if (!taskSet.isEmpty()) {
                dateSetSortedMap.put(newArrayTaskList.
                        getTask(0).nextTimeAfter(i), taskSet);
            }
        }
        return dateSetSortedMap;
    }

}
