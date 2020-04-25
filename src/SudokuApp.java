public class SudokuApp {

    public static void main(String[] args) {
        System.out.println("app starting");

        Board sudokuBoard = new Board();

        sudokuBoard.fillInitialValues();
        sudokuBoard.print();
        sudokuBoard.solve();
        sudokuBoard.print();
    }

}
