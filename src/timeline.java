import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.abs;

public class timeline {
    public void new_action(ArrayList<schedule> scheduleList, LinkedList<action> list, Logger LOGGER) {
        events event = new events();
        action act;
        schedule str = scheduleList.get(0);
        int delay = event.delay();
        if (delay != 0) {
            if (delay > 0 && str.getArrival_time() == -1 || delay < 0 && str.getDep_time() == -1) {
                delay = -delay;
            }
            if (delay > 0) {
                LOGGER.log(Level.INFO, "Случайная задержка прибытия на " + delay + " минут" );
                act = new action(str, str.getArrival_time() - 10, 4);
                act.setDelay(delay);
                event.insert(list, act);
            } else {
                LOGGER.log(Level.INFO, "Случайная задержка отбытия на " + -(delay) + " минут");
                act = new action(str, str.getDep_time() - 10, 4);
                act.setDelay(delay);
                event.insert(list, act);
            }
        }
        if (str.getArrival_time() != -1) {
            if (delay > 0) {
                str.delay_arrival_time(delay);
            }
            act = new action(str, str.getArrival_time() - 10, 0);
            event.insert(list, act);
            act = new action(str, str.getArrival_time(), 3);
            event.insert(list, act);
        }
        if (str.getDep_time() != -1 && str.getArrival_time() != -1) {
            if (delay != 0) {
                str.delay_dep_time(abs(delay));
            }
            act = new action(str, str.getDep_time(), 1);
            event.insert(list, act);
        }
        if (str.getArrival_time() == -1) {
            if (delay < 0) {
                str.delay_dep_time(abs(delay));
            }
            act = new action(str, str.getDep_time() - 30, 0);
            event.insert(list, act);
            act = new action(str, str.getDep_time(), 1);
            event.insert(list, act);
        }
        if (str.getChange_carriages() != 0) {
            if (str.getDep_time() != -1) {
                act = new action(str, str.getDep_time() - 10, 2);
                event.insert(list, act);
            } else {
                act = new action(str, str.getArrival_time() + 10, 2);
                event.insert(list, act);
            }
        }
        scheduleList.remove(0);
    }

    public int execution(LinkedList<action> list, full_station station, FileWriter writer, HashMap<Integer, arrival_trains> trains, int timer, Logger LOGGER) throws IOException {
        String time;
        action act = list.get(0);
        while(timer < act.getTime()) {
            time = "" + (timer / 60) / 10 + (timer / 60) % 10 + ":" + (timer % 60) / 10 + (timer % 60) % 10;
            writer.write(time + " : ------\n");
            timer += 1;
        }
        schedule str = act.getObj();
        events event = new events();
        time = "" + (act.getTime() / 60) / 10 + (act.getTime() / 60) % 10 + ":" + (act.getTime() % 60) / 10 + (act.getTime() % 60) % 10;
        writer.write(time + " : ");
        String msg;
        arrival_trains train;
        if (act.getType() == 0) {
            train = new arrival_trains(str.getNumber_of_train(), str.getSrc(), str.getDest(), str.getArrival_time(), str.getDep_time(), str.getNumber_of_carriages(), str.getDirection());
            LOGGER.log(Level.INFO, "Вызов метода platform_way");
            event.platform_way(train, station);
            if (str.getArrival_time() != -1) {
                msg = "Поезд №" + str.getNumber_of_train() + " " + str.getSrc() + "-" + str.getDest() + " прибывает через 10 мин на " + train.getWay() + " путь, " + train.getPlatform() + " платформа, нумерация " + str.getDirection() + " состава";
                writer.write(msg + "\n");
            } else {
                msg = "Поезд №" + str.getNumber_of_train() + " " + str.getSrc() + "-" + str.getDest() + " отбывает через 30 мин с " + train.getWay() + " пути, " + train.getPlatform() + " платформы, нумерация " + str.getDirection() + " состава";
                writer.write(msg + "\n");
            }
            LOGGER.log(Level.INFO, "Добавление обьекта train в словарь");
            trains.put(str.getNumber_of_train(), train);
        }
        if (act.getType() == 3) {
            train = trains.get(str.getNumber_of_train());
            msg = "Поезд №" + str.getNumber_of_train() + " " + str.getSrc() + "-" + str.getDest() + " прибыл на " + train.getWay() + " путь, " + train.getPlatform() + " платформа";
            writer.write(msg + "\n");
        }

        if (act.getType() == 1) {
            train = trains.get(str.getNumber_of_train());
            LOGGER.log(Level.INFO, "Вызов метода departure");
            event.departure(train, station);
            msg = "Поезд №" + str.getNumber_of_train() + " " + str.getSrc() + "-" + str.getDest() + " отправляется  с " + train.getWay() + " пути, " + train.getPlatform() + " платформы";
            writer.write(msg + "\n");
            trains.remove(str.getNumber_of_train());
        }
        if (act.getType() == 2) {
            train = trains.get(str.getNumber_of_train());
            LOGGER.log(Level.INFO, "Вызов метода change_carriages");
            if (event.change_carriages(train, station, str)) {
                if (str.getChange_carriages() > 0) {
                    msg = "К поезду №" + str.getNumber_of_train() + " " + str.getSrc() + "-" + str.getDest() + " прицеплено " + str.getChange_carriages() + " вагонов ";
                    writer.write(msg + "\n");
                } else {
                    msg = "От поезда №" + str.getNumber_of_train() + " " + str.getSrc() + "-" + str.getDest() + " отцеплено " + -(str.getChange_carriages()) + " вагонов ";
                    writer.write(msg + "\n");
                }
            }
        }
        if (act.getType() == 4) {
            if (act.getDelay() > 0) {
                msg = "Прибытие поезда №" + str.getNumber_of_train() + " " + str.getSrc() + "-" + str.getDest() + " задерживается на " + act.getDelay() + " минут";
                writer.write(msg + "\n");
            } else {
                msg = "Отбытие поезда №" + str.getNumber_of_train() + " " + str.getSrc() + "-" + str.getDest() + " задерживается на " + -(act.getDelay()) + " минут";
                writer.write(msg + "\n");
            }
        }
        list.remove(0);
        return timer;
    }

}
