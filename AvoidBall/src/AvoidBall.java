import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class AvoidBall extends PApplet {

    private int ballNum;
    private int lastScore;
    private int currentBallNum;
    private boolean start;

    private List<Ball> ballList;
    private Player player;

    public static void main(String args[]) {
        PApplet.main("AvoidBall");
    }

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        ballNum = 7;
        currentBallNum = 0;
        start = true;
        player = new Player(400);


        rectMode(CENTER);

        ballList = new ArrayList<>();
        for (int i = 0; i < ballNum; i++)
            ballList.add(new Ball(random(width), 10, random(3, 9)));

        background(0);
    }

    public void draw() {
        if (!start) {
            return;
        }

        background(0);

        for (currentBallNum = 0; currentBallNum < ballList.size(); currentBallNum++) {
            ballList.get(currentBallNum).drawBall(this);
            ballList.get(currentBallNum).moveBall();
        }

        checkCollision();
        detectCurrentBall();

        fill(255);
        textSize(25);
        text("Energy: " + player.getEnergy(), 640, 35);
        countScore();


        player.drawPlayer(this);

    }

    private void detectCurrentBall() {

        for (Ball aBallList : ballList) {
            if (aBallList.getPosY() >= height + 15) {
                aBallList.setPosY(10);
                aBallList.setPosX(random(width));
                aBallList.setSpeed(1);
                if (aBallList == player.getBeforeTouch())
                    player.setBeforeTouch(null);
            }
        }
    }

    private void checkCollision() {
        for (Ball aBallList : ballList) {
            boolean collision = player.checkCollision(aBallList);
            if (collision) {
                if (player.getEnergy() == 0) {

                    textSize(40);
                    text("You die", 350, 300);
                    start = false;
                }
            }
        }
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == RIGHT || keyCode == LEFT)
                player.setPlayerPosition(keyCode);
        }
        char keycode = Character.toUpperCase(key);

        if (keycode == 'R') { //재시작
            resetGame();
        }
    }

    private void countScore() {
        int currentScore = millis() - lastScore;
        textSize(20);
        fill(255);
        text("Score: "+ currentScore, 630, 70);

    }

    private void resetGame() {
        if(start)
            return;

        for (Ball aBallList : ballList) {
            aBallList.setPosY(10);
            aBallList.setPosX(random(width));
            aBallList.setSpeed(1);
        }

        player.setEnergy(100);
        start = true;
        lastScore = millis();
    }

}



