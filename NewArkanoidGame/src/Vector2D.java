class Vector2D implements Cloneable {
    private float x;
    private float y;

    Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    static Vector2D mul(Vector2D direction, float velocity) {
        return new Vector2D(direction.x * velocity, direction.y * velocity);
    }

    static Vector2D add(Vector2D pos, Vector2D speed) {
        return new Vector2D(pos.x + speed.x, pos.y + speed.y);
    }

    private float distance() {
        return (float) (Math.sqrt(x * x + y * y));
    }

    void normalize() {
        float dist = distance();
        this.x /= dist;
        this.y /= dist;
    }

    public Vector2D clone() {
        try {
            return (Vector2D) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    void setX(float x) {
        this.x = x;
    }

    void setY(float y) {
        this.y = y;
    }
}
