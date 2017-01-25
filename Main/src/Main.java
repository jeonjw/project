import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCase = scanner.nextInt();
        int[][] people = new int[15][15];
        int[] print = new int[testCase];

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                
                if (i == 0)
                    people[i][j] = j + 1;
                if (j == 0)
                    people[i][j] = 1;
                else if(i != 0){
                    people[i][j] = people[i][j - 1] + people[i-1][j];
                }

            }
        }


        for (int i = 0; i < testCase; i++) {
            int k = scanner.nextInt();
            int n = scanner.nextInt();

            print[i] = people[k][n-1];
        }

        for (int i = 0; i < testCase; i++) {
            System.out.println(print[i]);
        }

    }


}
