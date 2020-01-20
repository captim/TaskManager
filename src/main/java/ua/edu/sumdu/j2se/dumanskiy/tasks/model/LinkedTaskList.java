package ua.edu.sumdu.j2se.dumanskiy.tasks.model;

import java.util.Objects;

public class LinkedTaskList extends AbstractTaskList implements Cloneable {
    private Node first;
    private Node last;
    private class Node implements Cloneable {
        private Task task;
        private Node next;
        private Node previous;
        private Node(Task task) {
            this.task = task;
            if (first == null) {
                first = this;
            } else {
                previous = last;
                previous.next = this;
            }
            last = this;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Node clonedObj = (Node) super.clone();
            if (this.next == null) {
                clonedObj.next = null;
            } else {
                clonedObj.next = (Node) this.next.clone();
            }
            clonedObj.task = (Task) this.task.clone();
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
            Node node = (Node) o;
            return Objects.equals(task, node.task)
                    && Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task, next, previous);
        }
    }

    @Override
    public void add(Task task) {
        Node node = new Node(task);
    }

    @Override
    public boolean remove(Task task) {
        Node temp = first;
        do {
            if (task == temp.task) {
                if (temp == first) {
                    first = first.next;
                    return true;
                }
                if (temp == last) {
                    last = last.previous;
                    last.next = null;
                    return true;
                }
                temp.previous.next = temp.next;
                temp.next.previous = temp.previous;
                return true;
            }
            temp = temp.next;
        } while (temp != null);
        return false;
    }

    @Override
    public Task getTask(int index) {
        Node temp = first;
        if (temp == null) {
            return null;
        }
        int tempLength = 0;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
            if (temp == null) {
                return null;
            }
        }
        return temp.task;
    }

    @Override
    public int size() {
        if (first == null) {
            return 0;
        }
        Node temp = first;
        int i;
        for (i = 0; temp != null; i++) {
            temp = temp.next;
        }
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkedTaskList that = (LinkedTaskList) o;
        return Objects.equals(first, that.first);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }

    @Override
    public Object clone() {
        LinkedTaskList clonedObj = (LinkedTaskList) super.clone();
        try {
            clonedObj.first = (Node) first.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
        try {
            clonedObj.last = (Node) last.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
        return clonedObj;
    }
}