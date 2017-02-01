package ex1;

// Java => 전역 변수 X
//         Reference

// Singleton: 오직 한개의 객체를 만들고, 언제 어디서든 동일한 방법을 통해
//            접근 가능한 객체

// 1. Singleton 1
//   문제점
//     1. 특수한 경우에 객체가 생성될 수 있다.
//        Reflection, Serialization

//     2. Cursor.class 를 JVM에 로딩할 때 정적 객체가 생성됨으로
//        프로그램 시작에 악영향을 미칠수 있다.
class Cursor {
    private static final Cursor INSTANCE = new Cursor();
    private Cursor() {
        System.out.println("...");
    }

    public static Cursor getInstance() {
        return INSTANCE;
    }
}

public class Program {
    public static void main(String[] args) {
        System.out.println("main");
        // Cursor cursor = new Cursor();
        Cursor c1 = Cursor.getInstance();
        Cursor c2 = Cursor.getInstance();

        if (c1 == c2) {
            System.out.println("equal..");
        }

    }
}
