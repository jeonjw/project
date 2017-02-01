package ex3;

// lazy initialization(늦은 초기화)

// 아래처럼 만들었을 경우 문제점이 있다.
//  => Thread Safety 가 없다.

class Cursor {
    private static Cursor sInstance;
    private Cursor() {}
    public static Cursor getInstance() {
        if (sInstance == null) {
            sInstance = new Cursor();
        }

        return sInstance;
    }
}


public class Program {
    public static void main(String[] args) {
        Cursor c1 = Cursor.getInstance();
        Cursor c2 = Cursor.getInstance();

        if (c1 == c2) {
            System.out.println("equal");
        }
    }
}
