package ua.edu.sumdu.j2se.dumanskiy.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Task;
import ua.edu.sumdu.j2se.dumanskiy.tasks.view.AddTaskView;

import java.io.IOException;

public class AddTaskController {
    private AbstractTaskList model;
    private AddTaskView view;
    final static Logger logger = Logger.getLogger(AddTaskController.class);
    public AbstractTaskList getModel() {
        return model;
    }

    public void setModel(AbstractTaskList model) {
        this.model = model;
    }

    public AddTaskView getView() {
        return view;
    }

    public void setView(AddTaskView view) {
        this.view = view;
    }

    public AddTaskController(AbstractTaskList model) {
        this.model = model;
        view = new AddTaskView();
    }
    public void addTask() throws IOException {
        logger.debug("User going to add new task to task list");
        Task newTask = view.createTask();
        model.add(newTask);
        logger.info("Task \"" + newTask.getTitle()
                + "\" was added to task list");
        new MainController().writeModelInJson(model);
    }
}
