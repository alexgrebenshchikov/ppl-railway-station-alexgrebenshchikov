import com.sun.tools.javac.Main;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;


public class events {
    public void platform_way(arrival_trains train, full_station station) {
        int free_way = station.get_free_way();
        train.setWay(free_way);
        train.setPlatform(free_way);
        station.setBusy_way(train.getWay());
    }


    public void departure(arrival_trains train, full_station station) {
        station.add_free_way(train.getWay());
    }

    public boolean change_carriages(arrival_trains train, full_station station, schedule sch) {
        if (sch.getChange_carriages() > station.getStationCarriages() || -(sch.getChange_carriages()) > train.getNumber_of_carriages()) {
            return false;
        } else {
            train.setNumber_of_carriages(sch.getChange_carriages());
            station.setStationCarriages(sch.getChange_carriages());
            return true;
        }
    }


    public int delay() {
        final Random random = new Random();
        if (random.nextInt(5) == 0) {
            int wait = 30 + random.nextInt(120);
            if (random.nextInt(2) == 0) {
                return wait - wait % 30;
            } else {
                return -(wait - wait % 30);
            }
        } else {
            return 0;
        }
    }

    public int insert(LinkedList<action> list, action act) {
        if (list.isEmpty()) {
            list.add(act);
            return 0;
        }
        int pos = 0;
        while (pos < list.size()) {
            if (list.get(pos).getTime() >= act.getTime()) {
                break;
            }
            pos++;
        }
        if (pos == list.size()) {
            list.addLast(act);
            return 0;
        } else {
            list.add(pos, act);
            return 0;
        }

    }

}
