package search;

import java.util.Arrays;

public class BinarySearch {

    // Pred: (args.length >= 1) && (for any i in [0; args.length) args[i] in Z) &&
    // && (for any i in [1; args.length-1) args[i] >= args[i+1])
    // Post: (printed min i in [0; args.length-1): args[i+1] <= args[0]) OR
    //      OR (printed args.length - 1 in case when args[args.length-1] > args[0])
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        if (args.length == 1) {
            System.out.println(0);
        } else {
            int[] a = new int[args.length-1];
            for (int i = 1; i < args.length; i++) {
                a[i-1] = Integer.parseInt(args[i]);
            }
            int res1 = binSearchIter(x, a);
            int res2 = binSearchRec(x, 0, a.length, a);
            if (res1 != res2) {
                System.err.println("Error. Different results of binary search. Pls, fix the bug, buddy");
                System.err.println(Arrays.toString(a));
                System.err.println(x);
            } else {
                System.out.println(res1);
            }
        }
    }

    // Pred: (left = 0) && (right = arr.length) && (left' < right') &&
    //      && (for any i in [left'; right'-1) arr[i] >= arr[i+1]) &&
    //      && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
    // Post: (left' < right') &&
    //       && (for any i in [left'; right'-1) arr[i] >= arr[i+1]) &&
    //       && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
    private static int binSearchRec(int x, int left, int right, int[] arr) {
        // (for any i in [left'; right'-1) arr[i] >= arr[i+1]) && left' < right' &&
        // && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
        if (right - left <= 1) {
            // (for any i in [left'; right'-1) arr[i] >= arr[i+1]) && (left' < right') && (right' - left' <= 1)
            // && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x) -->
            // --> (right' = left' + 1) && (for any i in [0; left') arr[i] > x) &&
            // && (for any i in [right'; arr.length) arr[i] <= x)
            if (arr[left] <= x) {
                // (right' = left' + 1) && (for any i in [0; left') arr[i] > x) &&
                // && (for any i in [left' + 1; arr.length) arr[i] <= x) && (arr[left'] <= x) -->
                // --> left' = min i in [0; arr.length): arr[i] <= x
                return left;
                // Res = left'
            }
            // (right' = left' + 1) && (for any i in [0; left') arr[i] > x) &&
            // && (for any i in [left' + 1; arr.length) arr[i] <= x) && (arr[left'] > x) -->
            // --> (right' = min i in [0; arr.length): arr[i] <= x) || (right' = arr.length)
            return right;
            // Res = right'
        }
        // (for any i in [left'; right'-1) arr[i] >= arr[i+1]) && left' < right' && right' - left' > 1
        // && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x) -->
        // (for any i in [left'; right'-1) arr[i] >= arr[i+1]) && (left' < floor((left' + right') / 2) < right')
        // && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
        int mid = (left + right) / 2;
        // (for any i in [left'; right'-1) arr[i] >= arr[i+1]) && (left' < mid' < right')
        // && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
        if (arr[mid] <= x) {
            // (for any i in [left'; right'-1) arr[i] >= arr[i+1]) && (left' < mid' < right')
            // && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
            // && arr[mid'] <= x -->
            // --> (for any i in [left'; mid'-1) arr[i] >= arr[i+1]) && (left' < mid') &&
            // && (for any i in [0; left') arr[i] > x) && (for any i in [mid'; arr.length) arr[i] <= x
            return binSearchRec(x, left, mid, arr);
        }
        // (for any i in [left'; right'-1) arr[i] >= arr[i+1]) && (left' < mid' < right')
        // && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
        // && arr[mid'] > x -->
        // --> (for any i in [mid'; right'-1) arr[i] >= arr[i+1]) && (mid' < right') &&
        // && (for any i in [0; mid') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
        return binSearchRec(x, mid, right, arr);
    }

    // Pred: arr.length > 0 && for any i in [0; arr.length-1) arr[i] >= arr[i+1]
    // Post: (Res = min i in [0; arr.length-1]: arr[i] <= x) || (Res = arr.length in case when arr[arr.length-1] > x)
    private static int binSearchIter(int x, int[] arr) {
        // arr.length > 0 && for any i in [0; arr.length-1) arr[i] >= arr[i+1]
        int left = 0;
        int right = arr.length;
        int mid;
        // left = 0 && right = arr.length
        // (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x) && right' > left'
        while (right - left > 1) {
            //(for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x) &&
            // && right' - left' > 0 && right' - left' > 1
            //(for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
            // && right' - left' > 1 && (left' < floor((left' + right') / 2) < right')
            mid = (left + right) / 2;
            // (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
            // && right' - left' > 1 && (left' < mid' < right')
            if (arr[mid] <= x) {
                // for any i in [0; arr.length - 1) arr[i] >= arr[i+1] &&
                // && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
                // && right' - left' > 1 && (left' < mid' < right') && arr[mid'] <= x -->
                // --> (for any i in [0; left') arr[i] > x) && (for any i in [mid'; arr.length) arr[i] <= x)
                // && mid' > left' && arr[mid'] <= x
                right = mid;
                // (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
                // && right' > left' && arr[right'] <= x
            } else {
                // for any i in [0; arr.length - 1) arr[i] >= arr[i+1] &&
                // && (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
                // && right' - left' > 1 && (left' < mid' < right') && arr[mid'] > x -->
                // --> (for any i in [0; mid') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
                // && right' > mid' && arr[mid'] > x
                left = mid;
                // (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x)
                // && right' > left' && arr[left'] > x
            }
            // (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x) && right' > left'
        }
        // (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x) &&
        // && right' - left' > 0 && right' - left' <= 1 -->
        // --> (for any i in [0; left') arr[i] > x) && (for any i in [right'; arr.length) arr[i] <= x) &&
        // && right' = left' + 1
        if (arr[left] <= x) {
            //(for any i in [0; left') arr[i] > x) && (for any i in [left' + 1; arr.length) arr[i] <= x) &&
            // && right' = left' + 1 && arr[left'] <= x -->
            // --> left' = min i in [0; arr.length): arr[i] <= x
            return left;
            // Res = left'
        }
        //(for any i in [0; left') arr[i] > x) && (for any i in [left' + 1; arr.length) arr[i] <= x)
        // && right' = left' + 1 && arr[left'] > x -->
        // --> (right' = min i in [0; arr.length): arr[i] <= x) || (right' == arr.length)
        return right;
        // Res = right'
    }
}
