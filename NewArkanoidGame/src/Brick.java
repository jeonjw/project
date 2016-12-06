class Brick extends GameObject {
    private Item item;

    Brick(float posX, float posY, int itemType) {
        super(0, 0, new Vector2D(posX, posY), 1);
        setItem(itemType);
    }

    private void setItem(int itemType) {
        if (itemType == 1) {
            item = new Item(getPos().getX() + 40, getPos().getY() + 15, ItemEffect.SpeedUp);
        } else if (itemType == 2) {
            item = new Item(getPos().getX() + 40, getPos().getY() + 15, ItemEffect.SpeedDown);
        } else if (itemType == 3) {
            item = new Item(getPos().getX() + 40, getPos().getY() + 15, ItemEffect.Clone);
        } else if (itemType == 4) {
            item = new Item(getPos().getX() + 40, getPos().getY() + 15, ItemEffect.Power);
        } else {
            item = null;
        }
    }

    float getPosX() {
        return getPos().getX();
    }

    float getPosY() {
        return getPos().getY();
    }


    Item getItem() {
        return item;
    }

}