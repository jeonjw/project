package ex2;

// 문제점 1 해결책
//   => Enum Singleton
//   => Reflection, Serialization 를 통한 객체가 생성되는 문제가 발생하지 않는다.

// 프로그램 시작시 로딩하는 싱글톤을 만드는 방법

enum Cursor {
    INSTANCE
}

public class Program {
    public static void main(String[] args) {

        Cursor c1 = Cursor.INSTANCE;
        Cursor c2 = Cursor.INSTANCE;

        if (c1 == c2) {
            System.out.println("equal");
        }
    }
}
