package abstractoperation;

import model.Image;
import model.MyImage;
import model.Pixel;

/**
 * This abstract class represents all operations that are perform by array X image
 * to make every pixel [r,g,b] --> [r',g',b'],
 * including decomposing channels, greyscale, sepia and visualize brightness (with a greyscale
 * result).
 */
public abstract class LinearTransformOperation implements Operation {
  /** Perform linear transformation on an image with given matrix.
   *
   * @param matrix the given matrix
   * @param image the image to work on
   * @return the new image after modification
   */
  public MyImage imgArrayMultiplication(float[][] matrix,Image image)
  {
    String newName= image.getName()+"-new";
    Pixel[][] newPixels=new Pixel[image.getWidth()][image.getHeight()];
    for (int i=0;i< image.getWidth();i++){
      for(int j=0;j< image.getHeight();j++)
      {
        newPixels[i][j]= image.getPixel(i,j).linearTransformation(matrix);
      }
    }
    return new MyImage(image.getHeight(), image.getWidth(), newName,newPixels);
  }
}
