import java.util.List;
import java.util.LinkedList;
import java.util.function.BiPredicate;

/**
 * TODO: This is your first major task.
 * <p>
 * This class implements a generic unbalanced binary search tree (BST).
 */

public class BinarySearchTree<K> implements OrderedSet<K> {

    /**
     * A Node<K> is a Location (defined in OrderedSet.java), which
     * means that it can be the return value of a search on the tree.
     */

    static class Node<K> implements Location<K> {

        protected K data;
        protected Node<K> left, right;
        protected Node<K> parent;
        protected int height;

        /**
         * Constructs a leaf Node<K> with the given key.
         */
        public Node(K key) {
            this(key, null, null);
        }

        /**
         * TODO
         * <p>
         * Constructs a new Node<K> with the given values for fields.
         */
        public Node(K data, Node<K> left, Node<K> right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = null;
            this.updateHeight();
        }

        /*
         * Provide the get() method required by the Location interface.
         */
        @Override
        public K get() {
            return this.data;
        }

        /**
         * Return true iff this Node<K> is a leaf in the tree.
         */
        protected boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        /**
         * TODO
         * <p>
         * Performs a local update on the height of this Node<K>. Assumes that the
         * heights in the child Node<K>s are correct. Returns true iff the height
         * actually changed. This function *must* run in O(1) time.
         */
        protected boolean updateHeight() {
            int newHeight = 1 + Math.max(get_height(this.left), get_height(this.right));
            if (newHeight != this.height) {
                this.height = newHeight;
                return true;
            }
            else return false;
        }

        public void updateHeightOfSelfAndAncestors() {
            // precondition: this (node) is not null
            this.updateHeight();
            if (this.parent != null) {
                this.parent.updateHeightOfSelfAndAncestors();
            }
        }

        // Return the first node wrt. inorder in this subtree.
        public Location<K> first() {
            if (this.left == null) {
                return this;
            }
            else return this.left.first();
        }

        // Return the last node wrt. inorder in this subtree.
        public Location<K> last() {
            if (this.right == null) {
                return this;
            }
            else return this.right.last();
        }

        // Return the first ancestor that is next wrt. inorder
        // or null if there is none.
        public Location<K> nextAncestor() {
            if (this.parent == null) {
                return null;
            }
            else if (this.parent.right == this) {
                return this.parent.nextAncestor();
            }
            else return this.parent;
        }

        // Return the first ancestor that is previous wrt. inorder
        // or null if there is none.
        public Location<K> prevAncestor() {
            if (this.parent == null) {
                return null;
            }
            else if (this.parent.left == this) {
                return this.parent.prevAncestor();
            }
            else return this.parent;
        }

        /**
         * TODO
         * <p>
         * Returns the location of the Node<K> containing the inorder predecessor
         * of this Node<K>.
         */
        @Override
        public Location<K> previous() {
            if (this.left != null) {
                return this.left.last();
            }
            else return this.prevAncestor();
        }

        /**
         * TODO
         * <p>
         * Returns the location of the Node<K> containing the inorder successor
         * of this Node<K>.
         */
        @Override
        public Location<K> next() {
            if (this.right != null) {
                return this.right.first();
            }
            else return this.nextAncestor();
        }

        public boolean isAVL() {
            int h1, h2;
            h1 = get_height(left);
            h2 = get_height(right);
            return Math.abs(h2 - h1) < 2;
        }

        public String toString() {
            return toStringPreorder(this);
        }
    }

    protected Node<K> root;
    protected int numNodes;
    protected BiPredicate<K, K> lessThan;

    /**
     * Constructs an empty BST, where the data is to be organized according to
     * the lessThan relation.
     */
    public BinarySearchTree(BiPredicate<K, K> lessThan) {
        this.lessThan = lessThan;
    }

    protected Node<K> find(K key, Node<K> curr, Node<K> parent) {
        if (curr == null) {
            return parent;
        }
        else if (lessThan.test(key, curr.data)) {
            return find(key, curr.left, curr);
        }
        else if (lessThan.test(curr.data, key)) {
            return find(key, curr.right, curr);
        }
        else return curr;
    }

    /**
     * TODO
     * <p>
     * Looks up the key in this tree and, if found, returns the
     * location containing the key.
     */
    public Node<K> search(K key) {
        Node<K> n = find(key, root, null);
        if (n == null) {
            return null;
        }
        else if (n.data.equals(key)) {
            return n;
        }
	    else {
            return null;
        }
    }

    /**
     * TODO
     * <p>
     * Returns the height of this tree. Runs in O(1) time!
     */
    public int height() {
        return get_height(this.root);
    }

