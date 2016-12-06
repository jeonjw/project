class GameObject {
    private Vector2D dir;
    private Vector2D pos;
    private float velocity;

    void setPos(Vector2D pos) {
        this.pos = pos;
    }

    float getVelocity() {
        return velocity;
    }

    void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    GameObject(float dirX, float dirY, Vector2D pos, float velocity) {
        dir = new Vector2D(dirX, dirY);
        dir.normalize();
        this.pos = pos;
        this.velocity = velocity;
    }

    public void update() {
        setPos(Vector2D.add(getPos(), Vector2D.mul(getDir(), getVelocity())));
    }

    public void draw(ArkanoidGame game) {

    }

    void setDirX(float dirX) {
        dir.setX(dirX);
    }

    void setDirY(float dirY) {
        dir.setY(dirY);
    }

    Vector2D getDir() {
        return dir;
    }

    Vector2D getPos() {
        return pos;
    }

    void setDir(Vector2D dir) {
        this.dir = dir;
    }

}
