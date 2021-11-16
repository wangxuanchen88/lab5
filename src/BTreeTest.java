import org.junit.Test;

import static org.junit.Assert.*;

public class BTreeTest {
    /*
        WARNING: do not edit the toString methods within BTree.java. They are used in testing and even a single
        character being off, such as an additional space (' ') will result in a failed test! You have been adequately
        warned to avoid changes to the toString methods, if you still edit these methods you may receive zero marks.

        For feedback, we have provided you with a visualisation of what your implementation 'should' look like
        in terms of order, root and children. To help beyond that, we have also provided a graphical representation
        of your tree.

        We advise you write additional tests to increase the confidence of your implementation. Simply getting these
	    tests correct does not mean your solution is robust enough to pass. See lab 4's AVLTreeTest.java for help on
	    how to write tests.

        Lastly, also check out:
        https://www.cs.usfca.edu/~galles/visualization/BTree.html
        for visualisation of BTree operations.
     */

    @Test(timeout = 1000)
    public void insertInOrderWithoutSplitTest() {
        // Avoid inserting to the point of causing a split.
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(2);

        // Check insertion was successful
        String expected = "[2]";
        assertEquals(
                "Insertion without split failed with order 3.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=3, root={keys=[2], children=[]}}",
                bTree.toString()
        );

        bTree.insert(1);
        expected = "[1, 2]";
        assertEquals(
                "\nInsertion without split failed with order 3.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=3, root={keys=[1, 2], children=[]}}",
                bTree.toString()
        );

        // Try the same but with order 6
        bTree = new BTree<>(6);
        bTree.insert(10);
        bTree.insert(7);
        bTree.insert(8);
        expected = "[7, 8, 10]";
        assertEquals(
                "\nInsertion without split failed with order 6.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=6, root={keys=[7, 8, 10], children=[]}}",
                bTree.toString()
        );

        bTree.insert(15);
        bTree.insert(9);
        expected = "[7, 8, 9, 10, 15]";
        assertEquals(
                "\nInsertion without split failed with order 6.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=6, root={keys=[7, 8, 9, 10, 15], children=[]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void singleSplitAtRootTest() {
        // Cause a split at the root node.
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);

        String expected = "[2]\n├─[1]\n├─[3]";
        assertEquals(
                "\nFailed to appropriately split at the root appropriately with order 3.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=3, root={keys=[2], children=[{keys=[1], children=[]}, {keys=[3], children=[]}]}}",
                bTree.toString()
        );

        // Attempt the same with order 5
        bTree = new BTree<>(5);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);
        bTree.insert(4);
        bTree.insert(5);

        expected = "[3]\n├─[1, 2]\n├─[4, 5]";
        assertEquals(
                "\nFailed to appropriately split at the root appropriately with order 5.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=5, root={keys=[3], children=[{keys=[1, 2], children=[]}, {keys=[4, 5], children=[]}]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void multiSplitTest() {
        // Cause a split at a node that is not the root (will cause a split at root to get to this point).
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);
        bTree.insert(4);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(7);

        String expected = "[4]\n├─[2]\n	├─[1]\n	├─[3]\n├─[6]\n	├─[5]\n	├─[7]";
        assertEquals(
                "\nFailed to appropriately split at multiple nodes with order 3.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=3, root={keys=[4], children=[{keys=[2], children=[{keys=[1], children=[]}, {keys=[3], children=[]}]}, {keys=[6], children=[{keys=[5], children=[]}, {keys=[7], children=[]}]}]}}",
                bTree.toString()
        );

        // Cause split the other way (left child) with greater order
        bTree = new BTree<>(7);
        bTree.insert(16);
        bTree.insert(15);
        bTree.insert(14);
        bTree.insert(13);
        bTree.insert(12);
        bTree.insert(11);
        bTree.insert(10);
        bTree.insert(9);
        bTree.insert(8);
        bTree.insert(7);
        bTree.insert(6);
        bTree.insert(5);
        bTree.insert(4);
        bTree.insert(3);
        bTree.insert(2);
        bTree.insert(1);

        expected = "[5, 9, 13]\n├─[1, 2, 3, 4]\n├─[6, 7, 8]\n├─[14, 15, 16]\n├─[10, 11, 12]";
        assertEquals(
                "\nFailed to appropriately split at multiple nodes with order 7.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=7, root={keys=[5, 9, 13], children=[{keys=[1, 2, 3, 4], children=[]}, {keys=[6, 7, 8], children=[]}, {keys=[10, 11, 12], children=[]}, {keys=[14, 15, 16], children=[]}]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void splitMiddleChild() {
        // Cause a situation where you must split the middle child.
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(5);
        bTree.insert(12);
        bTree.insert(75);
        bTree.insert(125);
        bTree.insert(220);
        bTree.insert(100);
        bTree.insert(105);

        String expected = "[100]\n├─[12]\n	├─[5]\n	├─[75]\n├─[125]\n	├─[105]\n	├─[220]";
        assertEquals(
                "Failed to appropriately split middle child with order 3.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=3, root={keys=[100], children=[{keys=[12], children=[{keys=[5], children=[]}, {keys=[75], children=[]}]}, {keys=[125], children=[{keys=[105], children=[]}, {keys=[220], children=[]}]}]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void manyInsertTest() {
        // Insert a lot of numbers with order 3
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);
        bTree.insert(4);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(7);
        bTree.insert(8);
        bTree.insert(9);
        bTree.insert(10);
        bTree.insert(11);
        bTree.insert(12);
        bTree.insert(13);
        bTree.insert(14);
        bTree.insert(15);

        // Check that the insertion is correct.
        String expected = "[8]\n├─[4]\n	├─[2]\n		├─[1]\n		├─[3]\n	├─[6]\n		├─[5]\n		├─[7]\n├─[12]\n	├─[10]\n		├─[9]\n		├─[11]\n	├─[14]\n		├─[13]\n		├─[15]";
        assertEquals(
                "\nAdvanced insertion failed with order 3.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=3, root={keys=[8], children=[{keys=[4], children=[{keys=[2], children=[{keys=[1], children=[]}, {keys=[3], children=[]}]}, {keys=[6], children=[{keys=[5], children=[]}, {keys=[7], children=[]}]}]}, {keys=[12], children=[{keys=[10], children=[{keys=[9], children=[]}, {keys=[11], children=[]}]}, {keys=[14], children=[{keys=[13], children=[]}, {keys=[15], children=[]}]}]}]}}",
                bTree.toString()
        );

        // Insert a lot of numbers with order 5
        bTree = new BTree<>(5);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);
        bTree.insert(4);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(7);
        bTree.insert(8);
        bTree.insert(9);
        bTree.insert(10);
        bTree.insert(11);
        bTree.insert(12);
        bTree.insert(13);
        bTree.insert(14);
        bTree.insert(15);

