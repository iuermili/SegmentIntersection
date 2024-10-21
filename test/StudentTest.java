import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.Random;

import static java.lang.Math.log;

public class StudentTest {

    /**
     * TODO: Test cases
     */
    @Test
    public void test() {
        insertSmallBST();
        dupsSmallBST();
        largeRandomBSTTest();
        largeRandomAVLTest();
        testLeftRightRotation();
        testRightLeftRotation();
    }

    @Test
    public void insertSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] a = new int[]{4, 8, 0, 2, 6, 10,14,2,3,9,22};
        /*
         *       4
         *     /  \
         *    /    \
         *   0      8
         *    \    / \
         *     2  6   10
         */
        for (Integer key : a) {
            bst.insert(key);
            map.put(key, key);
        }

        bst.remove(4);
        map.remove(4);

        for (int i = 0; i != 11; ++i) {
            assertEquals(bst.contains(i), map.containsKey(i));
        }


    }

    @Test
    public void dupsSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] a = new int[]{4, 4,4,4};
        /*
         *       4
         *     /  \
         *    /    \
         *   0      8
         *    \    / \
         *     2  6   10
         */
        for (Integer key : a) {
            bst.insert(key);
            map.put(key, key);
        }
        assertEquals(bst.size(), map.size());

        for (int i = 0; i != 11; ++i) {
            assertEquals(bst.contains(i), map.containsKey(i));
        }
    }

    @Test
    public void largeRandomBSTTest() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Random random = new Random();
        int size = 1000;
        int maxValue = 100;

        for (int i = 0; i < size; i++) {
            int key = random.nextInt(maxValue);
            bst.insert(key);
            map.put(key, key);
        }

        assertEquals( map.size(), bst.size());

        for (int key : map.keySet()) {
            assertTrue(bst.contains(key));
        }

        for (int i = 0; i < 1000; i++) {
            int key = random.nextInt(maxValue * 2);
            assertEquals(map.containsKey(key), bst.contains(key));
        }

        BinarySearchTree.Node<Integer> curr = bst.root;
        assertTrue(height_check(curr));

        for (int i = 0; i < 100; i++) {
            int key = random.nextInt(maxValue);
            bst.remove(key);
            assertFalse(bst.contains(key));
        }

        BinarySearchTree.Node<Integer> new_curr = bst.root;
        assertTrue(height_check(new_curr));
    }


    public boolean height_check(BinarySearchTree.Node<Integer> curr) {
        if (curr == null) return true;
        int expected_height = 1 + Math.max(get_height(curr.left), get_height(curr.right));
        if (curr.height == expected_height) {
            return (height_check(curr.left) && height_check(curr.right));
        } else {
            return false;
        }
    }

    public int get_height(BinarySearchTree.Node<Integer> n) {
        if (n == null) return -1;
        else return n.height;
    }

    @Test
    public void largeRandomAVLTest() {
        AVLTree<Integer> avl = new AVLTree<>((Integer x, Integer y) -> x < y);
        Random random = new Random();
        int size = 10000;
        int maxValue = 10000;

        for (int i = 0; i < size; i++) {
            int key = random.nextInt(maxValue);
            avl.insert(key);
        }

        validate_AVL_property(avl);

        for (int i = 0; i < 10000; i++) {
            int key = random.nextInt(maxValue);
            avl.remove(key);
            assertFalse(avl.contains(key));
        }

        validate_AVL_property(avl);

    }

    public void testLeftRightRotation() {
        AVLTree<Integer> avl = new AVLTree<>((Integer x, Integer y) -> x < y);
        // Pre-rotation
        //      5
        //     / \
        //    2   7
        //   / \
        //  1   4
        avl.insert(5);
        avl.insert(2);
        avl.insert(7);
        avl.insert(1);
        avl.insert(4);
        avl.insert(3);

        // This last insert should trigger a Left-Right rotation
        //      5
        //     / \
        //    2   7
        //   / \
        //  1   4
        //     /
        //    3
        avl.insert(3);

        // Post-rotation
        //      4
        //     / \
        //    2   5
        //   / \   \
        //  1   3   7

        // Verifying the structure
        assertEquals(4, avl.root.data);
        assertEquals(2, avl.root.left.data);
        assertEquals(5, avl.root.right.data);
        assertEquals(1, avl.root.left.left.data);
        assertEquals(3, avl.root.left.right.data);
        assertEquals(7, avl.root.right.right.data);

        // Verifying the AVL properties
        assertTrue(avl.isAVL());
        assertEquals(2, avl.height());
    }

    @Test
    public void testRightLeftRotation() {
        AVLTree<Integer> avl = new AVLTree<>((Integer x, Integer y) -> x < y);
        // Pre-rotation
        //      5
        //     / \
        //    2   7
        //       / \
        //      6   8
        avl.insert(5);
        avl.insert(2);
        avl.insert(7);
        avl.insert(6);
        avl.insert(8);

        // This last insert should trigger a Right-Left rotation
        //      5
        //     / \
        //    2   7
        //       / \
        //      6   8
        //           \
        //            9
        avl.insert(9);

        // Post-rotation
        //        7
        //       / \
        //      5   8
        //     / \   \
        //    2   6   9

        // Verifying the structure
        assertEquals(7, avl.root.data);
        assertEquals(5, avl.root.left.data);
        assertEquals(8, avl.root.right.data);
        assertEquals(2, avl.root.left.left.data);
        assertEquals(6, avl.root.left.right.data);
        assertEquals(9, avl.root.right.right.data);

        // Verifying the AVL properties
        assertTrue(avl.isAVL());
        assertEquals(2, avl.height());
    }

    // Check that the tree is an AVL tree.

    public static void validate_AVL_property(AVLTree<Integer> tree) {
        validate_AVL_property(tree.root);
    }

    // Checks that the subtree rooted at location n is an AVL tree
// and returns the height of this subtree.
    public static int validate_AVL_property(BinarySearchTree.Node<Integer> n) {
        if (n == null) {
            return -1; // Height of an empty subtree
        }

        // Recursively validate the left and right subtrees
        int leftHeight = validate_AVL_property(n.left);
        int rightHeight = validate_AVL_property(n.right);


        // Check the AVL property
        assertTrue(Math.abs(leftHeight - rightHeight) < 2);

        return 1 + Math.max(leftHeight, rightHeight); // Return the height of this node
    }
}
