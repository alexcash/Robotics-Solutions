// File: IntStack.java from the package edu.colorado.collections
// Complete documentation is available from the IntStack link in:
//   http://www.cs.colorado.edu/~main/docs/

import java.util.EmptyStackException;

/******************************************************************************
* An <CODE>IntStack</CODE> is a stack of int values.
*
* <dl><dt><b>Limitations:</b>
*   <dd>
*   (1) The capacity of one of these stacks can change after it's created, but
*   the maximum capacity is limited by the amount of free memory on the 
*   machine. The constructor, <CODE>ensureCapacity</CODE>, <CODE>push</CODE>, 
*   and <CODE>trimToSize</CODE> will result in an 
*   <CODE>OutOfMemoryError</CODE> when free memory is exhausted.
*   <dd>
*   (2) A stack's capacity cannot exceed the maximum integer 2,147,483,647
*   (<CODE>Integer.MAX_VALUE</CODE>). Any attempt to create a larger capacity
*   results in a failure due to an arithmetic overflow. 
* </dl>
*
* <dt><b>Java Source Code for this class:</b><dd>
*   <A HREF="../../../../edu/colorado/collections/IntStack.java">
*   http://www.cs.colorado.edu/~main/edu/colorado/collections/IntStack.java
*   </A>
*
* @author Michael Main 
*   <A HREF="mailto:main@colorado.edu"> (main@colorado.edu) </A>
*
* @version
*   Jun 12, 1998
*
* @see IntLinkedStack
* @see ObjectStack
* @see BooleanStack
* @see ByteStack
* @see CharStack
* @see DoubleStack
* @see FloatStack
* @see LongStack
* @see ShortStack
******************************************************************************/
public class IntStack implements Cloneable
{
   // Invariant of the IntStack class:
   //   1. The number of items in the stack is in the instance variable manyItems.
   //   2. For an empty stack, we do not care what is stored in any of data; for a
   //      non-empty stack, the items in the stack are stored in a partially-filled array called
   //      data, with the bottom of the stack at data[0], the next item at data[1], and so on
   //      to the top of the stack at data[manyItems-1].
   private int[ ] data;
   private int manyItems; 


   /**
   * Initialize an empty stack with an initial capacity of 10.  Note that the
   * <CODE>push</CODE> method works efficiently (without needing more
   * memory) until this capacity is reached.
   * @param - none
   * <dt><b>Postcondition:</b><dd>
   *   This stack is empty and has an initial capacity of 10.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for: 
   *   <CODE>new int[10]</CODE>.
   **/   
   public IntStack( )
   {
      final int INITIAL_CAPACITY = 10;
      manyItems = 0;
      data = new int[INITIAL_CAPACITY];
   }

   
   /**
   * Initialize an empty stack with a specified initial capacity. Note that the
   * <CODE>push</CODE> method works efficiently (without needing more
   * memory) until this capacity is reached.
   * @param <CODE>initialCapacity</CODE>
   *   the initial capacity of this stack
   * <dt><b>Precondition:</b><dd>
   *   <CODE>initialCapacity</CODE> is non-negative.
   * <dt><b>Postcondition:</b><dd>
   *   This stack is empty and has the given initial capacity.
   * @exception IllegalArgumentException
   *   Indicates that initialCapacity is negative.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for:
   *   <CODE>new int[initialCapacity]</CODE>.
   **/   
   public IntStack(int initialCapacity)
   {
      if (initialCapacity < 0)
         throw new IllegalArgumentException
         ("initialCapacity too small " + initialCapacity);
      manyItems = 0;
      data = new int[initialCapacity];
   }

   
   /**
   * Generate a copy of this stack.
   * @param - none
   * @return
   *   The return value is a copy of this stack. Subsequent changes to the
   *   copy will not affect the original, nor vice versa. Note that the return
   *   value must be type cast to an <CODE>IntStack</CODE> before it can be used.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
   public Object clone( )       
   {  // Clone an IntStack.
      IntStack answer;
      
      try
      {
         answer = (IntStack) super.clone( );
      }
      catch (CloneNotSupportedException e)
      { 
         // This exception should not occur. But if it does, it would probably indicate a
         // programming error that made super.clone unavailable. The most comon error
         // The most common error would be forgetting the "Implements Cloneable"
         // clause at the start of this class.
         throw new RuntimeException
         ("This class does not implement Cloneable");
     }
      
      answer.data = (int [ ]) data.clone( );
      
      return answer;
   }        
 
   
   /**
   * Change the current capacity of this stack.
   * @param <CODE>minimumCapacity</CODE>
   *   the new capacity for this stack
   * <dt><b>Postcondition:</b><dd>
   *   This stack's capacity has been changed to at least <CODE>minimumCapacity</CODE>.
   *   If the capacity was already at or greater than <CODE>minimumCapacity</CODE>,
   *   then the capacity is left unchanged.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for: <CODE>new int[minimumCapacity]</CODE>. 
   **/
   public void ensureCapacity(int minimumCapacity)
   {
      int biggerArray[ ];
      
      if (data.length < minimumCapacity)
      {
         biggerArray = new int[minimumCapacity];
         System.arraycopy(data, 0, biggerArray, 0, manyItems);
         data = biggerArray;
      }
   }


