/**
 * @author Davis Jeffrey
 */


/**
 * Impl. of an Elementary Symbol Table with unique keys
 * Tree, such that for each node: (keys in left subtree) < key < (keys in right subtree)
 */
public class BinarySearchTree<Key extends Comparable<Key>, Val> {

    Node root;
    int n = 0;

    public BinarySearchTree() {
    }

    //For each Node, store children and key-value pair.
    private class Node {
        Node left;
        Node right;
        Key key;
        Val val;
        int size;

        public Node(Key key, Val val, Node left, Node right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public void put(Key k, Val v) {
        if (k == null) {
            delete(k);
            return;
        }
        root = put(k, v, root);
        n++;
    }

    //Either finds Key and changes value, or travels to the last Node and inserts a new Node
    private Node put(Key k, Val v, Node x) {
        if (x == null) {
            x = new Node(k, v, null, null);
            x.size = 1;
            return x;
        }
        int cmp = k.compareTo(x.key);
        if (cmp < 0) { //Req key < current key, belongs to left subtree
            //left subtree = left subtree after inserting the Node
            x.left = put(k, v, x.left);
        }
        if (cmp > 0) { //Req key > current key, belongs to right subtree
            //right subtree = right subtree after inserting the Node
            x.right = put(k, v, x.right);
        }
        if (cmp == 0) {
            x.val = v;
        }
        x.size = 1 + size(x.left) + size(x.right); //Invariant
        return x;
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    //Returns null if Key not present
    public Val get(Key k) {
        if (k == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return get(k, root);
    }

    private Val get(Key k, Node x) {
        if (x == null) { //Happens when no match is found but last Node has been reached
            return null;
        }
        int cmp = k.compareTo(x.key);
        if (cmp < 0) {
            return get(k, x.left); //Should be in left subtree since k < x.key
        }
        if (cmp > 0) {
            return get(k, x.right);
        }
        return x.val; //cmp == 0

    }

    public void delete(Key k) {
        if (k == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        delete(k, root);
        n--;
    }

    //Replace target node x with min(x.right), delete min(x.right)
    private Node delete(Key k, Node x) {
        if (x == null) { //Happens when no match is found but last Node has been reached
            return null;
        }
        int cmp = k.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(k, x.left);
        }
        if (cmp > 0) {
            x.right = delete(k, x.right);
        }
        //if (cmp == 0)
        //If there's only one child, just promote that to current node's position
        if (x.left == null) {
            return x.right;
        } else if (x.right == null) {
            return x.left;
        }
        //If it has 2 children, find min of right and replace
        Node temp = minNode(x.right);
        temp.right = deleteMin(temp.right);
        temp.left = x.left;
        x = temp;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node minNode(Node x) {
        if (x.left == null) {
            return x;
        }
        return minNode(x.left);
    }

    //Deletes Min node from the subtree constituted in input node x and returns x
    private Node deleteMin(Node x) {
        if (x == null) {
            return null;
        }
        if (x.left == null) { //If no key is smaller, return right subtree
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMin() {
        Key min = min();
        delete(min);
        n--;
    }

    public void deleteMax() {
        Key max = max();
        delete(max);
        n--;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return size(root);
    }

    public Key floor() {
        return null;
    }

    public Key ceil() {
        return null;
    }

    //Smallest Key: Keep looking at left child till it's null
    public Key min() {
        Node x = root;
        while (x.left != null) {
            x = x.left;
        }
        return x.key;
    }

    //Largest Key: Keep looking at right child till it's null
    public Key max() {
        Node x = root;
        while (x.right != null) {
            x = x.right;
        }
        return x.key;
    }

    //No of Keys less than k
    public int rank(Key k) {
        return rank(k, root);
    }

    private int rank(Key k, Node x) {
        if (x == null) {
            return 0;
        }
        int cmp = k.compareTo(x.key);
        if (cmp < 0) {
            return rank(k, x.left);
        }
        if (cmp > 0) {
            //1 includes current node, left are all smaller, right needs to be checked
            return 1 + size(x.left) + rank(k, x.right);
        }
        return size(x.left); //cmp == 0
    }

    //Select the nth largest Key
    public Key select(int position) {
        if (position == 0 || position > n) {
            throw new IllegalArgumentException("Position must be between 1 and n");
        }
        Node x = root;
        //For keys 1 to 10: (rank, position) == (9, 1) ==(8, 2) ...
        // ~ rank(n) + position = n ; position = n - rank(n)
        outerloop:
        while (position != n - rank(x.key)) {
            int currentPosition = n - rank(x.key);
            if (currentPosition < position) { //Required key must be < x's key
                if (x.left != null) {
                    x = x.left;
                }
                else {
                    break outerloop;
                }
            }
            if (currentPosition > position) { //Required key must be > x's key
                if (x.right != null) {
                    x = x.right;
                }
                else {
                    break outerloop;
                }
            }
        }
        return x.key;
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.put(1, "xcv");
        bst.put(4, "asd");
        bst.put(8, "qwe");
        bst.put(3, "rty");
        bst.put(6, "cvb");
        bst.put(10, "dfg");
        bst.put(9, "sdf");
        bst.put(5, "ert");
        bst.put(2, "wer");
        bst.put(7, "zxc");
        assert (bst.get(8).equals("qwe"));
        System.out.println(bst.rank(1));
        assert (bst.rank(10) == 9);
        assert (bst.select(10) == 1);
        assert (bst.select(5) == 6);
        bst.delete(8);
        assert (bst.get(8) == null);

    }
}