    /**
     * TODO
     * <p>
     * Clears all the keys from this tree. Runs in O(1) time!
     */
    public void clear() {
        this.root = null;
        this.numNodes = 0;
    }

    /**
     * Returns the number of keys in this tree.
     */
    public int size() {
        return numNodes;
    }

    /**
     * TODO
     * <p>
     * Inserts the given key into this BST, as a leaf, where the path
     * to the leaf is determined by the predicate provided to the tree
     * at construction time. The parent pointer of the new Node<K> and
     * the heights in all Node<K> along the path to the root are adjusted
     * accordingly.
     * <p>
     * Note: we assume that all keys are unique. Thus, if the given
     * key is already present in the tree, nothing happens.
     * <p>
     * Returns the location where the insert occurred (i.e., the leaf
     * Node<K> containing the key), or null if the key is already present.
     */
    public Node<K> insert(K key) {
        Node<K> parentNodeToInsertUnder = find(key, this.root, null);
        if (parentNodeToInsertUnder == null) {
            this.root = new Node<K>(key, null, null);
            // the root is the only node in the tree so this isn't needed
            // also we already updateHeight() in the constructor
            // this.root.updateHeightOfSelfAndAncestors();
            ++this.numNodes;
            return this.root;
        }
        else if (lessThan.test(key, parentNodeToInsertUnder.data)) {
            parentNodeToInsertUnder.left = new Node<K>(key, null, null);
            // connect new child with parent
            parentNodeToInsertUnder.left.parent = parentNodeToInsertUnder;
            // update height of new node and its ancestors
            parentNodeToInsertUnder.left.updateHeightOfSelfAndAncestors();
            ++this.numNodes;
            return parentNodeToInsertUnder.left;
        }
        else if (lessThan.test(parentNodeToInsertUnder.data, key)) {
            parentNodeToInsertUnder.right = new Node<K>(key, null, null);
            // connect new child with parent
            parentNodeToInsertUnder.right.parent = parentNodeToInsertUnder;
            // update height of new node and its ancestors
            parentNodeToInsertUnder.right.updateHeightOfSelfAndAncestors();
            ++this.numNodes;
            return parentNodeToInsertUnder.right;
        }
        // key is already present, no need to increment number of nodes
        // or return a newly added node
        else return null;
    }

    /**
     * Returns a textual representation of this BST.
     */
    public String toString() {
        return toStringPreorder(root);
    }

    /**
     * Returns true iff the given key is in this BST.
     */
    public boolean contains(K key) {
        Node<K> p = search(key);
        return p != null;
    }

    /**
     * TODO
     * <p>
     * Removes the key from this BST. If the key is not in the tree,
     * nothing happens.
     */
    public void remove(K key) {
        root = remove_helper(root, key);
    }

    private Node remove_helper(Node<K> curr, K key) {
        if (curr == null) {
            return null;
        }
        else if (lessThan.test(key, curr.data)) { // remove in left subtree
            curr.left = remove_helper(curr.left, key);
            return curr;
        }
        else if (lessThan.test(curr.data, key)) { // remove in right subtree
            curr.right = remove_helper(curr.right, key);
            return curr;
        }
        else {      // remove this node
            if (curr.left == null) {
                curr.updateHeightOfSelfAndAncestors();
                --this.numNodes;
                return curr.right;
            }
            else if (curr.right == null) {
                curr.updateHeightOfSelfAndAncestors();
                --this.numNodes;
                return curr.left;
            }
            else {   // two children, replace with first of right subtree
                Node<K> min = (Node<K>) curr.right.first();
                curr.data = min.data;
                curr.right = remove_helper(curr.right, min.data);

                return curr;
            }
        }
    }

    /**
     * TODO * <p> * Returns a sorted list of all the keys in this tree.
     */
    public List<K> keys() {
        List<K> sorted = new LinkedList<>();
        if (this.root != null) {
            Node<K> curr = (Node<K>) this.root.first();

            while (curr != null) {
                sorted.add(curr.data);
                curr = (Node<K>) curr.next();
            }
        }
        return sorted;
    }

    static private <K> String toStringPreorder(Node<K> p) {
        if (p == null) return ".";
        String left = toStringPreorder(p.left);
        if (left.length() != 0) left = " " + left;
        String right = toStringPreorder(p.right);
        if (right.length() != 0) right = " " + right;
        String data = p.data.toString();
        return "(" + data + "[" + p.height + "]" + left + right + ")";
    }

    /**
     * The get_height method returns the height of the Node<K> n, which may be null.
     */
    static protected <K> int get_height(Node<K> n) {
        if (n == null) return -1;
        else return n.height;
    }
}

