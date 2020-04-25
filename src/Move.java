public class Move {

    private Coord region, tile;
    private int value;

    public Move(Coord region, Coord tile, int value) {
        this.region = region;
        this.tile = tile;
        this.value = value;
    }

    public Coord getRegionCoord() {
        return this.region;
    }

    public Coord getTileCoord() {
        return this.tile;
    }

    public int getValue() {
        return this.value;
    }

}
