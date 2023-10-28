package operation;

import operable.ComposeOperable;

/**
 * A class that represents channel composing operations on images.
 */
public class CompositionOperation implements Operation {
  private ComposeOperable composeOperable;
  private Iterable<ComposeOperable> toBeComposed;

  /** Construct a channel composing operation on a given ComposeOperable.
   *
   * @param composeOperable the ComposeOperable to operate
   */
  public CompositionOperation(ComposeOperable composeOperable,Iterable<ComposeOperable> toBeComposed) {
    this.composeOperable = composeOperable;
    this.toBeComposed = toBeComposed;
  }
  /**
   * Perform the operation.
   */
  @Override
  public ComposeOperable perform() {
    return composeOperable.addition(toBeComposed);
  }
}
