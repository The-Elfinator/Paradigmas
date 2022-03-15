package queue;

import java.util.Arrays;

public class ArrayQueueModule {

    // Model: a[1] .. a[n]
    // Invariant: for i = 1..n a[i] != null

    private static int currentLength = 0;
    private static Object[] elements = new Object[8];
    private static int head = -1, tail = -1;
    private static final boolean log = false;
    // My comments: NOT CONTRACT
    // head - where to push new elements
    // tail - from where to pop old elements
    // define: (tail; head]

    // enqueue(x) <=> push(x)
    // Pred: x != null
    // Post: n' = n + 1 && for i = 1..n a'[i] = a[i] && a[n'] = x
    public static void enqueue(Object x) {
        if (log) {
            System.err.println("Push(" + x + ") " + "head= " + head + " tail= " + tail + " size= " + currentLength);
        }
        assert x != null;
        if (tail == -1) {
            increaseLengthNotCycle(currentLength + 1);
        } else {
            increaseLengthCycle(currentLength + 1);
        }
        elements[++head] = x;
        currentLength++;
    }


    private static void increaseLengthNotCycle(int len) { //Head More than Tail
        if (len > elements.length) {
            elements = Arrays.copyOf(elements, 2*elements.length);
        }
    }

    private static void increaseLengthCycle(int len) { //Head Less than Tail
        if (head == elements.length - 1 && currentLength != elements.length) {
            head = -1;
        }
        if (len > elements.length) {
            int oldLength = currentLength;
            int cnt = oldLength - 1 - tail;
            elements = Arrays.copyOf(elements, 2*oldLength);
            for (int i = 0; i < cnt; i++) {
                elements[elements.length - i - 1] = elements[oldLength - i - 1];
                elements[oldLength - i - 1] = null;
            }
            tail = elements.length - cnt - 1;
        }
    }

    // element() <=> get_first()
    // Pred: n > 0
    // Post: R = a[1] && for i = 1..n a'[i] == a[i] && n' == n
    public static Object element() {
        if (log) {
            System.err.println("element() " + "head= " + head + " tail= " + tail + " size= " + currentLength);
        }
        if (tail == elements.length - 1) {
            tail = -1;
        }
        return elements[tail+1];
    }

    // dequeue <=> remove_first() - return first
    // Pred: n > 0
    // Post: R = a[1] && for i in 2..n: a'[i-1] = a[i] && n' = n - 1
    public static Object dequeue() {
        if (log) {
            System.err.println("Pop(); head= " + head + " tail= " + tail + " size= " + currentLength);
        }
        assert currentLength > 0;
        if (tail >= elements.length - 1) {
            tail = -1;
        }
        tail++;
        Object res = elements[tail];
        elements[tail] = null;
        currentLength--;
        return res;
    }

    // lastIndexOf(elem) <=> last index of elements in queue
    // Pred: n > 0
    // Post: (R = max(i): a[i] = elem || R = -1 in case when elem not in queue) && for i = 1 .. n a'[i] = a[i] && n' = n
    public static int lastIndexOf(Object elem) {
        if (elem == null) {
            return -1;
        }
        int res = -1;
        int i = currentLength - 1;
        int pos = head;
        if (pos == elements.length) {
            pos--;
        }
        while (i >= 0) {
            if (elem.equals(elements[pos]))
                return i;
            pos--;
            if (pos < 0) {
                pos = elements.length - 1;
            }
            i--;
        }
        return res;
    }

    // indexOf(elem) <=> first index of elements in queue
    // Pred: n > 0
    // Post: (R = min(i): a[i] = elem || R = -1 in case when elem not in queue) && for i = 1 .. n a'[i] = a[i] && n' = n
    public static int indexOf(Object elem) {
        if (elem == null) {
            return -1;
        }
        int i = 0;
        int pos = tail + 1;
        if (pos == elements.length) {
            pos = 0;
        }
        while (i < currentLength) {
            if (elem.equals(elements[pos])) {
                return i;
            }
            pos++;
            if (pos == elements.length) {
                pos = 0;
            }
            i++;
        }
        return -1;
    }

    // size
    // Pred: True
    // Post: R = n && for i = 1 .. n a'[i] = a[i] && n' = n
    public static int size() {
        return currentLength;
    }

    // isEmpty
    // Pred: True
    // Post: R = (n <= 0) && for i = 1 .. n a'[i] = a[i] && n' = n
    public static boolean isEmpty() {
        return currentLength <= 0;
    }

    // clear
    // Pred: True
    // Post: for i = 1 .. n a'[i] = null && n' = 0
    public static void clear() {
        elements = new Object[8];
        head = -1;
        tail = -1;
        currentLength = 0;
        if (log) {
            System.err.println("clear(); head= " + head + " tail= " + tail + " size= " + currentLength);
        }
    }
}
