package queue;

import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int currentLength = 0;
    public void enqueue(Object x) {
        assert x != null;
        doEnqueue(x);
        this.currentLength++;
    }

    public Object element() {
        assert this.currentLength > 0;
        return getElement();
    }

    public Object dequeue() {
        assert this.currentLength > 0;
        return doDequeue();
    }

    public int indexIf(Predicate<Object> cond) {
        int i = 0;
        setStartPosition();
        while (i < currentLength) {
            if (condIsTrue(cond)) {
                return i;
            }
            i++;
            setNextPosition();
        }
        return -1;
    }

    public int lastIndexIf(Predicate<Object> cond) {
        int i = 0;
        int res = -1;
        setStartPosition();
        while (i < currentLength) {
            if (condIsTrue(cond)) {
                res = i;
            }
            i++;
            setNextPosition();
        }
        return res;
    }

    public int size() {
        return this.currentLength;
    }

    public boolean isEmpty() {
        return this.currentLength <= 0;
    }

    public abstract void clear();

    protected abstract void doEnqueue(Object x);
    protected abstract Object getElement();
    protected abstract Object doDequeue();
    protected abstract void setStartPosition();
    protected abstract void setNextPosition();
    protected abstract boolean condIsTrue(Predicate<Object> cond);

}
