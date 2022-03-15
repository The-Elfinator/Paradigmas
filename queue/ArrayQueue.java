package queue;

import java.util.Arrays;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {

    private Object[] elements = new Object[8];
    private int head = -1, tail = -1;
    private int pos;
    // My comments: NOT CONTRACT
    // head - where to push new elements
    // tail - from where to pop old elements
    // define: (tail; head]

    @Override
    protected void doEnqueue(Object x) {
        if (this.tail == -1) {
            increaseLengthNotCycle(this.currentLength + 1);
        } else {
            increaseLengthCycle(this.currentLength + 1);
        }
        this.elements[++this.head] = x;
    }

    private void increaseLengthNotCycle(int len) { //Head More than Tail
        if (len > this.elements.length) {
            this.elements = Arrays.copyOf(this.elements, 2*this.elements.length);
        }
    }

    private void increaseLengthCycle(int len) { //Head Less than Tail
        if (this.head == this.elements.length - 1 && this.currentLength != this.elements.length) {
            this.head = -1;
        }
        if (len > elements.length) {
            int oldLength = this.currentLength;
            int cnt = oldLength - 1 - this.tail;
            this.elements = Arrays.copyOf(this.elements, 2*oldLength);
            for (int i = 0; i < cnt; i++) {
                this.elements[this.elements.length - i - 1] = this.elements[oldLength - i - 1];
                this.elements[oldLength - i - 1] = null;
            }
            this.tail = this.elements.length - cnt - 1;
        }
    }

    @Override
    protected Object getElement() {
        if (this.tail == this.elements.length - 1) {
            this.tail = -1;
        }
        return this.elements[this.tail+1];
    }

    @Override
    protected Object doDequeue() {
        if (this.tail >= this.elements.length - 1) {
            this.tail = -1;
        }
        this.tail++;
        Object res = this.elements[this.tail];
        this.elements[this.tail] = null;
        this.currentLength--;
        return res;
    }

    @Override
    protected void setStartPosition() {
        this.pos = this.tail + 1;
        if (this.pos == this.elements.length) {
            this.pos = 0;
        }
    }

    @Override
    protected void setNextPosition() {
        this.pos++;
        if (this.pos == this.elements.length) {
            this.pos = 0;
        }
    }

    @Override
    protected boolean condIsTrue(Predicate<Object> cond) {
        return cond.test(elements[this.pos]);
    }

    public int indexOf(Object elem) {
        if (elem == null) {
            return -1;
        }
        int i = 0;
        int pos = this.tail + 1;
        if (pos == this.elements.length) {
            pos = 0;
        }
        while (i < this.currentLength) {
            if (elem.equals(this.elements[pos])) {
                return i;
            }
            pos++;
            if (pos == this.elements.length) {
                pos = 0;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexOf(Object elem) {
        if (elem == null) {
            return -1;
        }
        int res = -1;
        int i = this.currentLength - 1;
        int pos = this.head;
        if (pos == this.elements.length) {
            pos--;
        }
        while (i >= 0) {
            if (elem.equals(this.elements[pos])) {
                return i;
            }
            pos--;
            if (pos < 0) {
                pos = this.elements.length - 1;
            }
            i--;
        }
        return res;
    }


    @Override
    public void clear() {
        this.elements = new Object[8];
        this.head = -1;
        this.tail = -1;
        this.currentLength = 0;
    }

}
