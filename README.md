# Paradigmas
IFMO, prog, Semester 2 (paradigms of programming)

## Homework 1 (Exception parser)
Add exceptions:
* parsing exception;
* evaluate exception:
  * DivisionByZeroException;
  * IntegerOutOfBoundsException.
Modification (MinMax and Zeroes):
* Realise operations `Min` and `Max` (minimal priority)
* Realise unary operations:
  * `l0` - leading count of zeroes in binary form of number;
  * `t0` - right count of zeroes in binary form of number.

## Homework 2 (Binary search)
* Realise iterative and recursive binary search
* Input: int x; array of integers sorted by non-growth. Output: min i: array\[i\] <= x
* For main-, iterativeBinSearch- and recursiveBinSearch-functions must be written contracts. For realisation of iterative and recursive binSearch must be provided evidences of compliance with contracts in terms of Hoar's triples
* The first command line argument is x, then array. Example of launch: `java search.BinarySearch 3 5 4 3 2 1`, expected result: `2`
Modification (Shift):
* Input: array sorted by growth, cyclically shift on `k` elements; all elemnts in array are different.
*  Output: find the `k`.

## Homework 3 (ArrayQueue)
* Define the model and find the invariant of data structure "Queue", realise <b>cycled</b> queue.
* Must be classe `ArrayQueueModule` (queue as module), `ArrayQueueADT` (queue as abstract data type), `ArrayQueue` (queue as class)
* Methods:
  * enqueue - add element in queue;
  * element - return the first element in queue;
  * dequeue - return the first element in queue and remove it from queue;
  * size - return current size of queue;
  * isEmpty - queue.size == 0 ? True : False;
  * clear - clean the queue;
  * Other methods if you need for queue (details of realisations).
* Write tests to queues.
Modification (Index):
* Add method:
  * indexOf(x) - min i: array[i] == x
  * lastIndexOf(x) - max i: array[i] == x
* In case when x not in queue - return `-1`.

## Homework 4 (LinkedQueue)
* Define interface of Queue and describe its contract
* Realise linkedQueue
* Similar part of ArrayQueue and LinkedQueue must be in abstract class
Modification (IndexIf):
* Realise methods:
  * indexIf(Predicate) - min i: array[i] sattisfied predicate;
  * lastIndexIf(Predicate) - max i: array[i] sattisfied predicate;
* In case when there is no such index - return `-1`.

## Homework 5 (Generic Expression)