   /**
   * Accessor method to get the current capacity of this stack. 
   * The <CODE>push</CODE> method works efficiently (without needing
   * more memory) until this capacity is reached.
   * @param - none
   * @return
   *   the current capacity of this stack
   **/
   public int getCapacity( )   
   {
      return data.length;
   }

   
   /**
   * Determine whether this stack is empty.
   * @param - none
   * @return
   *   <CODE>true</CODE> if this stack is empty;
   *   <CODE>false</CODE> otherwise. 
   **/
   public boolean isEmpty( )
   {
      return (manyItems == 0);
   }
   

   /**
   * Get the top item of this stack, without removing the item.
   * @param - none
   * <dt><b>Precondition:</b><dd>
   *   This stack is not empty.
   * @return
   *   the top item of the stack
   * @exception EmptyStackException
   *   Indicates that this stack is empty.
   **/   
   public int peek( )   
   {
      if (manyItems == 0)
         // EmptyStackException is from java.util and its constructor has no argument.
         throw new EmptyStackException( );
      return data[manyItems-1];
   }

   
   /**
   * Get the top item, removing it from this stack.
   * @param - none
   * <dt><b>Precondition:</b><dd>
   *   This stack is not empty.
   * <dt><b>Postcondition:</b><dd>
   *   The return value is the top item of this stack, and the item has
   *   been removed.
   * @exception EmptyStackException
   *   Indicates that this stack is empty.
   **/    
   public int pop( )
   {
      if (manyItems == 0)
         // EmptyStackException is from java.util and its constructor has no argument.
         throw new EmptyStackException( );
      return data[--manyItems];
   }
   
 
   /**
   * Push a new item onto this stack.  If the addition
   * would take this stack beyond its current capacity, then the capacity is 
   * increased before adding the new item. The new item may be the null
   * reference.
   * @param <CODE>item</CODE>
   *   the item to be pushed onto this stack 
   * <dt><b>Postcondition:</b><dd>
   *   The item has been pushed onto this stack.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for increasing the stack's capacity.
   * <dt><b>Note:</b><dd>
   *   An attempt to increase the capacity beyond
   *   <CODE>Integer.MAX_VALUE</CODE> will cause the stack to fail with an
   *   arithmetic overflow.
   **/    
   public void push(int item)
   {
      if (manyItems == data.length)
      {
         // Int the capacity and add 1; this works even if manyItems is 0. However, in
         // case that manyItems*2 + 1 is beyond Integer.MAX_VALUE, there will be an 
         // arithmetic overflow and the bag will fail.
         ensureCapacity(manyItems*2 + 1);
      }
      data[manyItems] = item;
      manyItems++;
   }
              

   /**
   * Accessor method to determine the number of items in this stack.
   * @param - none
   * @return
   *   the number of items in this stack
   **/ 
   public int size( )   
   {
      return manyItems;
   }


   /**
   * Reduce the current capacity of this stack to its actual size (i.e., the
   * number of items it contains).
   * @param - none
   * <dt><b>Postcondition:</b><dd>
   *   This stack's capacity has been changed to its current size.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for altering the capacity. 
   **/   
   public void trimToSize( )
   {
      int trimmedArray[ ];
      
      if (data.length != manyItems)
      {
         trimmedArray = new int[manyItems];
         System.arraycopy(data, 0, trimmedArray, 0, manyItems);
         data = trimmedArray;
      }
   } 
}
