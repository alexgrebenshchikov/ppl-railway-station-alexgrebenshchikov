
public class trains {
    private int number_of_train;
    private String source;
    private String destination;
    private int arrival_time;
    private int dep_time;
    private int number_of_carriages;
    private String direction;
    private int way;
    private int platform;

    public trains(int number, String src,String dest,int ar_t,int dep_t,int num,String dir) {
        this.number_of_train = number;
        this.source = src;
        this.destination = dest;
        this.arrival_time = ar_t;
        this.dep_time = dep_t;
        this.number_of_carriages = num;
        this.direction = dir;
    }

    public void setWay(int way) {
        this.way = way;
    }
    public void setPlatform(int p) {
        this.platform = p;
    }
    public int getWay() {
        return way;
    }
    public int getPlatform() {
        return platform;
    }
    public void setNumber_of_carriages(int num) {
        this.number_of_carriages += num;
    }
    public int getNumber_of_carriages() { return number_of_carriages; }

}


