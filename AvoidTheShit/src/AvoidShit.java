import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class AvoidShit extends PApplet {

    private int shitNum ;
    private int lastScore;
    private int currentScore;
    private int currentShitNum;
    private boolean start;

    private List<Shit> shitList;
    private Player player;

    public static void main(String args[]) {
        PApplet.main("AvoidShit");
    }

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        shitNum = 7;
        currentShitNum = 0;
        start = true;
        player = new Player(400);


        rectMode(CENTER);

        shitList = new ArrayList<>();
        for (int i = 0; i < shitNum; i++)
            shitList.add(new Shit(random(width), 10, random(3, 9)));

        background(0);
    }

    public void draw() {
        if (!start) {
            return;
        }


        background(0);




        for (currentShitNum = 0; currentShitNum < shitList.size(); currentShitNum++) {
            shitList.get(currentShitNum).drawShit(this);
            shitList.get(currentShitNum).moveShit();
        }
        fill(255);
        textSize(32);
        text(player.getEnergy(), 730, 35);

        player.drawPlayer(this);
        detectCurrentShit();




    }

    private void detectCurrentShit() {

        for (Shit aShitList : shitList) {

            if (aShitList.getPosY() >= height + 15) {
                aShitList.setPosY(10);
                aShitList.setPosX(random(width));
                aShitList.setSpeed(1);
            }
        }

        isShitTouched();
    }

    private void isShitTouched() {
        for (Shit aShitList : shitList) {
            boolean collision = aShitList.checkCollision(player.getPosX(), 580);
            if (collision) {
                countScore();

                player.setEnergy(player.getEnergy()-25);
                textSize(32);
                text("You die", 350, 300);
                text(currentScore,350,350);


                start = false;
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
            initShit();
        }
    }

    public void countScore(){
        currentScore = millis() - lastScore;
        lastScore = millis();

    }

    public void initShit(){
        for (Shit aShitList : shitList) {
            aShitList.setPosY(10);
            aShitList.setPosX(random(width));
            aShitList.setSpeed(1);
        }

        start = true;
    }


}



