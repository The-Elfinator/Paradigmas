package queue;

import java.util.function.Predicate;

public interface Queue {

    // Model: a[1] .. a[n]
    // Invariant: for i = 1..n a[i] != null

    // enqueue(x) <=> push(x)
    // Pred: x != null
    // Post: n' = n + 1 && for i = 1..n a'[i] = a[i] && a[n'] = x
    void enqueue(Object x);

    // element() <=> get_first()
    // Pred: n > 0
    // Post: R = a[1] && for i = 1..n a'[i] == a[i] && n' == n
    Object element();

    // dequeue <=> remove_first() - return first
    // Pred: n > 0
    // Post: R = a[1] && for i in 2..n: a'[i-1] = a[i] && n' = n - 1
    Object dequeue();

    // size
    // Pred: True
    // Post: R = n && for i = 1 .. n a'[i] = a[i] && n' = n
    int size();

    // isEmpty
    // Pred: True
    // Post: R = (n <= 0) && for i = 1 .. n a'[i] = a[i] && n' = n
    boolean isEmpty();

    // clear
    // Pred: True
    // Post: for i = 1 .. n a'[i] = null && n' = 0
    void clear();

    // indexIf (cond) <=> first index of Object where cond(Object) == true
    // Pred: n > 0
    // Post: (Res = min(i): cond(a[i]) == true || Res = -1 in case when for any i cond(a[i]) == false) &&
    //      && (n' = n) && (for i = 1..n a'[i] = a[i])
    int indexIf(Predicate<Object> condition);

    // lastIndexIf (cond) <=> last index of Object where cond(Object) == true
    // Pred: n > 0
    // Post: (Res = max(i): cond(a[i]) == true || Res = -1 in case when for any i cond(a[i]) == false) &&
    //      && (n' = n) && (for i = 1..n a'[i] = a[i])
    int lastIndexIf(Predicate<Object> condition);
}