        // Check that the insertion is correct.
        expected = "\n[3, 6, 9, 12]\n├─[1, 2]\n├─[4, 5]\n├─[7, 8]\n├─[10, 11]\n├─[13, 14, 15]";

        assertEquals(
                "\nAdvanced insertion failed with order 5.\nYour tree should look like:\n" + expected + "\nBut it actually looks like:\n" + bTree.display(),
                "{order=5, root={keys=[3, 6, 9, 12], children=[{keys=[1, 2], children=[]}, {keys=[4, 5], children=[]}, {keys=[7, 8], children=[]}, {keys=[10, 11], children=[]}, {keys=[13, 14, 15], children=[]}]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void maxWithoutSplitTest() {
        // Create a BTree and ensure the root has no children
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);

        String expected = "[1, 2]";

        // Check that the maximum is correct.
        assertEquals(
                "\n.max() failed with order 3.\nYour tree should look like:\n" + expected + "\nand return 2 as its maximum but actually looks like:\n" +bTree.display() + "\nand returns " + bTree.max() +" as its maximum",
                2,
                (int) bTree.max()
        );

        // Try again with different order
        bTree = new BTree<>(7);
        bTree.insert(4);
        bTree.insert(12);
        bTree.insert(8);
        bTree.insert(6);
        bTree.insert(1);

        expected = "[1, 4, 6, 8, 12]";

        // Check that the maximum is correct.
        assertEquals(
                "\n.max() failed with order 7.\nYour tree should look like:\n" + expected + "\nand return 12 as its maximum but actually looks like:\n" +bTree.display() + "\nand returns " + bTree.max() +" as its maximum",
                12,
                (int) bTree.max()
        );

        // Add to limit of BTree before it splits
        bTree.insert(75);

        expected = "[1, 4, 6, 8, 12, 75]";

        // Check that the maximum is correct.
        assertEquals(
                "\n.max() failed with order 7.\nYour tree should look like:\n" + expected + "\nand return 75 as its maximum but actually looks like:\n" +bTree.display() + "\nand returns " + bTree.max() +" as its maximum",
                75,
                (int) bTree.max()
        );
    }

    @Test(timeout = 1000)
    public void maxWithSplitTest() {
        // Create a BTree and ensure the root has children (splits at one point).
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);

        String expected = "[2]\n├─[1]\n├─[3]";

        // Check that the maximum is correct.
        assertEquals(
                "\n.max() failed with order 3.\nYour tree should look like:\n" + expected + "\nand return 3 as its maximum but actually looks like:\n" +bTree.display() + "\nand returns " + bTree.max() +" as its maximum",
                3,
                (int) bTree.max()
        );

        // Create mote splits
        bTree.insert(7);
        bTree.insert(5);
        bTree.insert(80);
        bTree.insert(10);

        expected = "[5]\n├─[2]\n	├─[1]\n	├─[3]\n├─[10]\n	├─[7]\n	├─[80]";

        // Check that the maximum is correct.
        assertEquals(
                "\n.max() failed with order 3.\nYour tree should look like:\n" + expected + "\nand return 80 as its maximum but actually looks like:\n" +bTree.display() + "\nand returns " + bTree.max() +" as its maximum",
                80,
                (int) bTree.max()
        );

        // Try again with different order
        bTree = new BTree<>(5);
        bTree.insert(14);
        bTree.insert(12);
        bTree.insert(87);
        bTree.insert(62);
        bTree.insert(11);
        bTree.insert(16);
        bTree.insert(90);
        bTree.insert(75);
        bTree.insert(30);
        bTree.insert(20);
        bTree.insert(10);
        bTree.insert(58);
        bTree.insert(20);
        bTree.insert(18);
        bTree.insert(68);
        bTree.insert(38);
        bTree.insert(8);
        bTree.insert(2);
        bTree.insert(77);
        bTree.insert(43);
        bTree.insert(23);
        bTree.insert(42);

        expected = "[30]\n├─[10, 14, 20]\n	├─[2, 8]\n	├─[11, 12]\n	├─[20, 23]\n	├─[16, 18]\n├─[58, 75]\n	├─[38, 42, 43]\n	├─[62, 68]\n	├─[77, 87, 90]";

        // Check that the maximum is correct.
        assertEquals(
                "\n.max() failed with order 5.\nYour tree should look like:\n" + expected + "\nand return 80 as its maximum but actually looks like:\n" +bTree.display() + "\nand returns " + bTree.max() +" as its maximum",
                90,
                (int) bTree.max()
        );
    }
}