package queue;

import java.util.function.Predicate;

public class MyArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        //testHw3(queue);
        testHw4(queue);
    }

    private static void testHw4(ArrayQueue queue) {
        queue.enqueue("[a]");
        queue.enqueue("Hello");
        queue.enqueue("[a]");
        queue.enqueue("[b]");
        Predicate<Object> cond = x -> !x.equals("[a]");
        System.out.println(queue.indexIf(cond));
        System.out.println(queue.lastIndexIf(cond));

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        Predicate<Object> cond1 = x -> !x.equals("[b]");
        System.out.println(queue.indexIf(cond));
        System.out.println(queue.lastIndexIf(cond));
    }

    private static void testHw3(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue("e_0_" + i);
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(queue.element() + " " +
                    queue.dequeue() + " " + queue.size());
        }
        for (int i = 0; i < 5; i++) {
            queue.enqueue("e_1_" + i);
        }
        while (!queue.isEmpty()) {
            System.out.println(queue.element() + " " +
                    queue.dequeue() + " " + queue.size());
        }
        queue.enqueue("[a]");
        // System.out.println(queue.element());
        System.out.println(queue.indexOf("[a]") + " " + queue.lastIndexOf("[a]"));
        queue.enqueue("[a]");
        System.out.println(queue.indexOf("[a]") + " " + queue.lastIndexOf("[a]"));
        queue.clear();
        System.out.println(queue.indexOf("[a]") + " " + queue.lastIndexOf("[a]"));
        System.out.println("If you have correct answers than congratulations. You fixed the bug!!!");

    }
}
