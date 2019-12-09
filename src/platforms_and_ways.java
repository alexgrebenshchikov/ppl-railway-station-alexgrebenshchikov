public class platforms_and_ways
    extends station_name{
    private int ways,platforms;
    public platforms_and_ways(String name, int n) {
        super(name);
        this.ways = n;
        this.platforms = n;
    }
    public int getWays() {
        return ways;
    }
    public int getPlatforms() {
        return platforms;
    }
}
