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
        // your tests go here
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
        System.out.print(bst.toString());
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




    /*
     * Helper Methods to Validate Properties of 
     * BST and AVL Tree.
     */
    
    // Check that the tree is ordered
//    public static void
//    validate_BST_property(OrderedSet<Integer> tree) {
//        validate_BST_property((BinarySearchTree<Integer>)tree.root());
//    }

    // Check that the subtree satisfies the BST Property.
    // Returns the min and max keys in the subtree of n.
//    public static Pair<Integer,Integer>
//    validate_BST_property(Location<Integer> n) {
//        if (n == null) {
//            return null;
//        } else {
//            Pair<Integer,Integer> left_range =
//                validate_BST_property(n.left_child());
//            Pair<Integer,Integer> right_range =
//                validate_BST_property(n.right_child());
//            if (left_range == null) {
//                left_range = new Pair<>(n.get(), n.get());
//            } else {
//                assertTrue(left_range.getValue() < n.get());
//            }
//            if (right_range == null) {
//                right_range = new Pair<>(n.get(), n.get());
//            } else {
//                assertTrue(n.get() < right_range.getKey());
//            }
//            return combine(left_range, right_range);
//        }
//    }
//
//
//    // Check that the tree is an AVL tree.
//    public static <K> void validate_AVL_property(OrderedSet<K> tree) {
//        validate_AVL_property(tree.root());
//    }
//    // Checks that the subtree rooted at location n is and AVL tree
//    // and returns the height of this subtree.
//    public static <K> int validate_AVL_property(Location<K> n) {
//        if (n == null) {
//            return -1;
//        } else {
//            int h1, h2;
//            h1 = validate_AVL_property(n.left_child());
//            h2 = validate_AVL_property(n.right_child());
//            assertTrue(Math.abs(h2 - h1) < 2);
//            return 1 + max(h1, h2);
//        }
//    }
}
