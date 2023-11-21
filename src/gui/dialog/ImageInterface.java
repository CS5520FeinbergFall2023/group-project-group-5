package gui.dialog;

import java.awt.image.BufferedImage;

/**
 * This interface represents windows that needs to update images.
 */
public interface ImageInterface {
  void updateProcessingImage(BufferedImage image);

  void updateDiagram(BufferedImage diagram);
}
