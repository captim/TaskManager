package ua.edu.sumdu.j2se.dumanskiy.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.dumanskiy.tasks.Main;
import ua.edu.sumdu.j2se.dumanskiy.tasks.controller.ListActions.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    final static Logger logger = Logger.getLogger(MainView.class);
        public void printMenu() {
            System.out.println("TASK MANAGER\n"
                    + "1. Add task;\n"
                    + "2. Update\\delete task;\n"
                    + "3. Task list;\n"
                    + "4. Calendar;\n"
                    + "5. Exit");
            logger.debug("Menu was printed");
        }
        public actions nextAction() {
            Scanner scanner = new Scanner(System.in);
            actions action = null;
            int ordAction = 0;
            try {
                ordAction = scanner.nextInt();
                action = actions.values()[ordAction];
            } catch (InputMismatchException e) {
                logger.error(Main.invalidData + "Expected: 1 - 5; Actual: "
                        + scanner.nextLine() + ";");
                printMenu();
                action = nextAction();
            }
            if (action == null) {
                printMenu();
                return nextAction();
            }
            return action;
        }
}
