package queue;

import java.util.function.Predicate;

public class MyLinkedQueueTest {
    public static void main(String[] args) {
        LinkedQueue queue = new LinkedQueue();
        //testHw3(queue);
        testHw4(queue);
    }

    private static void testHw3(LinkedQueue queue) {
        add(queue, 1, 10);
        delete(queue, 3);
        add(queue, 2, 5);
        delete(queue);
        add(queue, 3, 6);
        delete(queue, 1);
        queue.clear();
        System.out.println(queue.size());
    }

    private static void testHw4(LinkedQueue queue) {
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

    private static void add(LinkedQueue q, int c, int k) {
        for (int i = 0; i < k; i++) {
            q.enqueue("e_" + c + "_" + i);
        }
    }

    private static void delete(LinkedQueue q, int c) {
        for (int i = 0; i < c; i++) {
            System.out.println(q.element() + " " + q.dequeue() + " " + q.size());
        }
    }

    private static void delete(LinkedQueue q) {
        while (!q.isEmpty()) {
            System.out.println(q.element() + " " + q.dequeue() + " " + q.size());
        }
    }
}
