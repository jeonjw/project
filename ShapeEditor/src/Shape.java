public abstract class Shape implements Cloneable {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private float strokeLevel;
    private Color color;

    public Shape(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.color = new Color();
        this.strokeLevel = 10;
    }

    public void setStrokeLevel(float ChangeLevel) {
        if (this.strokeLevel >= 0) {
            this.strokeLevel += -ChangeLevel;
            if (this.strokeLevel < 0) {
                this.strokeLevel = 0;
            }
        }
    }

    public abstract boolean contains(int posX, int posY);


    public void draw(ShapeEditor shapeEditor) {
        shapeEditor.strokeWeight(this.strokeLevel);
        shapeEditor.fill(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
    }

    public Shape clone() {
        try {
            Shape shape = (Shape) super.clone();
            shape.setPosX(this.getPosX() + 150);
            shape.color = this.color.clone();
            return shape;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setGrayLevel(float colorLevel) {
       color.setGrayLevel(colorLevel);
    }

    public void setRedColor(float colorLevel) {
        color.setRedColor(colorLevel);
    }

    public void setGreenColor(float colorLevel) {
        color.setGreenColor(colorLevel);
    }

    public void setBlueColor(float colorLevel) {
        color.setBlueColor(colorLevel);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
//반복문 조건문