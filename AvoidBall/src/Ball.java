public class Ball {


    private float posX;
    private float posY;
    private float acceleration;
    private float speed;


    public Ball(float posX, float posY, float acceleration) {
        this.posX = posX;
        this.posY = posY;
        this.acceleration = (acceleration / 40);
        speed = 1;
    }

    public void drawBall(AvoidBall game) {
        game.fill(150, 150, 30);
        game.ellipse(posX, posY, 30, 30);
    }

    public void moveBall() {
        speed += acceleration;
        posY += speed;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
