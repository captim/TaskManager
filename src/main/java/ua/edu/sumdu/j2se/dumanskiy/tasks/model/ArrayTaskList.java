package ua.edu.sumdu.j2se.dumanskiy.tasks.model;

import java.util.Arrays;
import java.util.Objects;

public class ArrayTaskList extends AbstractTaskList {

    private final int INIT_SIZE = 16;
    private Task[] tasks = new Task[INIT_SIZE];
    private int length;

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException();
        }
        if (length == tasks.length - 1) {
            resize(tasks.length * 2);
        }
        tasks[length++] = task;
    }

    private void resize(int length) {
        Task[] newTasks = new Task[length];
        System.arraycopy(tasks, 0, newTasks, 0, this.length);
        tasks = newTasks;
    }

    @Override
    public boolean remove(Task task) {
        for (int i = 0; i < length; i++) {
            if (task.equals(tasks[i])) {
                for (int j = i; j < length - 1; j++) {
                    tasks[j] = tasks[j + 1];
                }
                tasks[length - 1] = null;
                length--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public Task getTask(int index) {
        if (length < index) {
            throw new IndexOutOfBoundsException();
        }
        return tasks[index];
    }

    @Override
    public Object clone() {
        ArrayTaskList clonedObj = (ArrayTaskList) super.clone();
        clonedObj.tasks = new Task[length];
        for (int i = 0; i < length; i++) {
            clonedObj.tasks[i] = this.tasks[i].clone();
        }
        return clonedObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrayTaskList that = (ArrayTaskList) o;
        return INIT_SIZE == that.INIT_SIZE
                && length == that.length
                && Arrays.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(INIT_SIZE, length);
        result = 31 * result + Arrays.hashCode(tasks);
        return result;
    }

}
