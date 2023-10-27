package operation;

import abstractoperation.ComposeOperable;

/**
 * A class that represents channel composing operations on images.
 */
public class CompositionOperation implements Operation {
  private ComposeOperable composeOperable;

  /** Construct a channel composing operation on a given ComposeOperable.
   *
   * @param composeOperable the ComposeOperable to operate
   */
  public CompositionOperation(ComposeOperable composeOperable) {
    this.composeOperable = composeOperable;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
