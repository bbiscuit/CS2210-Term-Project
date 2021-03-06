package termproject;

/**
 * Title:        Term Project 2-4 Trees
 * Description:  A 2-4 tree implementation based upon the Dictionary interface.
 * Copyright:    Copyright (c) 2001
 * Company:      Cedarville University
 * @author       Andrew Huffman, Kyle Samuelson
 * @version      1.0
 */
public class TwoFourTree
        implements Dictionary {

    private Comparator treeComp;
    private int size = 0;
    private TFNode treeRoot = null;
    private static final int MAX_ITEMS = 3;

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
     * @param key                to be searched for
     * @return                   object corresponding to key; null if not found
     * @throws NullKeyException  when the provided key is null
     */
    public Object findElement(Object key) throws NullKeyException {
        // # Param checking
        
        if (key == null) {
            throw new NullKeyException("key was null");
        }	
            
        // # Get the starting node as the root of the tree.

        TFNode node = root();

        // # While the node is not null...

        while (node != null) {

            // # Find the index of the first key in the node which is greater than or equal to the argument.

            int fge = findFirstGreaterThanOrEqualTo(node, key);

            // # If the found key is the parameter, return.

            if (node.getItem(fge).key() == key) {
                return node.getItem(fge).element();
            }

            // # Otherwise, repeat with the child at the index of the found key.

            node = node.getChild(fge);
        }

        // # If nothing was ever found, return null.
            
        return null;
    }

    /**
     * Inserts provided element into the Dictionary
     *
     * @param key       of object to be inserted
     * @param element   to be inserted
     */
    public void insertElement(Object key, Object element) throws NullKeyException {
        // # Parameter check: if either the key or the object is null,
        // throw.
            
        if (key == null) {
            throw new NullKeyException("key was null");
        }
        
        // # Declare a new element to insert into the tree
        Item tempItem = new Item(key, element);

        // # Edge case: root is null

        if (treeRoot == null) {

            // # Make a new node at the root, and increase the size of the tree.

            treeRoot = new TFNode();
            treeRoot.insertItem(0, tempItem);
            size++;

            return;
        }

        // # Find the location of the node to insert into.

        TFNode insertLocation = treeRoot;
        int index = findFirstGreaterThanOrEqualTo(insertLocation, key);
        TFNode next = insertLocation.getChild(index);

        while (next != null) {
            // # Find the index of the child to insert at
            insertLocation = next;
            index = findFirstGreaterThanOrEqualTo(insertLocation, key);
            next = insertLocation.getChild(index);

            // index = findFirstGreaterThanOrEqualTo(insertLocation, key);

        }

        insertLocation.insertItem(index, tempItem);
        fixNode(insertLocation, whatChildIsThis(insertLocation));
    }


    /**
     * Determines what child of its parent the provided node is.
     *
     * @param node the node to check.
     * @return the child index of the passed node; -1 if the node is parent-less or if the provided node is not hooked
     * up to its child correctly.
     * @throws TFNodeException if the provided node is null
     */
    private int whatChildIsThis(TFNode node) throws TFNodeException {
        // # Loop through the parent's children.

        if (node == null) {
            throw new TFNodeException("node provided was null");
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

        // # If no child was ever found, return -1.

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
     * @throws TFNodeException if the provided node is null.
     * @throws NullKeyException if the provided key is null.
     */

    private int findFirstGreaterThanOrEqualTo(TFNode node, Object key) throws TFNodeException, NullKeyException {
        // # Param checking

        if (node == null) {
            throw new TFNodeException("provided node was null");
        }
        else if (key == null) {
            throw new NullKeyException("provided key was null");
        }

        int i = 0;

        // # Search through all the items in the node

        for (i = 0; i < node.getNumItems(); i++) {
            Object nodeKey = node.getItem(i).key();

            // # if we find something that is greater than the given key
            // we get its index if not we return the greatest index
            if (treeComp.isGreaterThanOrEqualTo(nodeKey, key)) {
                return i;
            }
        }

        // # If no key was found to be greater than or equal to the passed key,
        // return i (which should be at numItems - 1).

        return i;
    }

    /**
     * Fixes the node to retain the 2-4 tree property.
     *
     * @param node       the node to fix.
     * @param childIndex the child the node is of its parent. This value is ignored if the node is parentless.
     */
    private void fixNode(TFNode node, int childIndex) {
        // # If the size of the node is at the limit (4)...

        if (node != null && node.getNumItems() == 4) {

            // # Get the parent of the passed node.

            TFNode parent = node.getParent();

            // # If the parent is null...

            if (parent == null) {

                // # Create a new parent node.

                parent = new TFNode();
                node.setParent(parent);
                childIndex = 0;

                setRoot(parent);
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

            // # Setup the parent post-split.

            parent.insertItem(childIndex, moveUp);
            splitNode.setParent(parent);
            parent.setChild(childIndex + 1, splitNode);

            // # Assign the last two children from the "fixing" node to the "split" node.

            splitNode.setChild(0, node.getChild(3));
            node.setChild(3, null);
            splitNode.setChild(1, node.getChild(4));
            node.setChild(4, null);

            // # If the new node has children, set them up

            if (splitNode.getChild(0) != null) {
                splitNode.getChild(0).setParent(splitNode);
            }
            if (splitNode.getChild(1) != null) {
                splitNode.getChild(1).setParent(splitNode);
            }
          
            // # Recursively fix the parent node

            fixNode(parent);
        }
    }

    /**
     * Fixes the specified node to retain the 2-4 tree property. This defaults the childIndex to
     * whatChildIsThis(node). Therefore, it's less efficient, but convenient if you would have to
     * manually fetch the value anyway.
     *
     * @param node  the node to fix.
     */
    private void fixNode(TFNode node) {
        fixNode(node, whatChildIsThis(node));
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
