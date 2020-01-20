package ua.edu.sumdu.j2se.dumanskiy.tasks;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.controller.MainController;

import java.io.IOException;

public class Main {
    public static String invalidData = "User has put invalid data; ";
    public static final String jsonFile = "taskData.json";
    final static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        logger.debug("Program has been activated");
        MainController mainController = new MainController();
        mainController.programStarts();
    }
}