import processing.core.PApplet;

class Ball extends GameObject implements Cloneable {

    private float maxSpeed;
    private boolean power;
    private static long powerItemTime;
    private static long speedItemTime;

    static void setSpeedItemTime(long speedItemTime) {
        Ball.speedItemTime = speedItemTime;
    }

    static void setPowerItemTime(long powerItemTime) {
        Ball.powerItemTime = powerItemTime;
    }

    Ball(float dirX, float dirY) {
        super(dirX, dirY, new Vector2D(400, 300), 1);
        this.maxSpeed = 7;
    }

    public void update() {
        getPos().setX(PApplet.constrain(getPos().getX(), 0, 800));
        super.update();
        setVelocity(getVelocity() + 0.1f);
        if (getVelocity() > maxSpeed) setVelocity(maxSpeed);
        itemTimeCheck();
    }

    public void draw(ArkanoidGame game) {
        game.fill(255);
        game.noStroke();
        game.ellipse(getPos().getX(), getPos().getY(), 20, 20);
    }

    boolean checkCollisionWindow() {
        if (getPos().getX() < 0) setDirX(-getDir().getX());
        else if (getPos().getX() + 10 > 800) setDirX(-getDir().getX());

        if (getPos().getY() - 10 < 0) setDirY(-getDir().getY());
        else if (getPos().getY() + 10 > 600) return true;

        return false;

    }

    void checkCollisionBar(PlayerBar bar) {
        float distance;
        if (getPos().getY() + 10 > 580) {
            if (getPos().getX() < bar.getPos().getX() + 60 && getPos().getX() > bar.getPos().getX() - 60) {
                distance = (getPos().getX() - bar.getPos().getX()) / 60;
                setDir(new Vector2D(distance, -getDir().getY()));
                getDir().normalize();
                setPos(Vector2D.add(getPos(), Vector2D.mul(getDir(), getVelocity() * 2)));
            }
        }
    }

    public Ball clone() {
        try {
            Ball copy = (Ball) super.clone();
            copy.setDir(this.getDir().clone());
            copy.setPos(this.getPos().clone());
            copy.setDirX(copy.getDir().getX() * -0.3f);
            return copy;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    void setPower(boolean power) {
        this.power = power;
    }

    void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    boolean isPower() {
        return power;
    }

    private void itemTimeCheck() {
        long endTime = System.currentTimeMillis();
        int speedTime = (int) (endTime - speedItemTime) / 1000;
        int powerTime = (int) (endTime - powerItemTime) / 1000;

        if (speedTime == 5)
            setMaxSpeed(7);
        if (powerTime == 5)
            power = false;
    }

}
