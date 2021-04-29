package termproject;

/**
 * Title:        Term Project 2-4 Trees
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
public class TwoFourTree
        implements Dictionary {

    private Comparator treeComp;
    private int size = 0;
    private TFNode treeRoot = null;

    public TwoFourTree(Comparator comp) {
        treeComp = comp;
    }

    private TFNode root() {
        return treeRoot;
    }

    private void setRoot(TFNode root) {
        treeRoot = root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }




    /**
     * Searches dictionary to determine if key is present
     *
     * @param key to be searched for
     * @return object corresponding to key; null if not found
     */
    public Object findElement(Object key) {
        // # Get the starting node as the root of the tree.

        TFNode node = root();

        // # While the node is not null...

        while (node != null) {

            // # Find the first key in the node which is greater than or equal to the argument.

            int fge = findFirstGreaterThanOrEqualTo(node, key);

            // # If the found key is the parameter, return.

            if (node.getItem(fge).key() == key) {
                return node.getItem(fge).element();
            }

            // # Otherwise, repeat with the child at the index of the found key.

            node = node.getChild(fge);
        }

        return null;
    }

    /**
     * Inserts provided element into the Dictionary
     *
     * @param key     of object to be inserted
     * @param element to be inserted
     */
    public void insertElement(Object key, Object element) {
        // # Declare a new element to insert into the tree
        final int MAX_ITEMS = treeRoot.getMaxItems();
        Item tempItem = new Item(key, element);

        // # Edge case: root is null

        if (treeRoot == null) {

            // # Make a new node at the root.

            treeRoot = new TFNode();
            treeRoot.insertItem(0, tempItem);
            size++;

            return;
        }

        // # If the root is not at capacity, insert.

        TFNode insertLocation = treeRoot;
        int index = findFirstGreaterThanOrEqualTo(insertLocation, key);

        while (insertLocation.getNumItems() > MAX_ITEMS) {
            // # Find the index of the child to insert at
            TFNode temp = insertLocation.getChild(index);

            // # If the child is null, then we
            if (temp == null) {
                break;
            }

            insertLocation = temp;
            index = findFirstGreaterThanOrEqualTo(insertLocation, key);
        }

        insertLocation.insertItem(index, tempItem);
        // fixNode(insertLocation, whatChildIsThis(insertLocation));
    }


    /**
     * Determines what child of its parent the provided node is.
     *
     * @param node the node to check.
     * @return the child index of the passed node; -1 if the node is parent-less or if the provided node is not hooked
     * up to its child correctly.
     * @throws NullPointerException if the provided node is null
     */
    private int whatChildIsThis(TFNode node) {
        // # Loop through the parent's children.

        if (node == null) {
            throw new NullPointerException("null argument");
        }

        TFNode parent = node.getParent();

        if (parent != null) {
            for (int i = 0; i <= parent.getMaxItems() + 1; i++) {

                // # If the reference of the passed node is the same as the child, then return the index.

                if (node == parent.getChild(i)) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * Searches dictionary to determine if key is present, then
     * removes and returns corresponding object
     *
     * @param key of data to be removed
     * @return object corresponding to key
     * @throws ElementNotFoundException if the key is not in dictionary
     */
    public Object removeElement(Object key) throws ElementNotFoundException {
        return null;
    }

    /**
     * Returns the key which is "first greater than or equal to" in the provided node.
     *
     * @param key  the key to test for.
     * @param node the node to check.
     * @return the node which is "first greater than or equal to."
     */

    private int findFirstGreaterThanOrEqualTo(TFNode node, Object key) {
        int i = 0;
        // Search through all the items in the node
        for (i = 0; i < node.getNumItems(); i++) {
            Object tempKey = node.getItem(i).key();
            // if we find something that is greater than the given key
            // we get its index if not we return the greatest index
            if (treeComp.isGreaterThanOrEqualTo(tempKey, key)) {
                return i;
            }
        }
        return i;
    }

    /**
     * Fixes the node to retain the 2-4 tree property.
     *
     * @param node       the node to fix.
     * @param childIndex the child the node is of its parent. This value is ignored if the node is parentless.
     */
    private static void fixNode(TFNode node, int childIndex) {
        // # If the node is overflowing (size == 4)...

        if (node != null && node.getNumItems() == 4) {

            // # Get the parent of the passed node.

            TFNode parent = node.getParent();

            // # If the parent is null...

            if (parent == null) {

                // # Create a new parent node.

                parent = new TFNode();
                node.setParent(parent);
                childIndex = 0;
                parent.setChild(childIndex, node);
            }

            // # Create new new node, into which we will split the items.

            final int MOVE_UP_INDEX = 2;
            final int SPLIT_INDEX = 3;
            TFNode splitNode = new TFNode();

            // # Remove the split number first (Because it is the last in
            // the array) then remove the item that needs to be moved up

            splitNode.addItem(0, node.deleteItem(SPLIT_INDEX));
            Item moveUp = node.deleteItem(MOVE_UP_INDEX);

            // # Fix up the parents

            parent.insertItem(childIndex, moveUp);
            splitNode.setParent(parent);
            parent.setChild(childIndex + 1, splitNode);

            // # Assign the children of the node to the children of the two new nodes.

            splitNode.setChild(0, node.getChild(3));
            node.setChild(3, null);
            splitNode.setChild(1, node.getChild(4));
            node.setChild(4, null);

            // # Shift the children to make room for the new node in the parent.

            /*
            // (If something is broken, it's probably this)
            for (int i = 4; i >= childIndex + 1; i--) {
                parent.setChild(i, parent.getChild(i - 1));
            }
            */

        }
    }

    public static void main(String[] args) {
        // # Declare a parent and fill it

        TFNode parent = new TFNode();
        for (int i = 1; i < 4; i++) {
            parent.addItem(i - 1, new Item(i * 10, i * 10));
        }

        TFNode firstChild = new TFNode();
        for (int i = 0; i < 4; i++) {
            firstChild.addItem(i, new Item(i, i));
        }

        firstChild.setParent(parent);
        parent.setChild(0, firstChild);

        fixNode(firstChild, 0);
        fixNode(parent, -1);

        TFNode grandparent = parent.getParent();

        System.out.println("grandparent: " + grandparent);
        System.out.println("child 1:" + grandparent.getChild(0));
        System.out.println("child 2: " + grandparent.getChild(1));
        System.out.println("grandchild 1: " + grandparent.getChild(0).getChild(0));
        System.out.println("grandchild 2: " + grandparent.getChild(0).getChild(1));
    }


    /*
    public static void main(String[] args) {
        Comparator myComp = new IntegerComparator();
        TwoFourTree myTree = new TwoFourTree(myComp);

        Integer myInt1 = new Integer(47);
        myTree.insertElement(myInt1, myInt1);
        Integer myInt2 = new Integer(83);
        myTree.insertElement(myInt2, myInt2);
        Integer myInt3 = new Integer(22);
        myTree.insertElement(myInt3, myInt3);

        Integer myInt4 = new Integer(16);
        myTree.insertElement(myInt4, myInt4);

        Integer myInt5 = new Integer(49);
        myTree.insertElement(myInt5, myInt5);

        Integer myInt6 = new Integer(100);
        myTree.insertElement(myInt6, myInt6);

        Integer myInt7 = new Integer(38);
        myTree.insertElement(myInt7, myInt7);

        Integer myInt8 = new Integer(3);
        myTree.insertElement(myInt8, myInt8);

        Integer myInt9 = new Integer(53);
        myTree.insertElement(myInt9, myInt9);

        Integer myInt10 = new Integer(66);
        myTree.insertElement(myInt10, myInt10);

        Integer myInt11 = new Integer(19);
        myTree.insertElement(myInt11, myInt11);

        Integer myInt12 = new Integer(23);
        myTree.insertElement(myInt12, myInt12);

        Integer myInt13 = new Integer(24);
        myTree.insertElement(myInt13, myInt13);

        Integer myInt14 = new Integer(88);
        myTree.insertElement(myInt14, myInt14);

        Integer myInt15 = new Integer(1);
        myTree.insertElement(myInt15, myInt15);

        Integer myInt16 = new Integer(97);
        myTree.insertElement(myInt16, myInt16);

        Integer myInt17 = new Integer(94);
        myTree.insertElement(myInt17, myInt17);

        Integer myInt18 = new Integer(35);
        myTree.insertElement(myInt18, myInt18);

        Integer myInt19 = new Integer(51);
        myTree.insertElement(myInt19, myInt19);

        myTree.printAllElements();
        System.out.println("done");

        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 10000;


        for (int i = 0; i < TEST_SIZE; i++) {
            myTree.insertElement(new Integer(i), new Integer(i));
            //          myTree.printAllElements();
            //         myTree.checkTree();
        }
        System.out.println("removing");
        for (int i = 0; i < TEST_SIZE; i++) {
            int out = (Integer) myTree.removeElement(new Integer(i));
            if (out != i) {
                throw new TwoFourTreeException("main: wrong element removed");
            }
            if (i > TEST_SIZE - 15) {
                myTree.printAllElements();
            }
        }
        System.out.println("done");
    }
*/

    public void printAllElements() {
        int indent = 0;
        if (root() == null) {
            System.out.println("The tree is empty");
        }
        else {
            printTree(root(), indent);
        }
    }

    public void printTree(TFNode start, int indent) {
        if (start == null) {
            return;
        }
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        printTFNode(start);
        indent += 4;
        int numChildren = start.getNumItems() + 1;
        for (int i = 0; i < numChildren; i++) {
            printTree(start.getChild(i), indent);
        }
    }

    public void printTFNode(TFNode node) {
        int numItems = node.getNumItems();
        for (int i = 0; i < numItems; i++) {
            System.out.print(((Item) node.getItem(i)).element() + " ");
        }
        System.out.println();
    }

    // checks if tree is properly hooked up, i.e., children point to parents
    public void checkTree() {
        checkTreeFromNode(treeRoot);
    }

    private void checkTreeFromNode(TFNode start) {
        if (start == null) {
            return;
        }

        if (start.getParent() != null) {
            TFNode parent = start.getParent();
            int childIndex = 0;
            for (childIndex = 0; childIndex <= parent.getNumItems(); childIndex++) {
                if (parent.getChild(childIndex) == start) {
                    break;
                }
            }
            // if child wasn't found, print problem
            if (childIndex > parent.getNumItems()) {
                System.out.println("Child to parent confusion");
                printTFNode(start);
            }
        }

        if (start.getChild(0) != null) {
            for (int childIndex = 0; childIndex <= start.getNumItems(); childIndex++) {
                if (start.getChild(childIndex) == null) {
                    System.out.println("Mixed null and non-null children");
                    printTFNode(start);
                }
                else {
                    if (start.getChild(childIndex).getParent() != start) {
                        System.out.println("Parent to child confusion");
                        printTFNode(start);
                    }
                    for (int i = childIndex - 1; i >= 0; i--) {
                        if (start.getChild(i) == start.getChild(childIndex)) {
                            System.out.println("Duplicate children of node");
                            printTFNode(start);
                        }
                    }
                }

            }
        }

        int numChildren = start.getNumItems() + 1;
        for (int childIndex = 0; childIndex < numChildren; childIndex++) {
            checkTreeFromNode(start.getChild(childIndex));
        }

    }
}
