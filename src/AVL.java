import java.io.IOException;
import java.io.BufferedWriter;


// -------------------------------------------------------------------------
/**
 *  AVL Class with code taken from textbook
 *
 *  @param <T> The java generic T
 *
 *  @author Mack Hasz (mrh17)
 *  @version Jun 24, 2014
 */
public class AVL<T extends Comparable<? super T>> {

    class Node {
        public Node( T elem) {
            this (elem, null, null);
        }

        public Node(T elem, Node lt, Node rt) {
            element = elem;
            left = lt;
            right = rt;
            height = 0;
        }

        T element;
        Node right;
        Node left;
        int height;
    }

    Node root;

    private int height(Node node) {
        return node == null ? -1 : node.height;
    }

    public T find( T x ) {
        return findHelp(root, x);
     }

     private T findHelp(Node rt, T x)
     {
         if (rt == null) {
             return null;
         }

         int cmp = x.compareTo(rt.element);

         if (cmp < 0 ) {
             return findHelp(rt.left, x);
         } else if (cmp > 0) {
             return findHelp(rt.right, x);
         } else {
             return rt.element;
         }
     }

     public boolean insert( T x ) {
         if (find(x) != null) {
             return false;
         }
         root = insertHelp(root, x);
         return true;
     }

    private Node insertHelp(Node rt, T elem) {
        if (rt == null) {
            return new Node(elem, null, null);
        }

        int cmp = elem.compareTo(rt.element);

        if (cmp < 0) {
            rt.left = insertHelp(rt.left, elem);
        } else if (cmp > 0) {
            rt.right = insertHelp(rt.right, elem);
        } else {
            ; // duplicate do nothing
        }
        return balance(rt);
    }

    public boolean remove( T x ) {
        if (find(x) == null) {
            return false;
        }
        root = removeHelp(root, x);
        return true;
    }

    private Node removeHelp(Node rt, T elem)
    {
        if (rt == null) {
            return rt;
        }

        int cmp = elem.compareTo(rt.element);

        if (cmp < 0) {
            rt.left = removeHelp(rt.left, elem);
        } else if (cmp > 0) {
            rt.right = removeHelp(rt.right, elem);
        } else if (rt.left != null && rt.right != null) {
            rt.element = findMin(rt.right).element;
            rt.right = removeHelp(rt.right, elem);
        } else {
            rt = (rt.left != null) ? rt.left : rt.right;
        }
        return balance(rt);
    }

    private Node findMin(Node rt)
    {
        if (rt.left == null) {
            return rt;
        }

        return findMin(rt.left);
    }

    private Node balance(Node rt)
    {
        if (rt == null) {
            return rt;
        }

        if ( height(rt.left) - height(rt.right) > 1) {
            if ( height(rt.left.left) >= height(rt.left.right)) {
                rt = rotateWithLeftChild(rt);
            } else {
                rt = doubleWithLeftChild(rt);
            }
        } else if ( height(rt.right) - height(rt.left) > 1) {
            if ( height(rt.right.right) >= height(rt.right.left)) {
                rt = rotateWithRightChild(rt);
            } else {
                rt = doubleWithRightChild(rt);
            }
        }

        rt.height = Math.max(height(rt.left) , height(rt.right)) + 1;
        return rt;
    }

    private Node doubleWithRightChild(Node rt)
    {
        rt.right = rotateWithLeftChild(rt.right);
        return rotateWithRightChild(rt);
    }

    private Node rotateWithRightChild(Node rt)
    {
        Node k1 = rt.right;
        rt.right = k1.left;
        k1.left = rt;

        rt.height = Math.max(height(rt.left), height(rt.right)) + 1;
        k1.height = Math.max(height(k1.right), rt.height) + 1;

        return k1;
    }

    private Node doubleWithLeftChild(Node rt)
    {
        rt.left = rotateWithRightChild(rt.left);
        return rotateWithLeftChild(rt);
    }

    private Node rotateWithLeftChild(Node rt)
    {
        Node k1 = rt.left;
        rt.left = k1.right;
        k1.right = rt;

        rt.height = Math.max(height(rt.left), height(rt.right)) + 1;
        k1.height = Math.max(height(k1.left), rt.height) + 1;

        return k1;
    }

    // prints the tree from least to greatest
    public void print (Node rt) {
        if (rt == null) {
            return;
        }


        print(rt.right);

        System.out.println(rt.element);

        print(rt.left);
    }


    // ----------------------------------------------------------
    /**
     * writes to a logfile in the format specified
     *
     * @param rt
     * @param logBuff
     * @throws IOException
     */
    public void display(Node rt, BufferedWriter logBuff) throws IOException {
        if (rt == null) {
            return;
        }

        display(rt.right, logBuff);

        for (int i = (this.root.height) - (rt.height);i > 0; i--) {
            logBuff.write("   ");
        }

        int right = height(rt.right);
        int left = height(rt.left);

        if (left > right) {
            logBuff.write("LEFTHI");
        } else if (right > left) {
            logBuff.write("RIGHTHI");
        } else {
            logBuff.write("EQUALHT");
        }

        MiniRecord temp = (MiniRecord) rt.element;
        logBuff.write(":(" + temp.getPlayer_ID() + ", " + temp.getOffset() + ")");
        logBuff.newLine();

        display(rt.left, logBuff);
    }
}