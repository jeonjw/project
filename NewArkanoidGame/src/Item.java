public class Item extends GameObject {

    private ItemEffect effect;

    Item(float posX, float posY, ItemEffect effect) {
        super(0, 1, new Vector2D(posX, posY), 3);
        this.effect = effect;
    }

    public void draw(ArkanoidGame game) {
        game.fill(effect.getColor());
        game.noStroke();
        game.ellipse(getPos().getX(), getPos().getY(), 20, 20);
    }

    boolean deleteItem() {
        return this.getPos().getY() > 610;
    }

    ItemEffect getEffect() {
        return effect;
    }
}
