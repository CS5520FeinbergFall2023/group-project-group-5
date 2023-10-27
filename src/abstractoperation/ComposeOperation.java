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

  /** Perform array addition an image with given matrix.
   *
   * @param matrix the given matrix
   * @param image the image to work on
   * @return the new image after modification
   */
  public MyImage imgArrayAddition(float[] matrix, Image image)
  {
    String newName= image.getName()+"-new";
    Pixel[][] newPixels=new Pixel[image.getWidth()][image.getHeight()];
    for (int i=0;i< image.getWidth();i++){
      for(int j=0;j< image.getHeight();j++)
      {
        newPixels[i][j]= image.getPixel(i,j).addition(matrix);
      }
    }
    return new MyImage(image.getHeight(), image.getWidth(), newName,newPixels);
  }
}
