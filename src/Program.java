import com.sun.tools.javac.Main;

import java.io.*;
import java.util.*;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Program {
    static Logger LOGGER;

    static {
        try (FileInputStream ins = new FileInputStream("log_config.txt")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            System.out.println("События поминутно выводятся в файл Timelist.txt");
            System.out.println("Расписание находится в файле  schedule.txt");
            System.out.println("Лог в файле log.0.0.txt");
            LOGGER.log(Level.INFO, "Начало main, создаем лист обьектов класса action");
            LinkedList<action> list = new LinkedList<>();

            LOGGER.log(Level.INFO, "Создание словаря обьектов класса trains");
            HashMap<Integer, trains> trains = new HashMap<>();

            LOGGER.log(Level.INFO, "Создание обьекта класса station");
            full_station station = new full_station("Курган", 12, 24);

            BufferedReader read = new BufferedReader(new FileReader("busy_ways.txt"));
            String line;

            LOGGER.log(Level.INFO, "Чтение списка занятых грузовыми составами путей");
            while ((line = read.readLine()) != null) {
                LOGGER.log(Level.INFO, "Занятый путь: " + line);
                station.setBusy_way(Integer.parseInt(line));
            }

            BufferedReader reader = new BufferedReader(new FileReader("schedule.txt"));
            LOGGER.log(Level.INFO, "Чтение расписания движения поездов");
            ArrayList<schedule> scheduleList = new ArrayList<>();
            schedule str;
            while ((line = reader.readLine()) != null) {
                LOGGER.log(Level.INFO, "Считана строка расписания");
                String[] ln = line.split(" ");
                str = new schedule(ln);
                scheduleList.add(str);
            }
            timeline obj = new timeline();
            int timer = 0;
            File file = new File("Timelist.txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            while (list.size() > 0 || scheduleList.size() > 0) {
                if (scheduleList.size() > 0) {
                    if (scheduleList.get(0).getArrival_time() != -1) {
                        if (list.size() == 0 || scheduleList.get(0).getArrival_time() - 10 < list.get(0).getTime()) {
                            obj.new_action(scheduleList, list, LOGGER);
                        } else {
                            timer = obj.execution(list, station, writer, trains, timer, LOGGER);
                        }
                    } else {
                        if (list.size() == 0 || scheduleList.get(0).getDep_time() - 30 < list.get(0).getTime()) {
                            obj.new_action(scheduleList, list, LOGGER);
                        } else {
                            timer = obj.execution(list, station, writer, trains, timer, LOGGER);
                        }
                    }
                } else {
                    timer = obj.execution(list,station,writer,trains, timer, LOGGER);
                }
            }
            while (timer < 1440) {
                String time = "" + (timer / 60) / 10 + (timer / 60) % 10 + ":" + (timer % 60) / 10 + (timer % 60) % 10;
                writer.write(time + " : ------\n");
                timer += 1;
            }
            writer.close();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "что-то пошло не так", e);
        }

    }
}
