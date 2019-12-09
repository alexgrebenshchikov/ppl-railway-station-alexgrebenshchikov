public class action {
    private schedule obj;
    private int time;
    private int type;
    private int delay;
    public action(schedule obj,int time,int type) {
        this.obj = obj;
        this.time = time;
        this.type = type;
    }

    public schedule getObj() {
        return obj;
    }

    public int getTime() {
        return time;
    }

    public int getType() {
        return type;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
