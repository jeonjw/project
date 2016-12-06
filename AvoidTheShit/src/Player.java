public class Player {


    private float posX;
    private float acceleration;
    private float speed;
    private int beforeKey;
    private int energy;

//circleX += (targetX - circleX) * easing;
    public Player(float posX) {
        this.posX = posX;
        this.beforeKey = 0;
        this.acceleration=6/4;
        this.speed =1;
        this.energy = 100;

    }

    public void drawPlayer(AvoidShit game) {

        posX = game.constrain(posX,15,785); //0 785 변수로 설정받기
        game.fill(100,200,130);
        game.ellipse(posX, 580, 40, 40); //크기도 변수로 받기
    }

    public void setPlayerPosition(int keyCode) {
        if(beforeKey==keyCode){
            speed+=acceleration;
        }else{
            speed=1;
        }

        beforeKey = keyCode;

        if (keyCode == 37) {
            posX-=speed;
        } else if (keyCode == 39) {
            posX+=speed;
        }

    }

    public float getPosX() {

        return posX;
    }
    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
