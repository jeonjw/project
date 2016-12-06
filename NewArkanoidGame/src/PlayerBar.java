import processing.core.PApplet;

import java.util.List;

class PlayerBar extends GameObject {

    private int barLength;

    PlayerBar(float dirX, float dirY) {
        super(dirX, dirY, new Vector2D(400, 580), 0);
        barLength = 120;
    }

    public void draw(ArkanoidGame game) {
        game.stroke(255);
        game.strokeWeight(5);
        game.line(getPos().getX() - (barLength / 2), getPos().getY(), getPos().getX() + (barLength / 2), getPos().getY());
    }

    public void update() {
        getPos().setX(PApplet.constrain(getPos().getX(), 0, 800));
        super.update();
        stopBar();
    }

    private void stopBar() {
        setVelocity(getVelocity() * 0.9f);
    }

    void accel(int key) {
        if (key == 39) {
            setVelocity(getVelocity() + 1f);
            if (getVelocity() > 10) setVelocity(10);
        } else if (key == 37) {
            setVelocity(getVelocity() - 1f);
            if (getVelocity() < -10) setVelocity(-10);

        }
    }

    boolean checkCollisionItem(Item item, List<Ball> balls) {
        if (item.getPos().getY() + 10 > 580) {
            if (item.getPos().getX() < this.getPos().getX() + 60 && item.getPos().getX() > this.getPos().getX() - 60) {
                item.getEffect().run(balls);
                return true;
            }
        }
        return false;
    }
}
