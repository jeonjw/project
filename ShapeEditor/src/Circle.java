public class Circle extends Shape {

    private int radius;

    public Circle(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
        radius = width / 2;
    }

    public boolean contains(int posX, int posY) {
        int x = this.getPosX();
        int y = this.getPosY();

        return Math.sqrt(Math.pow((x - posX), 2) + Math.pow((y - posY), 2)) < radius;
    }

    public void draw(ShapeEditor shapeEditor) {
        super.draw(shapeEditor);
        shapeEditor.ellipse(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
    }

    public Shape clone() {
        Shape copy = super.clone();
        return copy;
    }
}

