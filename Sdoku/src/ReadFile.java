import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
    private String fileName;
    private int[][] sudokuArray;

    public ReadFile() {
        fileName = "sudoku.txt";
        sudokuArray = new int[9][9];

    }

    public void fileOpen() {
        try {
            int num = 0;
            int i = 0;
            int j = 0;
            FileReader reader = new FileReader(fileName);
            num = reader.read();
            while (num != -1) {
                if (Character.isDigit(num)) {
                    sudokuArray[i][j] = num - '0';
                    j++;
                } else {
                    j = 0;
                    i++;
                }
                num = reader.read();
            }

        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않습니다");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                System.out.print(sudokuArray[i][j] + " ");
            System.out.print("\n");
        }
    }

    public void findZero() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if (sudokuArray[i][j] == 0)
                    fillZero(i, j);
        }
    }

    private void fillZero(int row, int col) {
        ArrayList<Integer> fillList = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            fillList.add(i);

        checkRow(row, fillList);
        checkCol(col,fillList);
        
        System.out.println(fillList.size());

        if(fillList.size()==1){
            sudokuArray[row][col]=fillList.get(0);
        }

    }

    private void checkRow(int row, ArrayList<Integer> fillList) {
        for (int find = 1; find < 10; find++) {
            for (int i = 0; i < 9; i++)
                if (sudokuArray[row][i] == find) {
                    fillList.remove(Integer.valueOf(find));
                    break;
                }
        }


    }

    private void checkCol(int col, ArrayList<Integer> fillList) {
        for (int find = 1; find < 10; find++) {
            for (int i = 0; i < 9; i++)
                if (sudokuArray[i][col] == find) {
                    fillList.remove(Integer.valueOf(find));
                    break;
                }
        }
    }

    public void checkThreeMatrix(int row, int col) {

    }
}
