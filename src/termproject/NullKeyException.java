package termproject;

/**
* An exception which is to be thrown when one attempts to insert
* an item with a null key into a search tree.
*
* @author      Andrew Huffman, Kyle Samuelson
* @version     4/30/21
*/
public class NullKeyException extends RuntimeException {
      public NullKeyException(String mesage) {
            super(mesage);
      }
}
