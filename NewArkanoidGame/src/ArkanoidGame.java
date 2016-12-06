import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class ArkanoidGame extends PApplet {

    private List<Ball> balls;
    private List<Item> items;
    private Stage stage;
    private PlayerBar bar;
    private boolean gameOver;
    private static ArkanoidGame game;


    public static void main(String args[]) {

        game = new ArkanoidGame();
        game.runSketch();

    }

    public void draw() {
        background(0);

        update();

        stage.drawStage(this);

        for (Item item : items) {
            item.draw(this);
        }

        bar.draw(this);

        for (Ball ball : balls)
            ball.draw(this);

    }

    private void update() {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.update();
            if (item.deleteItem() || bar.checkCollisionItem(item, balls))
                items.remove(item);
        }

        if (stage.isStageClear()) {
            balls.clear();
            balls.add(new Ball(0, 1));
            displayStageClear();
            noLoop();
        }

        barKeyPressed();
        bar.update();

        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            ball.update();
            if (ball.checkCollisionWindow())
                balls.remove(ball);
            ball.checkCollisionBar(bar);
            stage.checkCollisionBrick(ball, items);

        }
        if (balls.size() == 0) {
            gameOver = true;
            displayGameOver();
            noLoop();
        }
    }

    public void setup() {
        balls = new ArrayList<>();
        items = new ArrayList<>();
        Ball ball = new Ball(0, 1);
        balls.add(ball);
        stage = new Stage();
        bar = new PlayerBar(1, 0);
    }

    public void settings() {
        size(800, 600);
    }

    private void barKeyPressed() {
        if (keyPressed) {
            if (key == CODED) {
                if (keyCode == RIGHT)
                    bar.accel(keyCode);
                else
                    bar.accel(keyCode);
            }
        }
    }

    public void keyPressed() {
        char keyCode = Character.toUpperCase(key);
        if (keyCode == 'R') {
            loop();
        }
        if (keyCode == 'S' && gameOver) {
            gameOver = false;
            restart();
        }
    }

    private void displayGameOver() {
        fill(255);
        textSize(25);
        text("Game Over", 350, 300);
        text("Restart : 'S'", 350, 400);
    }

    private void displayStageClear() {
        fill(255);
        textSize(25);
        text("Stage Clear", 350, 350);
        text("Press 'R'", 350, 400);
    }

    private void restart() {
        game.dispose();
        game = new ArkanoidGame();
        game.runSketch();
    }

}
