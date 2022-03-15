package queue;

public class MyArrayQueueModuleTest {

    public static void main(String[] args) {
        test1();
        test2(10, 5, "Test2 is passed!");
        test2(8, 3, "Test3 is passed!");
        test4();
        test5();
        testInd();
        testBug();
    }

    private static void testBug() {
        ArrayQueueModule.enqueue(1);
        ArrayQueueModule.dequeue();
        ArrayQueueModule.enqueue("[b]");
        ArrayQueueModule.dequeue();
        ArrayQueueModule.enqueue("1");
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.enqueue("[a]");
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.enqueue("3");
        ArrayQueueModule.dequeue();
        ArrayQueueModule.enqueue("[a]");
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.enqueue("Hello");
        ArrayQueueModule.enqueue("[b]");
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.enqueue("1");
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.enqueue("[a]");
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.enqueue("1");
        ArrayQueueModule.dequeue();
        ArrayQueueModule.enqueue("Hello");
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.dequeue();
        System.out.println(ArrayQueueModule.element());
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.enqueue("[a]");
        ArrayQueueModule.dequeue();
        System.out.println(ArrayQueueModule.element());
        ArrayQueueModule.enqueue("Hello");
        ArrayQueueModule.dequeue();
        ArrayQueueModule.dequeue();
        ArrayQueueModule.enqueue("world");
        ArrayQueueModule.enqueue("[a]");
        ArrayQueueModule.enqueue("2");
        System.out.println(ArrayQueueModule.element());
    }

    private static void testInd() {
        System.out.println("Testing Index");
        ArrayQueueModule.enqueue("Hello");
        ArrayQueueModule.enqueue("World");
        System.out.println(ArrayQueueModule.indexOf("Hello"));
        System.out.println(ArrayQueueModule.lastIndexOf("Hello"));
        ArrayQueueModule.clear();
        ArrayQueueModule.enqueue("[a]");
        System.out.println(ArrayQueueModule.indexOf("[a]") + " " + ArrayQueueModule.lastIndexOf("[a]"));
        ArrayQueueModule.enqueue("[a]");
        System.out.println(ArrayQueueModule.indexOf("[a]") + " " + ArrayQueueModule.lastIndexOf("[a]"));
        ArrayQueueModule.clear();
        System.out.println(ArrayQueueModule.indexOf("[a]") + " " + ArrayQueueModule.lastIndexOf("[a]"));
        System.out.println("Test passed");
    }

    private static void test5() {
        ArrayQueueModule.clear();
        int c = 1;
        for (int i = 0; i < 3; i++) {
            ArrayQueueModule.enqueue("Elem_" + c);
            c++;
        }
        System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " " + ArrayQueueModule.dequeue());
        for (int i = 0; i < 4; i++) {
            ArrayQueueModule.enqueue("Elem_" + c);
            c++;
        }
        c = getC(c);
        c = getC(c);
        ArrayQueueModule.enqueue("Elem_" + c);
        System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " "  + ArrayQueueModule.dequeue());
        System.out.println("Test5 is passed!");
        ArrayQueueModule.clear();
    }

    private static int getC(int c) {
        System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " " + ArrayQueueModule.dequeue());
        System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " "  + ArrayQueueModule.dequeue());
        ArrayQueueModule.enqueue("Elem_" + c);
        c++;
        System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " "  + ArrayQueueModule.dequeue());
        return c;
    }

    private static void test4() {
        for (int i = 0; i < 8; i++) {
            ArrayQueueModule.enqueue("Elem_" + (i+1));
        }
        for (int i = 0; i < 7; i++) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " " + ArrayQueueModule.dequeue());
        }
        ArrayQueueModule.enqueue("Elem_Last");
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.dequeue());
        }
        System.out.println("Test4 is passed!");
    }

    private static void test2(int i2, int i3, String s) {
        for (int i = 0; i < i2; i++) {
            ArrayQueueModule.enqueue("Elem_" + (i + 1));
        }
        for (int i = 0; i < i3; i++) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.dequeue());
        }
        for (int i = 0; i < 3; i++) {
            ArrayQueueModule.enqueue("Elem_1_" + (i + 1));
        }
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.dequeue());
        }
        System.out.println(s);
    }

    private static void test1() {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue("e_" + i);
        }
        System.out.println(ArrayQueueModule.dequeue());
        System.out.println(ArrayQueueModule.size());
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " " + ArrayQueueModule.dequeue());
        }
        System.out.println("Test1 is passed!");
    }
}
