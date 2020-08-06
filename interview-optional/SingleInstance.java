package com.strands.spf;

/**
 * <p>
 * Single instance in a multi threading environment
 * This is a thread safe singleton: Singleton property is maintained even in multithreaded environment.
 * To make a singleton class thread-safe, getInstance() method is made synchronized so that multiple
 * threads can’t access it simultaneously.
 * </p>
 * 
 * @author strands
 * 
 */
public class SingleInstance {

  // private instance, so that it can only be accessed by getInstance() method
  private static SingleInstance instance;

  // private constructor
  private SingleInstance {  }


  synchronized public static SingleInstance getInstance() {
    // Lazy inicialization: the object is created only if it is needed to prevent resource wastage
    if (instance = null) {
      instance = new SingleInstance();
    }
    return instance;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
