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

    public Node left_rotate(Node n){
        Node l = n.left; // l is right child of n
        Node lr = l.right; // lr is right child of l

        l.right = n; //
        n.left = lr;

        l.updateHeight();
        n.updateHeight();

        return l; // return new root
    }

    public Node right_rotate(Node n){
        Node r = n.right;
        Node rl = r.left;

        r.left = n;
        n.right = rl;

        r.updateHeight();
        n.updateHeight();

        return r; // return new root

    }

    public Node fix(Node n) {
        if (get_height(n.left) <= get_height(n.right)) {
            if (get_height(n.right.left) <= get_height(n.right.right)) {
                n = left_rotate(n);
            }
            else {
                n = left_rotate(right_rotate(n));
            }
        }
        else {
            if (get_height(n.left.left) < get_height(n.left.right)) {
                n = right_rotate(left_rotate(n));
            }
            else {
                n = right_rotate(n);
            }
        }
    }

    /**
     * TODO
     * Inserts the given key into this AVL tree such that the ordering
     * property for a BST and the balancing property for an AVL tree are
     * maintained.
     */
    public Node insert(K key) {
        // insert as BST
        Node curr = super.insert(key);

        // check if is AVL
        if (!curr.isAVL()){
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
        super.remove(key);

        // check if is AVL
        if (!this.isAVL()){
            fix(this);
        }
    }

}
