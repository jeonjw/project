import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Stage {
    private List<Brick> bricks;
    private String filePath;

    Stage() {
        bricks = new ArrayList<>();
        filePath = "./map1.txt";
        readFile();
    }

    private void readFile() {
        FileReader fileReader;
        int data;
        int brickPosX = 5;
        int brickPosY = 5;

        try {
            fileReader = new FileReader(filePath);
            while ((data = fileReader.read()) != -1) {
                if (data == '\n') {
                    brickPosX = 5;
                    brickPosY += 35;
                } else if (data == ' ') {
                    brickPosX += 85;
                } else {
                    Brick brick = new Brick(brickPosX, brickPosY, data - 48);
                    bricks.add(brick);
                    brickPosX += 85;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void drawStage(ArkanoidGame game) {
        game.fill(200, 100, 30);
        game.stroke(255);
        game.strokeWeight(1);

        for (Brick b : bricks)
            game.rect(b.getPosX(), b.getPosY(), 80, 30);

    }

    boolean isStageClear() {
        if (bricks.size() == 0) {
            filePath = "./map2.txt";
            readFile();
            return true;
        }
        return false;
    }

    void checkCollisionBrick(Ball ball, List<Item> items) {
        for (int i = 0; i < bricks.size(); i++) {
            Brick b = bricks.get(i);
            if (isBrickRange(ball, b)) {

                if (!ball.isPower()) {
                    if (isHitBottom(ball, b) || isHitTop(ball, b)) {
                        ball.setDirY(-ball.getDir().getY());
                    } else if (isHitRight(ball, b) || isHitLeft(ball, b)) {
                        ball.setDirX(-ball.getDir().getX());
                    }
                }
                createItem(b, items);
                bricks.remove(b);
            }

        }

    }

    private boolean isBrickRange(Ball ball, Brick b) {

        return ball.getPos().getX() - 10 <= b.getPosX() + 80 && ball.getPos().getX() + 10 >= b.getPosX() &&
                ball.getPos().getY() - 10 <= b.getPosY() + 30 && ball.getPos().getY() + 10 >= b.getPosY();
    }

    private boolean isHitBottom(Ball ball, Brick b) {
        float brickMiddleY = b.getPosY() + 15;

        System.out.println("bottom");
        return ball.getPos().getY() - 10 <= b.getPosY() + 30 && ball.getPos().getY() - 10 >= brickMiddleY + 5;
    }

    private boolean isHitTop(Ball ball, Brick b) {
        float brickMiddleY = b.getPosY() + 15;
        System.out.println("top");

        return ball.getPos().getY() + 10 >= b.getPosY() && ball.getPos().getY() + 10 <= brickMiddleY - 5;
    }

    private boolean isHitRight(Ball ball, Brick b) {
        float brickMiddleX = b.getPosX() + 40;
        System.out.println("right");
        return ball.getPos().getX() - 10 <= b.getPosX() + 80 && ball.getPos().getX() - 10 >= brickMiddleX + 30;
    }

    private boolean isHitLeft(Ball ball, Brick b) {
        float brickMiddleX = b.getPosX() + 40;
        System.out.println("left");
        return ball.getPos().getX() + 10 >= b.getPosX() && ball.getPos().getX() + 10 <= brickMiddleX - 30;
    }

    private void createItem(Brick b, List<Item> items) {
        Item item = b.getItem();
        if (item != null)
            items.add(item);
    }


}
