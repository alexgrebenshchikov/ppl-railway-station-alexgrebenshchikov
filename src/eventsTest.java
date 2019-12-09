import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class eventsTest {
    @Test
    public void Test_platforms_ways() {
        full_station station = new full_station("Курган", 3, 24);
        station.setBusy_way(1);
        station.setBusy_way(3);
        String[] ln = {"176", "Краснодар", "Воронеж", "00:10", "01:00", "21", "с_хвоста", "2"};
        schedule str = new schedule(ln);
        arrival_trains train = new arrival_trains(str.getNumber_of_train(), str.getSrc(), str.getDest(), str.getArrival_time(), str.getDep_time(), str.getNumber_of_carriages(), str.getDirection());
        events et = new events();
        et.platform_way(train,station);
        Assert.assertEquals(train.getWay(), 2);
        station.setBusy_way(2);
        et.platform_way(train,station);
        Assert.assertEquals(train.getWay(), 0);
    }
    @Test
    public void Test_change_carriages() {
        full_station station = new full_station("Курган", 3, 24);
        String[] ln = {"176", "Краснодар", "Воронеж", "00:10", "01:00", "21", "с_хвоста", "12"};
        schedule str = new schedule(ln);
        arrival_trains train = new arrival_trains(str.getNumber_of_train(), str.getSrc(), str.getDest(), str.getArrival_time(), str.getDep_time(), str.getNumber_of_carriages(), str.getDirection());
        events event = new events();
        event.change_carriages(train,station,str);
        Assert.assertEquals(train.getNumber_of_carriages(), 33);
        Assert.assertEquals(station.getStationCarriages(), 12);
        str.setChange_carriages(37);
        Assert.assertEquals(event.change_carriages(train,station,str), false);
        str.setChange_carriages(-21);
        event.change_carriages(train,station,str);
        Assert.assertEquals(train.getNumber_of_carriages(), 12);
        Assert.assertEquals(station.getStationCarriages(), 33);
        Assert.assertEquals(event.change_carriages(train,station,str), false);

    }

}