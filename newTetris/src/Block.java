import processing.core.PApplet;
import processing.core.PVector;

class Block {
    private PVector pos;
    private int[][] blocks;
    private int color;
    private int minX;
    private int maxX;
    private int maxY;


    Block(int color, int[][] blocks) {
        pos = new PVector(4, 0);
        this.color = color;
        this.blocks = blocks;
        findMinMax(blocks);
    }

    void drawBlock(PApplet g) {
        g.fill(color);
        g.stroke(200);
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 1)
                    g.rect((j + pos.x) * 40, (i + pos.y) * 40, 40, 40);
            }
    }

    void drawNextBlock(PApplet g) {
        g.fill(color);
        g.stroke(200);
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 1)
                    g.rect((9 + j + pos.x) * 40, (2 + i + pos.y) * 40, 40, 40);
            }
    }

    void moveDown() {
        pos.y += 1;
    }

    void moveLeft() {
        pos.x -= 1;
    }

    void moveRight() {
        pos.x += 1;
    }

    void space() {
        while (!checkBottom()) {
            moveDown();
        }
        fixBlock();
    }

    boolean checkBottom() {
        if (pos.y + maxY + 1 >= 20)
            return true;

        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 1)
                    if (Tetris.getTetrisBoard()[(int) pos.y + i + 1][(int) pos.x + j] == 1)
                        return true;
            }


        return false;
    }

    boolean checkLeftWall() {
        if (pos.x + minX == 0)
            return true;

        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 1)
                    if (Tetris.getTetrisBoard()[(int) pos.y + i][(int) pos.x + j - 1] == 1)
                        return true;
            }

        return false;

    }

    boolean checkRightWall() {
        if (pos.x + maxX == 9)
            return true;

        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 1)
                    if (Tetris.getTetrisBoard()[(int) pos.y + i][(int) pos.x + j + 1] == 1)
                        return true;
            }
        return false;
    }

    void fixBlock() {
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 1)
                    Tetris.getTetrisBoard()[(int) pos.y + i][(int) pos.x + j] = 1;
            }
    }

    private void findMinMax(int[][] blocks) {
        minX = 4;
        maxX = 0;
        maxY = 0;

        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++)
                if (blocks[i][j] == 1) {
                    if (j > maxX) maxX = j;

                    if (j < minX) minX = j;

                    if (i > maxY) maxY = i;
                }
    }

    void checkRotate() {
        int[][] rotateBlocks = new int[blocks[0].length][blocks.length];
        int tempX = (int) pos.x;
        int tempY = (int) pos.y;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                rotateBlocks[j][blocks.length - i - 1] = blocks[i][j];
            }
        }
        findMinMax(rotateBlocks);

        if (tempX + maxX + 1 > 10) {
            float count = tempX + maxX + 1 - 10;
            for (int i = 0; i < count; i++) {
                tempX -= 1;
            }
        }

        if (tempY + maxY + 1 > 20) {
            float count = tempY + maxY + 1 - 20;
            for (int i = 0; i < count; i++)
                tempY -= 1;
        }


        for (int i = 0; i < rotateBlocks.length; i++)
            for (int j = 0; j < rotateBlocks[0].length; j++)
                if (rotateBlocks[i][j] == 1)
                    if (Tetris.getTetrisBoard()[tempY + i][tempX + j] == 1) {
                        findMinMax(blocks);
                        return;
                    }

        rotate(rotateBlocks);
    }

    private void rotate(int[][] rotateBlocks) {
        if (pos.x + maxX + 1 > 10) {
            float count = pos.x + maxX + 1 - 10;
            for (int i = 0; i < count; i++) {
                pos.x -= 1;
            }
        }

        if (pos.y + maxY + 1 > 20) {
            float count = pos.y + maxY + 1 - 20;
            for (int i = 0; i < count; i++)
                pos.y -= 1;
        }
        blocks = rotateBlocks;
        findMinMax(blocks);
    }

}