public class Shit {


    private float posX;
    private float posY;
    private float acceleration;
    private float speed;


    public Shit(float posX, float posY, float acceleration) {
        this.posX = posX;
        this.posY = posY;
        this.acceleration = (acceleration / 40);
        speed = 1;
    }

    public void drawShit(AvoidShit game) {
        game.fill(150, 20, 30);
        game.ellipse(posX, posY, 30, 30);
    }

    public void moveShit() {
        speed += acceleration;
        posY += speed;
    }

    public boolean checkCollision(float playerX, float playerY) {
        double distanceX = playerX - posX;
        double distanceY = playerY - posY;
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        if (distance <= 28) {
            return true;
        }
        return false;
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
