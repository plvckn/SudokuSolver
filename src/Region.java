public class Region {

    private int[][] region = new int[3][3];

    public int[][] getRegion() {
        return region;
    }

    public int getTile(int x, int y) {
        return this.region[x][y];
    }

    public void setTile(int x, int y, int value) {
        this.region[x][y] = value;
    }

    public void setTile(Coord coord, int value) {
        this.region[coord.getX()][coord.getY()] =  value;
    }

    public Coord findAvailableTile() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(this.getTile(i, j) == 0) {
                    return new Coord(i, j);
                }
            }
        }
        return null;
    }

    public int findLowestAvailableValue(int previousValue) {
        for (int value = 1; value <= 9; value++) {
            if(value > previousValue && !isValuePresentRegion(value)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unable to find any fitting value for this tile.");
    }

    private boolean isValuePresentRegion(int value) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(this.getTile(i, j) == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValuePresentColumn(int value, int column)  {
        for(int row = 0; row < 3; row++) {
            if(this.getTile(row, column) == value) {
                return true;
            }
        }
        return false;
    }

    public boolean isValuePresentRow(int value, int row) {
        for(int column = 0; column < 3; column++) {
            if(this.getTile(row, column) == value) {
                return true;
            }
        }
        return false;
    }

    // Functions for testing & printing purposes

    public void printFirstRow() {
        for(int i = 0; i < 1; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(this.getTile(i, j) + " ");
            }
        }
    }

    public void printSecondRow() {
        for(int i = 1; i < 2; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(this.getTile(i, j) + " ");
            }
        }
    }

    public void printThirdRow() {
        for(int i = 2; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(this.getTile(i, j) + " ");
            }
        }
    }

}
