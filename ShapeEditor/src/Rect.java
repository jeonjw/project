public class Rect extends Shape {


    public Rect(int xPos, int yPos, int weight, int height) {
        super(xPos, yPos, weight, height);
    }

    public boolean contains(int posX, int posY) {
        int x = this.getPosX();
        int y = this.getPosY();
        int width = this.getWidth();
        int height = this.getHeight();

        return (posX > x - (width / 2) && posX < x + (width / 2) &&
                posY > y - (height / 2) && posY < y + (height / 2));
    }

    public void draw(ShapeEditor shapeEditor) {
        super.draw(shapeEditor);
        shapeEditor.rect(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
    }

    public Shape clone() {
        Shape copy = super.clone();
        return copy;
    }
}