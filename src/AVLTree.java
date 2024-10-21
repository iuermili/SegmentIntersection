import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * TODO: This is your second major task.
 * <p>
 * This class implements a height-balanced binary search tree,
 * using the AVL algorithm. Beyond the constructor, only the insert()
 * and remove() methods need to be implemented. All other methods are unchanged.
 */

public class AVLTree<K> extends BinarySearchTree<K> {

    /**
     * Creates an empty AVL tree as a BST organized according to the
     * lessThan predicate.
     */
    public AVLTree(BiPredicate<K, K> lessThan) {
        super(lessThan);
    }

    public boolean isAVL() {
        if (root == null)
            return true;
        else
            return root.isAVL();
    }

    public Node<K> right_rotate(Node<K> n){
        Node<K> x = n.left;
        Node<K> b = x.right;

        x.right = n;
        n.left = b;

        // Update parent references
        x.parent = n.parent;
        n.parent = x;
        if (b != null) {
            b.parent = n;
        }

        // Update the parent's child reference
        if (x.parent != null) {
            if (x.parent.left == n) {
                x.parent.left = x;
            } else {
                x.parent.right = x;
            }
        } else {
            root = x;
        }

        n.updateHeights();

        return x; // return new root
    }

    public Node<K> left_rotate(Node<K> n){
        Node<K> y = n.right;
        Node<K> b = y.left;


        y.left = n;
        n.right = b;

        // Update parent references
        y.parent = n.parent;
        n.parent = y;
        if (b != null) {
            b.parent = n;
        }

        // Update the parent's child reference
        if (y.parent != null) {
            if (y.parent.left == n) {
                y.parent.left = y;
            } else {
                y.parent.right = y;
            }
        } else {
            root = y;
        }

        n.updateHeights();

        return y; // return new root

    }

    public void fix(Node<K> n) {
        if (n == null) {
            return;
        }

        if (!n.isAVL()) {
            if (get_height(n.left) <= get_height(n.right)) {
                if (get_height(n.right.left) <= get_height(n.right.right)) {
                    n = left_rotate(n);
                }
                else {
                    n.right = right_rotate(n.right);
                    n = left_rotate(n);
                }
            }
            else {
                if (get_height(n.left.left) < get_height(n.left.right)) {
                    n.left = left_rotate(n.left);
                    n = right_rotate(n);
                }
                else {
                    n = right_rotate(n);
                }
            }
        }

        fix(n.parent);

    }

    /**
     * TODO
     * Inserts the given key into this AVL tree such that the ordering
     * property for a BST and the balancing property for an AVL tree are
     * maintained.
     */
    public Node<K> insert(K key) {
        // insert as BST
        Node<K> curr = super.insert(key);

        // check if is AVL
        if (curr != null) {
            fix(curr);
        }

        return curr;
    }

    /**
     * TODO
     * <p>
     * Removes the key from this BST. If the key is not in the tree,
     * nothing happens.
     */
    public void remove(K key) {
        // remove as BST
        Node<K> curr = search(key);
        if (curr == null) return;

        Node<K> start;
        if (curr.left != null && curr.right != null) {
            start = ((Node<K>) curr.right.first()).parent; // parent of successor node
        } else {
            start = curr.parent; // parent of removed
        }
        super.remove(key);


        if (start != null) {
            fix(start);
        }
    }
}
