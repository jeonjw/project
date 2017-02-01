package ex4;

class Cursor {
    // private static Cursor sInstance;
    private Cursor() {
        System.out.println("Cursor()");
    }

    // 1. synchronized method
    //  => 성능에 악영향을 미친다.
//    public synchronized static Cursor getInstance() {
//        if (sInstance == null) {
//            sInstance = new Cursor();
//        }
//
//        return sInstance;
//    }

    // 2. DCLP(Double Check Locking Pattern)
    //  문제점: 가독성이 떨어진다.
    //    코드가 선언적이지 않다.
    //    Declarative Programming
//    public static Cursor getInstance() {
//        if (sInstance == null) {
//            synchronized (Cursor.class) {
//                if (sInstance == null) {
//                    sInstance = new Cursor();
//                }
//            }
//        }
//
//        return sInstance;
//    }

    // 3. Idiom(관용구)
    //    => IODH(Initialization On Demand Holder)
    //    => JLS(Java Language Spec)
    //    => 내부 정적 클래스의 객체는 .class가 로딩되는 시점에 생성되지 않는다.
    private static class Singleton {
        private static final Cursor INSTANCE = new Cursor();
    }

    public static Cursor getInstance() {
        return Singleton.INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println("main");
        Cursor c1 = Cursor.getInstance();
    }
}