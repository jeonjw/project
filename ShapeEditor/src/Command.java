import java.util.List;


interface Command {

    void execute();

    default void undo() {
    }
}

abstract class AddCommand implements Command {
    int posX;
    int posY;
    private List<Shape> shapeList;

    public AddCommand(List<Shape> shapeList, int posX, int posY) {
        this.shapeList = shapeList;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void execute() {
        shapeList.add(createShape());
    }

    @Override
    public void undo() {
        shapeList.remove(shapeList.size() - 1);
    }

    protected abstract Shape createShape();
}

class AddCircleCommand extends AddCommand {
    private Shape shape;

    public AddCircleCommand(List<Shape> shapeList, int posX, int posY) {
        super(shapeList, posX, posY);
    }

    @Override
    protected Shape createShape() {
        this.shape = new Circle(posX, posY, 100, 100);
        return this.shape;
    }

}

class AddRectCommand extends AddCommand {
    private Shape shape;

    public AddRectCommand(List<Shape> shapeList, int posX, int posY) {
        super(shapeList, posX, posY);
    }

    @Override
    protected Shape createShape() {
        this.shape = new Rect(posX, posY, 100, 100);
        return this.shape;
    }
}

class DeleteShapeCommand implements Command {
    private List<Shape> shapeList;
    private Shape shape;

    public DeleteShapeCommand(List<Shape> shapeList, Shape shape) {
        this.shapeList = shapeList;
        this.shape = shape;
    }

    @Override
    public void execute() {
        shapeList.remove(shape);
    }

    public void undo() {
        shapeList.add(shape);
    }
}

class CopyShapeCommand implements Command {
    private List<Shape> shapeList;
    private Shape copyShape;

    public CopyShapeCommand(List<Shape> shapeList, Shape shape) {
        this.shapeList = shapeList;
        this.copyShape = shape.clone();

    }

    @Override
    public void execute() {
        shapeList.add(this.copyShape);
    }

    public void undo() {
        shapeList.remove(shapeList.size() - 1);
    }
}

class StrokeChangeCommand implements Command {

    private Shape shape;
    private float wheelValue;

    public StrokeChangeCommand(Shape shape, float wheelValue) {
        this.shape = shape;
        this.wheelValue = wheelValue;
    }

    @Override
    public void execute() {
        shape.setStrokeLevel(wheelValue);
    }
}


class ShapeMoveCommand implements Command {
    private Shape shape;
    private int posX;
    private int posY;
    private int previousX;
    private int previousY;

    public ShapeMoveCommand(Shape shape, int posX, int posY, int previousX, int previousY) {
        this.shape = shape;
        this.posX = posX;
        this.posY = posY;
        this.previousX = previousX;
        this.previousY = previousY;
    }


    @Override
    public void execute() {
        shape.setPosX(posX);
        shape.setPosY(posY);
    }

    public void undo() {
        shape.setPosX(previousX);
        shape.setPosY(previousY);
    }
}

class ColorChangeCommand implements Command {
    private Shape shape;
    private float wheelValue;
    private char keycode;

    public ColorChangeCommand(Shape shape, float wheelValue, char keycode) {
        this.shape = shape;
        this.wheelValue = wheelValue;
        this.keycode = keycode;

    }

    @Override
    public void execute() {
        switch ((keycode)) {
            case 'C':
                shape.setGrayLevel(wheelValue);
                break;
            case 'R':
                shape.setRedColor(wheelValue);
                break;
            case 'G':
                shape.setGreenColor(wheelValue);
                break;
            case 'B':
                shape.setBlueColor(wheelValue);
                break;
        }
    }
}