package abstractoperation;

import java.lang.reflect.Constructor;

import model.Image;
import model.MyImage;
import model.Pixel;

/**
 * This abstract class represents all operations perform with filtering.
 * Including blurring and sharpening.
 */

public abstract class FilterOperation implements Operation {

  /** Perform filtering an image with given matrix.
   *
   * @param matrix the given matrix
   * @param image the image to work on
   * @return the new image after modification
   * @throws IllegalArgumentException when the given argument is not legal
   */
  public Image imgFiltering(float[][] kernel, Image image)
  {
    if(kernel.length%2==0)
    {
      throw new IllegalArgumentException("The kernel should have odd dimensions.");
    }
    String newName= image.getName()+"-new";
    Pixel[][] newPixels=new Pixel[image.getWidth()][image.getHeight()];
    for (int i=0;i< image.getWidth();i++){
      for(int j=0;j< image.getHeight();j++)
      {
//        newPixels[i][j]= image.getPixel(i,j).addition(matrix);
      }
    }
  }
}
