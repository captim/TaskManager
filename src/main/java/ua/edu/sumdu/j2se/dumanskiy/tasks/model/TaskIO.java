package ua.edu.sumdu.j2se.dumanskiy.tasks.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Iterator;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out)
            throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeInt(tasks.size());
        Iterator<Task> iterator = tasks.iterator();
        do {
            Task task = iterator.next();
            dataOutputStream.writeInt(task.getTitle().length());
            dataOutputStream.writeChars(task.getTitle());
            dataOutputStream.writeBoolean(task.isRepeated());
            dataOutputStream.writeInt(task.getRepeatInterval());
            if (task.isRepeated()) {
                dataOutputStream.writeInt(task.getStartTime().getNano());
                dataOutputStream.writeInt(task.getEndTime().getNano());
            } else {
                dataOutputStream.writeInt(task.getTime().getNano());
            }
        } while (iterator.hasNext());
    }

    public static void read(AbstractTaskList tasks, InputStream in)
            throws IOException, ClassNotFoundException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int length = dataInputStream.readInt();
        String title;
        boolean activity;
        int interval;
        int startTime = 0;
        int endTime = 0;
        int time = 0;
        for (int i = 0; i < length; i++) {
            int lengthOfTitle = dataInputStream.readInt();
            byte[] forTitle = new byte[lengthOfTitle * 2];
            for (int j = 0; j < lengthOfTitle * 2; j++) {
                forTitle[j] = dataInputStream.readByte();
            }
            title = new String(forTitle, "UTF-16");
            activity = dataInputStream.readBoolean();
            interval = dataInputStream.readInt();
            if (interval != 0) {
                startTime = dataInputStream.readInt();
                endTime = dataInputStream.readInt();
            } else {
                time = dataInputStream.readInt();
            }
            Task task;
            if (time != 0) {
                task = new Task(title, LocalDateTime.now().withNano(time));
                task.setActive(activity);
            } else {
                task = new Task(title, LocalDateTime.now().withNano(startTime),
                        LocalDateTime.now().withNano(endTime), interval);
            }
            tasks.add(task);
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file)
            throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        TaskIO.write(tasks, outputStream);
    }
    public static void readBinary(AbstractTaskList tasks, File file)
            throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        TaskIO.read(tasks, fileInputStream);
    }
    public static void write(AbstractTaskList tasks, Writer out)
            throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
                .create();
        Iterator<Task> iterator = tasks.iterator();
        Task[] arrTasks = new Task[tasks.size()];
        int i = 0;
        do {
            arrTasks[i++] = iterator.next();
        } while (iterator.hasNext());
        gson.toJson(arrTasks, out);
        out.close();
    }

    public static void read(AbstractTaskList tasks, Reader in)
            throws IOException {
        Gson gson = new  GsonBuilder().setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd hh:mm:ss.S").create();
        try {
            Task[] arrayTasks = gson.fromJson(in, Task[].class);
            for (Task task : arrayTasks) {
                tasks.add(task);
            }
        } catch (NullPointerException e) {
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            in.close();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file)
            throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        TaskIO.write(tasks, fileWriter);
    }

    public static void readText(AbstractTaskList tasks, File file)
            throws IOException {
        FileReader fileReader = new FileReader(file);
        TaskIO.read(tasks, fileReader);
    }
}
