# Paradigmas
IFMO, prog, Semester 2 (paradigms of programming)

# Java

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
* Add in expression.parser with 3 variables ability to calculate an expressions in various modes (BigInteger, Integer, Double):
  * Add class `GenericTabulator ` implements interface `expression.generic.Tabulator`
```Java
public interface Tabulator {
    Object[][][] tabulate(
        String mode, String expression, 
        int x1, int x2, int y1, int y2, int z1, int z2
    ) throws Exception;
}
```
  * `mode` - in which type we need to calculate: `i` - int with exceptions (overflow), `d` - double, `bi` - BigInteger;
  * `expression` - calculated expression which must be parsed;
  * `x1, x2; y1, y2; z1, z2` - variable change range (inclusive);
  * Must be returned Object\[\]\[\]\[\] where Result\[i\]\[j\]\[k\] = expression.evaluate(x1 + i, y1 + j, z1 + k) where i = 0..x2-x1, j and k - similarly. If we catched overflowError then Result\[i\]\[j\]\[k\] is `null`.
* Update interface of command line:
  * 1-st argument of command line is mode: `-i`, `-d` or `-bi` - obviously integer-, double- or BigInteger-mode;
  * 2-nd argument - expression;
  * Result: expression.evaluate for x in \[-2; 2\], y in \[-2; 2\] and z in \[-2; 2\].

Modification (Cmm and CmmUls):
 * Realise unary operation `count` - number of set bits: count 5 -> 2;
 * Realise binary operation `min` and `max`;
 * Add modes: 
   * `u` - Integer without overflow;
   * `l` - Long without overflow;
   * `s` - Short without overflow.

# JavaScript

## Homework 6 (Functional expressions on JavaScript)
* Realise functions `cnst`, `variable`, `add`, `subtract`, `multiply`, `divide`, `negate` for calculating expressions with 3 variables;
* Sample expression:
```JavaScript
let expr = subtract(
    multiply(
        cnst(2),
        variable("x")
    ),
    cnst(3)
);

println(expr(5, 0, 0));
```
Output is `7`
* Code have to calculate expression `x^2-2x+1` for x in \[0; 10\];
* __Hard mode__: realise function `parse` that parses expression in *Reverse Polish Notation*. Example: `parse("x x 2 - * x * 1 +")(5)`, result is `76`;

Modification (PieSinhCosh):
* Add constants `PI` and `E`
* `sinh` - hyperbolic sin, `(sinh 3)` nearly more 10
* `cosh` - hyperbolic cos, `(cosh 3)` nearly less 10

## Homework 7 (Object expressions on JavaScript)

Realise classes `Const`, `Variable`, `Add`, `Subtract`, `Multiply`, `Divide`, `Negate` for calculating and representation expressions with 3 variables x, y and z:
Here is example for representation of expression `2x-3`:
```JavaScript
let expr = new Subtract(
    new Multiply(
        new Const(2),
        new Variable("x")
    ),
    new Const(3)
);

println(expr.evaluate(5, 0, 0));
```
