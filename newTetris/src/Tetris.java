import ddf.minim.Minim;
import processing.core.PApplet;

public class Tetris extends PApplet {

    private static int[][] tetrisBoard;
    private static Tetris game;
    private Block block;
    private Block nextBlock;
    private int time;
    private boolean keyIn;
    private int keyTime;
    private int score;
    private boolean play;
    private ddf.minim.AudioPlayer bgm;
    private int combo;

    public static void main(String args[]) {
        game = new Tetris();
        game.runSketch();

    }

    static int[][] getTetrisBoard() {
        return tetrisBoard;
    }

    public void draw() {
        background(0);
        drawGrid();
        displayText();
        block.drawBlock(this);
        nextBlock.drawNextBlock(this);
        pressKey();
        update();
        drawFixedBlock();

    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        noLoop();
        tetrisBoard = new int[20][10];
        block = new TBlock();
        setNextBlock();
        time = millis();
        keyTime = millis();
        combo = 0;
        playBgm();
    }

    private void playBgm() {
        Minim minim = new Minim(this);
        bgm = minim.loadFile("tetris.wav", 2048);
        bgm.play();
    }

    private void drawGrid() {
        stroke(140, 70);
        for (int i = 0; i <= 400; i += 40)
            line(i, 0, i, 800);
        for (int i = 0; i <= 800; i += 40)
            line(0, i, 400, i);


        stroke(140, 70);
        line(500, 40, 500, 220);
        line(680, 40, 680, 220);
        line(500, 40, 680, 40);
        line(500, 220, 680, 220);
    }

    private void displayText() {
        fill(255);
        textSize(25);
        text("Next", 530, 25);

        fill(255, 255, 50);
        textSize(80);
        text("TETRIS", 480, 350);

        fill(255);
        textSize(25);
        text("Score : ", 480, 500);
        text(score, 570, 500);

        if (!play) {
            fill(255, 255, 255);
            textSize(40);
            text("Press 'S' ", 350, 400);
        }
    }

    private void displayGameOver() {
        fill(255, 50, 50);
        textSize(40);
        text("Game Over", 480, 400);
        fill(255);
        text("Restart 'R'", 480, 450);
    }

    private void pressKey() {
        if (keyPressed && !keyIn) {
            keyIn = true;
            keyTime = millis();
            if (keyCode == LEFT && !block.checkLeftWall())
                block.moveLeft();
            else if (keyCode == RIGHT && !block.checkRightWall())
                block.moveRight();
            else if (keyCode == DOWN && !block.checkBottom())
                block.moveDown();
            else if (keyCode == UP)
                block.checkRotate();

        }

    }

    public void keyPressed() {

        char keyValue = Character.toUpperCase(key);
        if (keyValue == 'S') {
            loop();
            play = true;
        } else if (keyValue == 'R')
            restart();

        if (keyCode == 32) {
            block.space();
            block = nextBlock;
            setNextBlock();
        }
    }

    private void update() {

        if (millis() >= time + 1000) {
            time = millis();
            block.moveDown();

        }
        if (millis() >= time + 700)
            if (block.checkBottom()) { //블록이 고정됬다

                block.fixBlock();
                block = nextBlock;
                setNextBlock();


            }
        if (millis() >= keyTime + 80) {
            keyIn = false;
        }





       /* checkCombo(afterScore,beforeScore);
        System.out.println((afterScore - beforeScore)/1000);
        System.out.println(combo);*/
        checkGameOver();

    }
    private void checkCombo(int after,int before){
        if(after > before)
            combo++;
        else if (after == before)
            combo = 0;
    }

    private void drawFixedBlock() {
        fill(200, 150);
        for (int i = 0; i < tetrisBoard.length; i++)
            for (int j = 0; j < tetrisBoard[i].length; j++)
                if (tetrisBoard[i][j] == 1)
                    rect((j * 40), (i * 40), 40, 40);
    }

    private void setNextBlock() {
        int value = (int) (Math.random() * 7);
        switch (value) {
            case 0:
                nextBlock = new OBlock();
                break;
            case 1:
                nextBlock = new IBlock();
                break;
            case 2:
                nextBlock = new ZBlock();
                break;
            case 3:
                nextBlock = new TBlock();
                break;
            case 4:
                nextBlock = new LBlock();
                break;
            case 5:
                nextBlock = new JBlock();
                break;
            case 6:
                nextBlock = new SBlock();
        }
    }

    private void checkDeleteLine() {

        for (int i = 0; i < tetrisBoard.length; i++) {
            int count = 0;
            for (int j = 0; j < tetrisBoard[i].length; j++) {
                if (tetrisBoard[i][j] == 1)
                    count++;
                if (count == 10)
                    deleteLine(i);

            }
        }
    }

    private void deleteLine(int lineNum) {
        for (int i = 0; i < 10; i++)
            tetrisBoard[lineNum][i] = 0;

        for (int i = lineNum; i > 0; i--)
            for (int j = 0; j < 10; j++) {
                tetrisBoard[i][j] = tetrisBoard[i - 1][j];
                tetrisBoard[i - 1][j] = 0;
            }
        score += 1000;
    }

    private void checkGameOver() {
        if (tetrisBoard[0][4] == 1 && tetrisBoard[0][5] == 1) {
            noLoop();
            displayGameOver();
            bgm.close();
        }
    }

    private void restart() {
        game.dispose();
        game = new Tetris();
        game.runSketch();
    }


}
