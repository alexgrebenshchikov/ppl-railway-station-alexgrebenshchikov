public class station_carriages
extends platforms_and_ways{
    private int StationCarriages;
    public station_carriages(String name, int n, int c) {
        super(name, n);
        StationCarriages = c;
    }
    public int getStationCarriages() {
        return StationCarriages;
    }
    public void setStationCarriages(int num) {
        StationCarriages = StationCarriages - num;
    }
}
