package ua.edu.sumdu.j2se.dumanskiy.tasks.view;

import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Task;

public class AddTaskView {
    public Task createTask() {
        return new TaskView().createTask();
    }
}
