package search;

import java.util.Arrays;

public class BinarySearchShift {

    //Pred: (args.length >= 1) && (args[i] in Z) && (args[i] == args[j] --> i == j) &&
    //      && (Exist k in [0; args.length]: args moved at k -->
    //      --> for any i in [0; args.length-1) args[i] < args[i+1])
    //Post: found such k in [0; args.length]
    public static void main(String[] args) {
        int[] a = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            a[i] = Integer.parseInt(args[i]);
        }
        if (a[0] <= a[a.length - 1]) {
            System.out.println(0);
        } else {
            int k1 = binSearchLimIter(a);
            int k2 = binSearchLimRec(0, a.length - 1, a);
            if (k1 != k2) {
                System.err.println("Different results. Pls fix the bug, buddy");
                System.err.println(Arrays.toString(a));
                System.err.println(k1 + " " + k2);
            } else {
                System.out.println(k1 + 1);
            }
        }
    }

    // Pred: (arr.length > 1) && (arr[i] == arr[j] --> i == j) && (Exist k in [0; arr.length]: arr moved at k -->
    //       --> for any i in [0; arr.length-1) arr[i] < arr[i+1]) && (arr[0] > arr[arr.length-1]) &&
    //      && (right = arr.length) && (left = 0) && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
    //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
    // Post: (arr.length > 1) && (arr[i] == arr[j] --> i == j) && (Exist k in [0; arr.length]: arr moved at k -->
    //       --> for any i in [0; arr.length-1) arr[i] < arr[i+1]) && (arr[0] > arr[arr.length-1]) &&
    //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
    //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
    private static int binSearchLimRec(int left, int right, int[] arr) {
        // (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
        //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        if (right - left <= 1) {
            // (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
            //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
            //      && (right' - left' <= 1)
            // left' = Ind of max(arr); right' = Ind of min(arr) --> arr was moved on left'+1
            return left;
        }
        // (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
        //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        //      && (right' - left' > 1)
        // (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
        //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        //      && (left' < floor((left' + right') / 2) < right'
        int mid = (right + left) / 2;
        // (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
        //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        //      && (left' < mid' < right')
        if (arr[mid] < arr[left]) {
            // (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
            //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
            //      && (left' < mid' < right') && (arr[mid'] < arr[left'])
            // (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
            //      && (for any i in [mid'; arr.length-1) arr[i] < arr[i+1])
            //      && (left' < mid') && (arr[mid'] < arr[left'])
            return binSearchLimRec(left, mid, arr);
        }
        // (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
        //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        //      && (left' < mid' < right') && (arr[mid'] > arr[left'])
        // (for any i in [0; mid'-1) arr[i] < arr[i+1]) &&
        //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        //      && (mid' < right') && (arr[mid'] > arr[right'])
        return binSearchLimRec(mid, right, arr);
    }

    // Pred: (arr.length > 1) && (arr[i] == arr[j] --> i == j) && (Exist k in [0; arr.length]: arr moved at k -->
    //      --> for any i in [0; arr.length-1) arr[i] < arr[i+1]) && (arr[0] > arr[arr.length-1])
    // Post: Res = k - 1
    private static int binSearchLimIter(int[] arr) {
        // arr[0] > arr[arr.length - 1]
        int left = 0;
        int right = arr.length - 1;
        int mid;
        // a[left'] > a[right'] && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
        //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        while (right - left > 1) {
            // (a[left'] > a[right']) && (right' - left' > 1) && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
            //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
            // (a[left'] > a[right']) && (right' - left' > 1) && (left' < floor((right' + left') / 2) < right') &&
            //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
            //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
            mid = (right + left) / 2;
            // (a[left'] > a[right']) && (right' - left' > 1) && (left' < mid' < right') &&
            //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
            //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
            if (arr[mid] < arr[left]) {
                // (a[left'] > a[right']) && (right' - left' > 1) && (left' < mid' < right') && arr[mid'] < arr[left'] &&
                //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
                //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
                right = mid;
                // (a[left'] > a[right']) && (right' - left' > 1) && (left' < right') && arr[right'] < arr[left'] &&
                //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
                //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])

                // (a[left'] > a[right']) && (right' - left' > 1) &&
                //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
                //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
            } else {
                // (a[left'] > a[right']) && (right' - left' > 1) && (left' < mid' < right') && (arr[mid'] > arr[left']) &&
                //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
                //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
                left = mid;
                // (a[left'] > a[right']) && (right' - left' > 1) && (left' < right') && (arr[left'] > arr[right']) &&
                //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
                //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
                // (a[left'] > a[right']) && (right' - left' > 1) &&
                //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
                //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
            }
            // (a[left'] > a[right']) && (right' - left' > 1) &&
            //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
            //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        }
        // a[left'] > a[right'] && (right' - left' <= 1) &&
        //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
        //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        // a[left'] > a[right'] && (right' = left' + 1) &&
        //      && (for any i in [0; left'-1) arr[i] < arr[i+1]) &&
        //      && (for any i in [right'; arr.length-1) arr[i] < arr[i+1])
        // Remembered about Pred: arr[left'] = max(arr) && arr[right'] = min(arr) -->
        // --> arr was moved on left' + 1
        return left;
    }
}
