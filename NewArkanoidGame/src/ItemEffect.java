import java.util.List;

enum ItemEffect {
    SpeedUp {
        void run(List<Ball> balls) {
            Ball.setSpeedItemTime(System.currentTimeMillis());
            for (Ball ball : balls)
                ball.setMaxSpeed(10);
        }

        int getColor() {
            return 0xFFFF0000;
        }
    },
    SpeedDown {
        void run(List<Ball> balls) {
            Ball.setSpeedItemTime(System.currentTimeMillis());
            for (Ball ball : balls)
                ball.setMaxSpeed(4);
        }

        int getColor() {
            return 0xFF00FF00;
        }
    },
    Clone {
        void run(List<Ball> balls) {
            balls.add(balls.get(0).clone());
        }

        int getColor() {
            return 0xFFFFFF00;
        }
    },
    Power {
        void run(List<Ball> balls) {
            Ball.setPowerItemTime(System.currentTimeMillis());
            for (Ball ball : balls)
                ball.setPower(true);
        }

        int getColor() {
            return 0xFF0000FF;
        }
    };

    abstract void run(List<Ball> balls);

    abstract int getColor();
}
