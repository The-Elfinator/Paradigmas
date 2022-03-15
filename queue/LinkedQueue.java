package queue;

import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {

    private Node head = null, tail = null;
    private Node pos;

    @Override
    protected void doEnqueue(Object x) {
        Node node = new Node(x, null);
        if (head == null) {
            // Current_size = 0
            this.head = node;
            this.tail = node;
        } else {
            // Current_size > 0
            this.head.next = node;
            this.head = node;
        }
    }

    @Override
    protected Object getElement() {
        return this.tail.value;
    }

    @Override
    protected Object doDequeue() {
        Object res = this.tail.value;
        this.tail = this.tail.next;
        this.currentLength--;
        if (this.currentLength == 0) {
            this.head = null;
        }
        return res;
    }

    @Override
    protected void setStartPosition() {
        this.pos = this.tail;
    }

    @Override
    protected void setNextPosition() {
        this.pos = this.pos.next;
    }

    @Override
    protected boolean condIsTrue(Predicate<Object> cond) {
        return cond.test(this.pos.value);
    }


    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.currentLength = 0;
    }

    private static class Node {
        Object value;
        Node next;

        public Node(Object value, Node next) {
            assert value != null;
            this.value = value;
            this.next = next;
        }
    }

}
