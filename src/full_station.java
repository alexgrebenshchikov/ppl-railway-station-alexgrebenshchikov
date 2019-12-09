import java.util.HashSet;
import java.util.Iterator;

public class full_station
        extends station_carriages {
    private HashSet<Integer> free_ways = new HashSet<>();


    public full_station(String name, int n, int c) {
        super(name, n, c);
        for (int i = 1; i <= getWays(); i++) {
            this.free_ways.add(i);
        }
    }



    public void setBusy_way(int num) {
        free_ways.remove(num);
    }

    public void add_free_way(int num) {
        this.free_ways.add(num);
    }



    public int get_free_way() {
        if(free_ways.size() > 0) {
            Iterator <Integer> i = free_ways.iterator();
            return i.next();
        } else {
            return 0;
        }
    }



}
