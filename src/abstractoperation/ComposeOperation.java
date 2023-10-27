package abstractoperation;// operations implemented by [r1,g1,b1]+[r2,g2,b2]+....
// including compose image from the 3 channel images, lighten and darken
// actually theoretically still belongs to linear operations but...

import model.Image;
import model.MyImage;
import model.Pixel;

/**
 * This abstract class represents all operations implemented by [r1,g1,b1]+[r2,g2,b2]+....,
 * including compose image from the 3 channel images, lighten and darken.
 */
public abstract class ComposeOperation implements Operation {

  /** Perform array addition an image with given matrix. Modification is made in-place.
   *
   * @param matrix the given matrix
   * @param image the image to work on
   */
  public void imgArrayAddition(float[] matrix, Image image)
  {
    String newName= image.getName()+"-new";
    for (int i=0;i< image.getWidth();i++){
      for(int j=0;j< image.getHeight();j++)
      {
        image.setPixel(i,j,image.getPixel(i,j).addition(matrix));
      }
    }
    image.setName(newName);
  }
}
