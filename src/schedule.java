public class schedule {
    private int number_of_train;
    private String src;
    private String dest;
    private int arrival_time;
    private int dep_time;
    private int number_of_carriages;
    private String direction;
    private int change_carriages;

    public schedule(String[] ln) {
        int number = Integer.parseInt(ln[0]);
        int ar_t;
        String[] time;
        if (!ln[3].equals("-")) {
            time = ln[3].split(":");
            ar_t = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
        } else {
            ar_t = -1;
        }
        int dep_t;
        if (!ln[4].equals("-")) {
            time = ln[4].split(":");
            dep_t = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
        } else {
            dep_t = -1;
        }
        int carr = Integer.parseInt(ln[5]);
        int change = Integer.parseInt(ln[7]);
        this.number_of_train = number;
        this.src = ln[1];
        this.dest = ln[2];
        this.arrival_time = ar_t;
        this.dep_time = dep_t;
        this.number_of_carriages = carr;
        this.direction = ln[6];
        this.change_carriages = change;
    }

    public int getNumber_of_train() {
        return number_of_train;
    }

    public String getSrc() {
        return src;
    }

    public String getDest() {
        return dest;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public int getDep_time() {
        return dep_time;
    }

    public int getNumber_of_carriages() {
        return number_of_carriages;
    }

    public int getChange_carriages() {
        return change_carriages;
    }

    public String getDirection() {
        return direction;
    }

    public void delay_arrival_time(int delay) {
        this.arrival_time += delay;
    }
    public void delay_dep_time(int delay) {
        this.dep_time += delay;
    }

    public void setChange_carriages(int change_carriages) {
        this.change_carriages = change_carriages;
    }
}
