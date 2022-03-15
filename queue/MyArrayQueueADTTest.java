package queue;

public class MyArrayQueueADTTest {
    public static void main(String[] args) {
        ArrayQueueADT queue = new ArrayQueueADT();
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, "e_0_" + i);
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(ArrayQueueADT.element(queue) + " "
                    + ArrayQueueADT.dequeue(queue) + " " + ArrayQueueADT.size(queue));
        }
        for (int i = 0; i < 5; i++) {
            ArrayQueueADT.enqueue(queue, "e_1_" + i);
        }
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.element(queue) + " "
                    + ArrayQueueADT.dequeue(queue) + " " + ArrayQueueADT.size(queue));
        }
        System.out.println("Testing Index");
        ArrayQueueADT.enqueue(queue, "[a]");
        System.out.println(ArrayQueueADT.indexOf(queue, "[a]") + " "
                + ArrayQueueADT.lastIndexOf(queue, "[a]"));
        ArrayQueueADT.enqueue(queue, "[a]");
        System.out.println(ArrayQueueADT.indexOf(queue, "[a]") + " "
                + ArrayQueueADT.lastIndexOf(queue, "[a]"));
        ArrayQueueADT.clear(queue);
        System.out.println(ArrayQueueADT.indexOf(queue, "[a]") + " "
                + ArrayQueueADT.lastIndexOf(queue, "[a]"));
        System.out.println("Test passed");
    }
}
