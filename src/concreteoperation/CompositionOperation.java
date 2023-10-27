package concreteoperation;

import abstractoperation.ComposeOperation;
import model.Image;

/**
 * A class that represents channel composing operations on images.
 */
public class CompositionOperation extends ComposeOperation {
  private Image image;

  /** Construct a channel composing operation on a given image.
   *
   * @param image the image to operate
   */
  public CompositionOperation(Image image) {
    this.image = image;
  }

  /**
   * Perform the operation.
   */
  @Override
  public void perform() {

  }
}
