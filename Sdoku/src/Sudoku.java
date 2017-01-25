public class Sudoku {

    public static void main(String args[]){
        ReadFile readFile = new ReadFile();
        readFile.fileOpen();
        readFile.printSudoku();
        System.out.println("---------------------------------");
        readFile.findZero();
        readFile.printSudoku();
    }

}
