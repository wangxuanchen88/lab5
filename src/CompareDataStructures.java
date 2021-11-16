import java.util.Random;

/**
 * A class which simply demonstrates the efficiency (in human time) of different data structures.
 * <p>
 * Here we will compare calculating the range (max-min) of an array vs your BTree!
 */
public class CompareDataStructures {
    public static void main(String[] args) {
        /*
            To compare our implementation speeds we will generate random numbers.
            Then we will count how long it takes for us to get the range from
            our array vs our BTree. Feel free to compare against the AVL tree as well!

            Note that you can only run this test after you have properly implemented
            the BTree insertion, split and .max() (both task 1 and 2).

            P.s. If you have also completed Lab 4's AVLTree than you can compare that
            as well to the BTree (see CompareDataStructures.java in Lab 4).
         */
        int[] values = new int[10000000]; // Feel free to edit this to be even larger!
        Random rand = new Random();
        for (int i = 0; i < values.length; i++) {
            values[i] = rand.nextInt();
        }

        // Create a BTree to test
        BTree<Integer> bTree = new BTree<>(21); // Feel free to edit the order.
        for (int i = 1; i < values.length; i++) {
            bTree.insert(values[i]);
        }

        // Now time each
        long startTime = System.nanoTime();
        int range = getRangeFromArray(values);
        long endTime = System.nanoTime();
        System.out.println("The array took: " + (endTime - startTime) + " nanoseconds and provided a range of " + range);

        startTime = System.nanoTime();
        range = getRangeFromTree(bTree);
        endTime = System.nanoTime();
        System.out.println("The BTree took: " + (endTime - startTime) + " nanoseconds and provided a range of " + range);
    }

    /**
     * @param array of integers.
     * @return range of array.
     */
    private static int getRangeFromArray(int[] array) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int val : array) {
            if (val < min)
                min = val;
            if (val > max)
                max = val;
        }

        return min < 0 ? max + min : max - min;
    }

    /**
     * @param tree<Integer> BTree which holds type integer.
     * @return range of the BTree.
     */
    private static int getRangeFromTree(BTree<Integer> tree) {
        int max = tree.max();
        int min = tree.min();
        return min < 0 ? max + min : max - min;
    }
}
