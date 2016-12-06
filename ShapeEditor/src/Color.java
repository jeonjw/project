public class Color implements Cloneable {
    private float grayLevel;
    private float red;
    private float green;
    private float blue;

    public Color() {
        this.grayLevel = 255;
    }

    public void setGrayLevel(float colorLevel) {
        this.grayLevel += colorLevel * 10;
        if (this.grayLevel > 255) {
            this.grayLevel = 255;
        } else if (this.grayLevel < 0) {
            this.grayLevel = 0;
        }
        this.red = this.grayLevel;
        this.green = this.grayLevel;
        this.blue = this.grayLevel;
    }

    public void setRedColor(float colorLevel) {
        this.red -= colorLevel * 5;
        if (this.red > 255) {
            this.red = 255;
        } else if (this.red < 0) {
            this.red = 0;
        }
    }

    public void setGreenColor(float colorLevel) {
        this.green -= colorLevel * 5;
        if (this.green > 255) {
            this.green = 255;
        } else if (this.green < 0) {
            this.green = 0;
        }
    }

    public void setBlueColor(float colorLevel) {
        this.blue -= colorLevel * 5;
        if (this.blue > 255) {
            this.blue = 255;
        } else if (this.blue < 0) {
            this.blue = 0;
        }
    }

    public Color clone() {
        try {
            Color color = (Color) super.clone();
            return color;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public float getRed() {
        return red;
    }
    public float getGreen() {
        return green;
    }
    public float getBlue() {
        return blue;
    }

}
