package operation;

import operable.ComposeOperable;

/**
 * A class that represents composing operations on images.
 */
public class CompositionOperation implements Operation {
  private ComposeOperable[] toBeComposed;

  /** Construct a composing operation on given ComposeOperables.
   *
   * @param toBeComposed the ComposeOperables to operate
   */
  public CompositionOperation(ComposeOperable[] toBeComposed) {
    if(toBeComposed.length==0)
    {
      throw new IllegalArgumentException("Given list is empty.");
    }
    this.toBeComposed = toBeComposed;
  }
  /**
   * Perform the operation.
   */
  @Override
  public ComposeOperable perform() {
    ComposeOperable result=toBeComposed[0];
    for(int i=1;i< toBeComposed.length;i++)
    {
      result=result.addition(toBeComposed[i]);
    }
    return result;
  }
}
