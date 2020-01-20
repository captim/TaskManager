package ua.edu.sumdu.j2se.dumanskiy.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.Main;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.dumanskiy.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.dumanskiy.tasks.view.MainView;

import java.io.*;

public class MainController {
    final static Logger logger = Logger.getLogger(MainController.class);
    private AbstractTaskList model;
    private MainView view;
    public MainController() {
        model = new ArrayTaskList();
        view = new MainView();
    }

    public AbstractTaskList getModel() {
        return model;
    }

    public void setModel(AbstractTaskList model) {
        this.model = model;
    }

    public MainView getView() {
        return view;
    }

    public void setView(MainView view) {
        this.view = view;
    }
    public void programStarts() throws IOException {
        readModelFromJson(model);
        NotificationController notification = new NotificationController(model);
        notification.setDaemon(true);
        notification.start();
        logger.info("Program has been started");
        selectAction();
    }
    public void selectAction() throws IOException {
        view.printMenu();
        ListActions.actions action = view.nextAction();
        logger.debug("User has printed next action: " + action);
        switch (action) {
            case ADD_TASK:
                new AddTaskController(model).addTask();
                break;
            case UPDATE_DELETE_TASK:
                new UpdateDeleteTaskController(model).updateDeleteTask();
                break;
            case TASK_LIST:
                new TaskListController(model).printTaskList();
                break;
            case CALENDAR:
                new CalendarController(model).calendar();
                break;
            case END:
                programEnds();
            default:
                selectAction();
                break;
        }
        selectAction();
    }
    public void programEnds() throws IOException {
        writeModelInJson(model);
        logger.info("Program has been shut down");
        System.exit(0);
    }
    public void readModelFromJson(AbstractTaskList model) throws IOException {
        File jsonFile = new File(Main.jsonFile);
        if (jsonFile.exists()) {
            logger.debug("Json file has been found");
            Reader reader = new FileReader(jsonFile);
            TaskIO.read(model, reader);
            logger.debug("Task list has been written from json file");
            logger.info("Program has been started");
        } else {
            logger.debug("Json file has not been found");
        }
    }
    public void writeModelInJson(AbstractTaskList model) throws IOException {
        File jsonFile = new File(Main.jsonFile);
        //jsonFile.createNewFile();
        Writer writer = new FileWriter(jsonFile);
        if (model.size() == 0) {
            writer.flush();
        } else {
            TaskIO.write(model, writer);
        }
        logger.debug("Json file was written");
    }
}
