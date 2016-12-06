import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ShapeEditor extends PApplet {

    Command command = null;
    Stack<Command> undo = new Stack<>();
    Stack<Command> redo = new Stack<>();
    private boolean isCtrlPressed;
    private boolean isDpressed;
    private boolean isZpressed;
    private boolean isYpressed;
    private boolean movingComplete;
    private int previousX;
    private int previousY;
    private Shape tempShape;
    private List<Shape> shapeList;

    public static void main(String[] args) {
        PApplet.main("ShapeEditor");
    }

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        background(0);
        stroke(120, 100, 50);
        rectMode(CENTER);
        shapeList = new ArrayList<>();
    }

    public void draw() {
        background(50);
        for (Shape shape : shapeList) {
            shape.draw(this);
        }
    }

    public void mouseClicked() {
        if (!keyPressed)
            return;
        addShape();
    }

    private Shape detectShape(int posX, int posY) {
        for (int i = shapeList.size() - 1; i >= 0; i--) {
            Shape shape = shapeList.get(i);
            if (shape.contains(posX, posY)) {
                return shape;
            }
        }
        return null;
    }

    public void mouseWheel(MouseEvent event) {
        if (!keyPressed)
            return;

        Shape shape = detectShape(mouseX, mouseY);
        if (shape == null)
            return;

        char keycode = Character.toUpperCase(key);
        float wheelValue = event.getCount();

        switch ((keycode)) {
            case 'S':
                command = new StrokeChangeCommand(shape, wheelValue);
                break;
            case 'C':
            case 'R':
            case 'G':
            case 'B':
                command = new ColorChangeCommand(shape, wheelValue, keycode);
                break;
        }
        if (command != null) {
            command.execute();
            command=null;
        }
    }

    private void addShape() {
        if (key == '1') {
            command = new AddCircleCommand(shapeList, mouseX, mouseY);
        } else if (key == '2') {
            command = new AddRectCommand(shapeList, mouseX, mouseY);
        }
        commandExecute(command);

    }

    public void keyPressed() {
        if (key == DELETE) {

            Shape shape = detectShape(mouseX, mouseY);
            command = new DeleteShapeCommand(shapeList, shape);
        } else if (keyCode == CONTROL)
            isCtrlPressed = true;
        else if ((char) keyCode == 'D') {
            isDpressed = true;
        } else if ((char) keyCode == 'Z') {
            isZpressed = true;
        } else if ((char) keyCode == 'Y') {
            isYpressed = true;
        }

        if (isCtrlPressed && isDpressed) {
            Shape shape = detectShape(mouseX, mouseY);
            if (shape != null) {
                command = new CopyShapeCommand(shapeList, shape);
            }

        } else if (isCtrlPressed && isZpressed) {
            if (undo.size() == 0)
                return;

            command = undo.pop();
            command.undo();
            redo.push(command);
            command = null;

        } else if (isCtrlPressed && isYpressed) {

            if (redo.size() != 0) {
                command = redo.pop();
            }
        }
        commandExecute(command);
    }


    public void keyReleased() {
        if (keyCode == CONTROL)
            isCtrlPressed = false;
        else if ((char) keyCode == 'D')
            isDpressed = false;
        else if ((char) keyCode == 'Z')
            isZpressed = false;
        else if ((char) keyCode == 'Y')
            isYpressed = false;
    }

    public void mouseDragged() {
        Shape shape = detectShape(mouseX, mouseY);

        if (shape == null)
            return;

        tempShape = shape;

        if (!movingComplete) {
            previousX = shape.getPosX();
            previousY = shape.getPosY();
            movingComplete = true;
        }
    }

    public void mouseReleased() {
        if (movingComplete) {
            if (tempShape == null) {
                return;
            }
            command = new ShapeMoveCommand(tempShape, mouseX, mouseY, previousX, previousY);
            commandExecute(command);
            movingComplete = false;
        }
    }

    public void commandExecute(Command command) {
        if (command != null) {
            command.execute();
            undo.push(command);
            this.command = null;
        }
    }
}