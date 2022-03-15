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
