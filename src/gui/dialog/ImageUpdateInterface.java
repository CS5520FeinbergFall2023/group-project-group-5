package gui.dialog;

import java.awt.image.BufferedImage;

/**
 * This interface represents windows that needs to update images.
 */
public interface ImageUpdateInterface {
  /**
   * Update the image that is currently being processed.
   *
   * @param image the new image that is currently being processed
   */
  void updateProcessingImage(BufferedImage image);

  /**
   * Update the current image diagram.
   *
   * @param diagram the new image diagram
   */
  void updateDiagram(BufferedImage diagram);
}
