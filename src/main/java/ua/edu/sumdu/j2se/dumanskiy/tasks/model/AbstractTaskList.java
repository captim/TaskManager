package ua.edu.sumdu.j2se.dumanskiy.tasks.model;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import static ua.edu.sumdu.j2se.dumanskiy.tasks.model.ListTypes.types.*;

public abstract class AbstractTaskList
        implements Iterable, Cloneable, Serializable {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public abstract Task getTask(int index);
    public final AbstractTaskList incoming(
            LocalDateTime from, LocalDateTime to) {
        AbstractTaskList abstractTaskList;
        if ("LinkedTaskList".equals((this.getClass()).getSimpleName())) {
            abstractTaskList = TaskListFactory.createTaskList(LINKED);
        } else {
            abstractTaskList = TaskListFactory.createTaskList(ARRAY);
        }
        this.getStream()
                .filter(x -> x.nextTimeAfter(from) != null)
                .filter(x -> x.nextTimeAfter(from).isEqual(to)
                        || x.nextTimeAfter(from).isBefore(to))
                .forEach(abstractTaskList::add);
        return abstractTaskList;
    }

    public Stream<Task> getStream() {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            taskArrayList.add(this.getTask(i));
        }
        return taskArrayList.stream();
    }

    @Override
    protected Object clone() {
        try {
            return (AbstractTaskList) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public  Iterator iterator() {
        if (this.size() == 0) {
            return null;
        }
        return new TaskIterator();
    }

    public class TaskIterator implements Iterator {
        private int iterator = -1;
        private int lastRet;
        @Override
        public boolean hasNext() {
            try {
                return AbstractTaskList.this.getTask(iterator + 1) != null;
            } catch (NullPointerException e) {
                return false;
            }
        }

        @Override
        public Task next() {
            iterator++;
            return AbstractTaskList.this.getTask(lastRet++);
        }

        @Override
        public void remove() {
            if (iterator == -1) {
                throw new IllegalStateException();
            }
            AbstractTaskList.this
                    .remove(AbstractTaskList.this.getTask(iterator));
            lastRet--;
            iterator--;
        }
    }
}
