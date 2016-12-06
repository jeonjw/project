public class Player {


    private float posX;
    private float posY;
    private float acceleration;
    private float speed;
    private int beforeKey;
    private int energy;
    private float tempPosX;
    private Ball beforeTouch;

    public Player(float posX) {
        this.posX = posX;
        this.beforeKey = 0;
        this.acceleration = 6 / 4;
        this.speed = 3;
        this.energy = 100;
        this.posY = 580;
        this.tempPosX = posX;

    }

    public void drawPlayer(AvoidBall game) {

        posX += (tempPosX-posX)*0.08;

        tempPosX = game.constrain(tempPosX,0,800);
        posX = game.constrain(posX, 15, 785);


        game.stroke(255,0,0);
        game.strokeWeight(4);
        game.line(posX-50,posY-40,(posX-50)+energy,posY-40);

        game.fill(100, 200, 130);
        game.strokeWeight(0);
        game.ellipse(posX, posY, 40, 40);
    }

    public void setPlayerPosition(int keyCode) {
        if (beforeKey == keyCode) {
            speed += acceleration;
        } else {
            speed = 3;
        }

        beforeKey = keyCode;

        if (keyCode == 37) {
            tempPosX -= speed;
        } else if (keyCode == 39) {
            tempPosX += speed;
        }

    }

    public boolean checkCollision(Ball ball) {

        double distanceX = ball.getPosX() - posX;
        double distanceY = ball.getPosY() - posY;
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        if (beforeTouch == ball) {
            return false;
        }

        if (distance <= 30) {
            beforeTouch = ball;
            setEnergy(energy - 25);
            return true;
        }
        return false;
    }

    public Ball getBeforeTouch() {
        return beforeTouch;
    }

    public void setBeforeTouch(Ball beforeTouch) {
        this.beforeTouch = beforeTouch;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}