package queue;

import java.util.Arrays;

public class ArrayQueueADT {

    // Model: a[1] .. a[n]
    // Invariant: for i = 1..n a[i] != null

    private int currentLength = 0;
    private Object[] elements = new Object[8];
    private int head = -1, tail = -1;
    // My comments: NOT CONTRACT
    // head - where to push new elements
    // tail - from where to pop old elements
    // define: (tail; head]

    // enqueue(x) <=> push(x)
    // Pred: x != null
    // Post: n' = n + 1 && for i = 1..n a'[i] = a[i] && a[n'] = x
    public static void enqueue(ArrayQueueADT queue, Object x) {
        assert x != null;
        if (queue.tail == -1) {
            increaseLengthNotCycle(queue, queue.currentLength + 1);
        } else {
            increaseLengthCycle(queue, queue.currentLength + 1);
        }
        queue.elements[++queue.head] = x;
        queue.currentLength++;
    }


    private static void increaseLengthNotCycle(ArrayQueueADT queue, int len) { //Head More than Tail
        if (len > queue.elements.length) {
            queue.elements = Arrays.copyOf(queue.elements, 2*queue.elements.length);
        }
    }

    private static void increaseLengthCycle(ArrayQueueADT queue, int len) { //Head Less than Tail
        if (queue.head == queue.elements.length - 1 && queue.currentLength != queue.elements.length) {
            queue.head = -1;
        }
        if (len > queue.elements.length) {
            int oldLength = queue.currentLength;
            int cnt = oldLength - 1 - queue.tail;
            queue.elements = Arrays.copyOf(queue.elements, 2*oldLength);
            for (int i = 0; i < cnt; i++) {
                queue.elements[queue.elements.length - i - 1] = queue.elements[oldLength - i - 1];
                queue.elements[oldLength - i - 1] = null;
            }
            queue.tail = queue.elements.length - cnt - 1;
        }
    }

    // element() <=> get_first()
    // Pred: n > 0
    // Post: R = a[1] && for i = 1..n a'[i] == a[i] && n' == n
    public static Object element(ArrayQueueADT queue) {
        if (queue.tail == queue.elements.length - 1) {
            queue.tail = -1;
        }
        return queue.elements[queue.tail+1];
    }

    // dequeue <=> remove_first() - return first
    // Pred: n > 0
    // Post: R = a[1] && for i in 2..n: a'[i-1] = a[i] && n' = n - 1
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.currentLength > 0;
        if (queue.tail >= queue.elements.length - 1) {
            queue.tail = -1;
        }
        queue.tail++;
        Object res = queue.elements[queue.tail];
        queue.elements[queue.tail] = null;
        queue.currentLength--;
        return res;
    }

    // lastIndexOf(elem) <=> last index of elements in queue
    // Pred: n > 0
    // Post: (R = max(i): a[i] = elem || R = -1 in case when elem not in queue) && for i = 1 .. n a'[i] = a[i] && n' = n
    public static int lastIndexOf(ArrayQueueADT queue, Object elem) {
        if (elem == null) {
            return -1;
        }
        int res = -1;
        int i = queue.currentLength - 1;
        int pos = queue.head;
        if (pos == queue.elements.length) {
            pos--;
        }
        while (i >= 0) {
            if (elem.equals(queue.elements[pos]))
                return i;
            pos--;
            if (pos < 0) {
                pos = queue.elements.length - 1;
            }
            i--;
        }
        return res;
    }

    // indexOf(elem) <=> first index of elements in queue
    // Pred: n > 0
    // Post: (R = min(i): a[i] = elem || R = -1 in case when elem not in queue) && for i = 1 .. n a'[i] = a[i] && n' = n
    public static int indexOf(ArrayQueueADT queue, Object elem) {
        if (elem == null) {
            return -1;
        }
        int i = 0;
        int pos = queue.tail + 1;
        if (pos == queue.elements.length) {
            pos = 0;
        }
        while (i < queue.currentLength) {
            if (elem.equals(queue.elements[pos])) {
                return i;
            }
            pos++;
            if (pos == queue.elements.length) {
                pos = 0;
            }
            i++;
        }
        return -1;
    }

    // size
    // Pred: True
    // Post: R = n && for i = 1 .. n a'[i] = a[i] && n' = n
    public static int size(ArrayQueueADT queue) {
        return queue.currentLength;
    }

    // isEmpty
    // Pred: True
    // Post: R = (n <= 0) && for i = 1 .. n a'[i] = a[i] && n' = n
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.currentLength <= 0;
    }

    // clear
    // Pred: True
    // Post: for i = 1 .. n a'[i] = null && n' = 0
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[8];
        queue.head = -1;
        queue.tail = -1;
        queue.currentLength = 0;
    }

}
