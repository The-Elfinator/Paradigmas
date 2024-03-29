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
 * Realise classes `Const`, `Variable`, `Add`, `Subtract`, `Multiply`, `Divide`, `Negate` for calculating and representation expressions with 3 variables *x*, *y* and _z_:
   * Here is example for representation of expression `2x-3`:
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
   * When calculating such an expression, instead of each variable, its value is substituted, passed as an argument to the evaluate method. Thus, the result of calculating the given example should be the number 7.
   * _toString_ method must return the expression written in [Reverse Polish notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation). Example: _expr.toString()_ must return ` 2 x * 3 - `
 * _parse_ function should return parsed object expression
 * __Hard variant__: method _diff("x")_ must return the expression which is the derivative of original expression by variable _x_. Example: _expr.diff("x")_ should return the expression which is equivalent to `new Const(2)` (expressions `new Subtract(new Const(2), new Const(0))` and 
```
new Subtract(
    new Add(
        new Multiply(new Const(0), new Variable("x")),
        new Multiply(new Const(2), new Const(1))
    )
    new Const(0)
)
```
will be correct answer).

Modification (SinhCosh):
 * Realise unary operations `Sinh` - hyperbolic sinus and `Cosh` - hyperbolic cosinus

## Homework 8 (Exceptions on JS)
 * Add function `parsePrefix()` to Homework 7 which parses expression like `(- (* 2 x) 3)` . If the expression isn't correct then method `parsePrefix` must return error human-read message
 * Add function `prefix()` to Homework 7 which returns the expression in prefix form
 
 Modification (MeanVar):
 * Realise operations of an arbitrary number of arguments
   * `Mean` - math expectation of arguments, `(mean 1 2 6)` equals 3
   * `Var` - variance of arguments, `(var 2 5 11)` equals 14
 
# Clojure

## Homework 9 (linear algebra on Clojure)
 * Realise functions to work with objects from linear algebra:
   * scalar - number;
   * vector - vector of numbers;
   * matrix - vector of vectors of numbers;
 * Operations with vectors:
   * _v+/v-/v*/vd_ - coordinate addition/subtraction/multiplication/division;
   * _scalar/vect_ - scalar/vector multiplication;
   * _v*s_ - multiplication of vector and scalar;
 * Operations with matrixes:
   * _m+/m-/m*/md_ - piecemeal addition/subtraction/multiplication/division;
   * _m*s_ - multiplication on scalar;
   * _m*v_ - multiplication on vector;
   * _m*m_ - multiplication of 2 matrixes;
   * _transpose_ - transposition;

 Modification (Shapeless):
 * Add operations of piecemeal addition/subtraction/multiplication/division (_s+/s-/s*/sd_) numbers and vectors of an arbitary length. Example: `(s+ [[1 2] 3] [[4 5] 6])` should return `[[5 7] 9]`.
 
## Homework 10 (Functional expressions)
 * Realise functions `constant`, `variable`, `add`, `subtract`, `multiply`, `divide` and `negate` for arithmetic operations
 * Here is an example of representation expression `2*x-3`:
```
(def expr
  (subtract
    (multiply
      (constant 2)
      (variable "x"))
    (constant 3)))
```
 * The expression must be a function that returns the value of the expression when substituting variables specified by the mapping. Examle: `(expr {"x" 2})` equals 1
 * Realise parser of prefix form expression, e.g. `(parseFunction "(- (* 2 x) 3)")` must be equal to `expr`
Modification (PowLog):
 * realise binary operations: `pow` (Exponentiation), e.g. `(pow 2 3)` equals to 8; `log` (Logarithm of the absolute value on the basis of the absolute value), e.g. `(log -2 -8)` equals to 3
 
 ## Homework 11 (Object expressions)
  * Realise constructors `Constant`, `Variable`, `Add`, `Subtract`, `Multiply`, `Divide` and `Negate` for arithmetic operations:
    * Here is an example with an expression `2*x-3`:
    ```
    (def expr
      (Subtract
        (Multiply
          (Constant 2)
          (Variable "x"))
        (Constant 3)))
    ```
    * Function `(evaluate expression vars)` should evaluate an `expression` for variables specified by the mapping `vars`, e.g. `(evaluate expr {"x" 2})` must return 1
    * Function `(toString expression)` should return the expression in prefix form
    * Function `(parseObject "expression")` should parse an expression written in prefix form, e.g. `(parseObject "(- (* 2 x) 3)")` should be equals to _expr_
    * Function `(diff expression "variable")` should return the expression which is the derivative of original expression by variable _x_. Example: `(diff expr "x")` should return the expression which is equivalent to `(Constant 2)` (expressions `(Subtract (Constant 2) (Constant 0))` and 
```
(Subtract
  (Add
    (Multiply (Constant 0) (Variable "x"))
    (Multiply (Constant 2) (Constant 1)))
  (Constant 0))
```
will be correct answer).

Modification (PowLog):
 * Realise functions `Pow` (exponentiation) and `Log` (logarithm of the absolute value on the basis of the absolute value)
 
## Homework 12 (Combinatorial parsers)
 * Realise function `(parseObjectSuffix "expression")` which parses an expression written in the suffix-form and function `toStringSuffix` which returns the expression written in the suffix-form, e.g. `(toStringSuffix (parseObjectSuffix "( ( 2 x * ) 3 - )"))` must return `((2 x *) 3 -)`

Modification (Variables and Bitwise):
 * Realise supporting of variables consisting of an arbitary number of letters _XYZ_ in any case
 * Realise bit operations:
   * `BitAnd` (`&`) - And: `5 & 6` is 4
   * `BitOr` (`|`) - Or: `5 | 6` is 7
   * `BitXor` (`^`) - Xor: `5 ^ 6` is 3

# Prolog

## Homework 13 (Primes on Prolog)
 * Make rules:
   * prime(N) - checking if N is primal
   * composite(N) - checking if N is not primal
   * prime_divisors(N, Divisors) - checking if the list Divisors contains all primal divisors of N, sorted in ascending order
 
 Modification (Unique)
  * Make rule `unique_prime_divisors(N, Divisors)` - returns prime divisors without repeatting, e.g. `unique_prime_divisors(99, [3, 11])`.

