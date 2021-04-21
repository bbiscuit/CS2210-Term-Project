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



        return null;
    }

    /**
     * Inserts provided element into the Dictionary
     *Flint, Michigan
     * @param key     of object to be inserted
     * @param element to be inserted
     */
    public void insertElement(Object key, Object element) {
        final int MAX_ITEMS = 3;
        Item tempItem = new Item(key, element);
        if (treeRoot == null) {
            treeRoot = new TFNode();
            treeRoot.insertItem(0, tempItem);
            size++;
        }
        else {
            if (treeRoot.getNumItems() < MAX_ITEMS) {
                treeRoot.insertItem(size, tempItem);
                size++;
            }
            else {
                int index = findFirstGreaterThanOrEqualTo(key, treeRoot);
                TFNode child = treeRoot.getChild(index);
                if (child == null) {
                    child = new TFNode();
                    child.insertItem(0, tempItem);
                    size++;
                }
                else {
                    if (child.getNumItems() < MAX_ITEMS) {
                        child.insertItem(child.getNumItems(), tempItem);
                        size++;
                    }
                    else {
                        child.insertItem(MAX_ITEMS, tempItem);
                        size++;
                        fixNode(child, MAX_ITEMS);
                    }
                }
            }
        }



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
    private int findFirstGreaterThanOrEqualTo(Object key, TFNode node) {
        return -1;
    }

    /**
     * Fixes the node to retain the 2-4 tree property.
     *
     * @param node       the node to fix.
     * @param childIndex the child the node is of its parent. This value is ignored if the node is parentless.
     */
    private static void fixNode(TFNode node, int childIndex) {
        // If the size of the node is at the limit (4)...

        if (node != null && node.getNumItems() == 4) {

            // Get the parent of the passed node.

            TFNode parent = node.getParent();

            // If the parent is null...

            if (parent == null) {

                // Create a new parent node.

                parent = new TFNode();
                node.setParent(parent);
                childIndex = 0;
            }

            // Send the second-to-last (the third) item to the parent.

            final int MOVE_UP_INDEX = 2;
            Item moveUp = node.deleteItem(MOVE_UP_INDEX);
            parent.insertItem(childIndex, moveUp);

            // Split the node into two children; the first makes up the first two items, the second is the last
            // item (after the one we moved up).

            final int SPLIT_INDEX = 2;
            TFNode splitNode = new TFNode();
            splitNode.addItem(0, node.deleteItem(SPLIT_INDEX));
            splitNode.setParent(parent);

            // Assign the children of the node to the children of the two new nodes.

            splitNode.setChild(0, node.getChild(3));
            node.setChild(3, null);
            splitNode.setChild(1, node.getChild(4));
            node.setChild(4, null);

            // Shift the children to make room for the new node in the parent.

            // (If something is broken, it's probably this)
            for (int i = 4; i >= childIndex + 1; i--) {
                parent.setChild(i, parent.getChild(i - 1));
            }
            parent.setChild(childIndex + 1, splitNode);
        }
    }
    public static void main(String[] args) {
        TFNode temp = new TFNode();
        temp.addItem(0, new Item(1, 12));
        temp.addItem(1, new Item(2, 12));
        temp.addItem(2, new Item(3, 12));
        temp.addItem(3, new Item(4, 12));

        fixNode(temp, 0);

    }


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
*/