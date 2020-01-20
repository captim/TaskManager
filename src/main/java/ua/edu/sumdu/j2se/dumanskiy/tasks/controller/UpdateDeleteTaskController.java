package ua.edu.sumdu.j2se.dumanskiy.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.Task;
import ua.edu.sumdu.j2se.dumanskiy.tasks.view.TaskListView;
import ua.edu.sumdu.j2se.dumanskiy.tasks.view.UpdateDeleteTaskView;

import java.io.IOException;

public class UpdateDeleteTaskController {
    private AbstractTaskList model;
    private UpdateDeleteTaskView view;
    final static Logger logger
            = Logger.getLogger(UpdateDeleteTaskController.class);

    public AbstractTaskList getModel() {
        return model;
    }

    public void setModel(AbstractTaskList model) {
        this.model = model;
    }

    public UpdateDeleteTaskView getView() {
        return view;
    }

    public void setView(UpdateDeleteTaskView view) {
        this.view = view;
    }

    public UpdateDeleteTaskController(AbstractTaskList model) {
        this.model = model;
        view = new UpdateDeleteTaskView();
    }
    public void updateDeleteTask() throws IOException {
        logger.debug("User going to update or delete task from task list");
        if (model.size() == 0) {
            new TaskListView().modelHasNotTasks();
            return;
        }
        ListActions.actions action = view.selectAction();
        if (action == ListActions.actions.DELETE_TASK) {
            logger.debug("User has choose to delete task");
        } else {
            logger.debug("User has choose to update task");
        }
        int taskIndex = view.selectTaskFor(model);
        logger.debug("User has choose " + taskIndex + " task");
        Task task = model.getTask(taskIndex - 1);
        if (action == ListActions.actions.UPDATE_TASK) {
            int numOfAtribute = view.selectAttributeToUpdate(task);
            logger.debug("User has choose " + numOfAtribute + " attribute");
            view.updateTask(task, numOfAtribute);
            logger.info("User has update task \"" + task.getTitle() + "\"");
        } else {
            view.deleteTask(task);
            model.remove(task);
            logger.info("User has deleted task \"" + task.getTitle() + "\"");
        }
        new MainController().writeModelInJson(model);
    }
}