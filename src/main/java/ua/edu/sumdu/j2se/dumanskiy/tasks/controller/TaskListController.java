package ua.edu.sumdu.j2se.dumanskiy.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.dumanskiy.tasks.view.TaskListView;

public class TaskListController {
    private AbstractTaskList model;
    private TaskListView view;
    final static Logger logger = Logger.getLogger(TaskListController.class);
    public AbstractTaskList getModel() {
        return model;
    }

    public void setModel(AbstractTaskList model) {
        this.model = model;
    }

    public TaskListView getView() {
        return view;
    }

    public void setView(TaskListView view) {
        this.view = view;
    }

    public TaskListController(AbstractTaskList model) {
        this.model = model;
        view = new TaskListView();
    }
    public void printTaskList() {
        if (model.size() != 0) {
            view.taskListInfo(model);
        } else {
            view.modelHasNotTasks();
        }
        logger.info("User has printed task list");
    }
}