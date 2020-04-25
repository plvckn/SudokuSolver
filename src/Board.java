import java.util.Stack;

public class Board {

    private Region[][] region;
    private Stack<Move> moves = new Stack<>();

    public Board() {
        this.region = new Region[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                region[i][j] = new Region();
            }
        }
    }

    public void print() {
        System.out.println("---------------------");
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                Region current = this.getRegion(i, j);
                current.printFirstRow();
                System.out.print(" ");
            }
            System.out.println();
            for(int j = 0; j < 3; j++) {
                Region current = this.getRegion(i, j);
                current.printSecondRow();
                System.out.print(" ");
            }
            System.out.println();
            for(int j = 0; j < 3; j++) {
                Region current = this.getRegion(i, j);
                current.printThirdRow();
                System.out.print(" ");
            }
            System.out.println("\n");
        }
        System.out.println("---------------------");
    }

    public void fillInitialValues() { // a sample of values that enable this particular sudoku to have 1 unique solution.
        this.getRegion(0, 0).setTile(0, 0, 3);
        this.getRegion(0, 0).setTile(0, 2, 6);
        this.getRegion(0, 0).setTile(1, 0, 5);
        this.getRegion(0, 0).setTile(1, 1, 2);
        this.getRegion(0, 0).setTile(2, 1, 8);
        this.getRegion(0, 0).setTile(2, 2, 7);

        this.getRegion(0, 1).setTile(0, 0, 5);
        this.getRegion(0, 1).setTile(0, 2, 8);

        this.getRegion(0, 2).setTile(0, 0, 4);
        this.getRegion(0, 2).setTile(2, 1, 3);
        this.getRegion(0, 2).setTile(2, 2, 1);

        this.getRegion(1, 0).setTile(0, 2, 3);
        this.getRegion(1, 0).setTile(1, 0, 9);
        this.getRegion(1, 0).setTile(2, 1, 5);

        this.getRegion(1, 1).setTile(0, 1, 1);
        this.getRegion(1, 1).setTile(1, 0, 8);
        this.getRegion(1, 1).setTile(1, 1, 6);
        this.getRegion(1, 1).setTile(1, 2, 3);
        this.getRegion(1, 1).setTile(2, 1, 9);

        this.getRegion(1, 2).setTile(0, 1, 8);
        this.getRegion(1, 2).setTile(1, 2, 5);
        this.getRegion(1, 2).setTile(2, 0, 6);

        this.getRegion(2, 0).setTile(0, 0, 1);
        this.getRegion(2, 0).setTile(0, 1, 3);
        this.getRegion(2, 0).setTile(2, 2, 5);

        this.getRegion(2, 1).setTile(2, 0, 2);
        this.getRegion(2, 1).setTile(2, 2, 6);

        this.getRegion(2, 2).setTile(0, 0, 2);
        this.getRegion(2, 2).setTile(0, 1, 5);
        this.getRegion(2, 2).setTile(1, 1, 7);
        this.getRegion(2, 2).setTile(1, 2, 4);
        this.getRegion(2, 2).setTile(2, 0, 3);


    }

    public Region getRegion(int i, int j) {
        return this.region[i][j];
    }

    public Region getRegion(Coord coord) {
        return this.region[coord.getX()][coord.getY()];
    }

    public boolean hasMatchingColumn(int tileColumn, int value, int regionColumn) {
        for (int i = 0; i < 3; i++) {
            if(this.getRegion(i, regionColumn).isValuePresentColumn(value, tileColumn)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMatchingRow(int tileRow, int value, int regionRow) {
        for (int i = 0; i < 3; i++) {
            if(this.getRegion(regionRow, i).isValuePresentRow(value, tileRow)) {
                return true;
            }
        }
        return false;
    }

    public void solve() {
        for(int regionRow = 0; regionRow < 3; regionRow++) {
            for(int regionColumn = 0; regionColumn < 3; regionColumn++) {
                Region currentRegion = this.getRegion(regionRow, regionColumn);
                Coord availableTile;
                while ((availableTile = currentRegion.findAvailableTile()) != null) {
                    try {
                        placeValue(availableTile, currentRegion, regionRow, regionColumn, 0);
                    } catch (Exception ex) {
                        Coord currentCoords = performBacktrackingStep();
                        currentRegion = this.getRegion(0, 0); // update current region coords
                        regionColumn = 0;
                        regionRow = 0;
                    }
                }
            }
        }
    }

    private void placeValue(Coord availableTile, Region currentRegion, int regionRow, int regionColumn, int previousValue) {
        int valueToPlace = currentRegion.findLowestAvailableValue(previousValue);
        int tileColumn = availableTile.getY();
        int tileRow = availableTile.getX();
        if (!this.hasMatchingColumn(tileColumn, valueToPlace, regionColumn) &&
                !this.hasMatchingRow(tileRow, valueToPlace, regionRow)) {
            currentRegion.setTile(availableTile, valueToPlace);
            moves.push(new Move(new Coord(regionRow, regionColumn), new Coord(tileRow, tileColumn), valueToPlace));
        } else {
            placeValue(availableTile, currentRegion, regionRow, regionColumn, valueToPlace);
        }
    }

    private Coord performBacktrackingStep() {
        if(moves.size() == 0) {
            throw new NullPointerException("Cannot perform backtracking on an empty move stack.");
        }
        Move lastMove = moves.pop();
        Coord regionCoord = lastMove.getRegionCoord();
        Region currentRegion = this.getRegion(regionCoord.getX(), regionCoord.getY());
        clearTile(currentRegion, lastMove.getTileCoord());
        try {
            placeValue(lastMove.getTileCoord(), currentRegion, regionCoord.getX(), regionCoord.getY(), lastMove.getValue());
        } catch (Exception ex) {
            performBacktrackingStep();
        }
        return regionCoord;
    }

    public void clearTile(Region region, Coord tileCoord) {
        region.setTile(tileCoord, 0);
    }
}
