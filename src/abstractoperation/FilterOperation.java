package abstractoperation;

import java.lang.reflect.Constructor;

import model.Image;
import model.MyImage;
import model.Pixel;
import model.RGBPixel;

/**
 * This abstract class represents all operations perform with filtering.
 * Including blurring and sharpening.
 */

public abstract class FilterOperation implements Operation {

  /** Perform filtering an image with given matrix. Modification is made in-place.
   *
   * @param kernel the given kernel
   * @param image the image to work on
   * @throws IllegalArgumentException when the given argument is not legal
   */
  public void imgFiltering(float[][] kernel, Image image)
  {
    int kernelHeight= kernel.length;
    int kernelWidth=kernel[0].length;
    if(kernel.length%2==0)
    {
      throw new IllegalArgumentException("The kernel should have odd dimensions.");
    }
    for (float[] k :kernel)
    {
      if(k.length%2==0)
      {
        throw new IllegalArgumentException("The kernel should have odd dimensions.");
      }
      if(k.length!= kernelWidth)
      {
        throw new IllegalArgumentException("The kernel should be rectangle.");
      }
    }
    //x, y coordinate of the center of the kernel relative to the coordinate of the kernel
    int kernelCenterX=kernelWidth/2;
    int kernelCenterY=kernelHeight/2;

    String newName= image.getName()+"-new";
    for (int i=0;i< image.getWidth();i++){
      for(int j=0;j< image.getHeight();j++)
      {
        //i,j is where the current kernel center lies relative to the coordinate of the image
        //row start and end are the area the kernel covers relative to the coordinate of the image
        int rowStart=Math.max(0,i-kernelCenterX);
        int rowEnd=Math.min(image.getWidth()-1,i+kernelCenterX );
        int colStart=Math.max(0,j-kernelCenterY);
        int colEnd=Math.min(image.getHeight()-1,i+kernelCenterY);
        Pixel sum;
        //traver all pixels on the image in this area
        for (int x=rowStart;x<=rowEnd;x++)
        {
          for (int y=colStart;y<=colEnd;y++)
          {
            Pixel tmp=
                image.getPixel(x,y).multiplyNumber(kernel[x-(i-kernelCenterX)][y-(j-kernelCenterY)]);
            if(x==rowStart && y==colStart)
            {
              image.setPixel(i,j,tmp);
            }
            else{
              image.setPixel(i,j,image.getPixel(i,j).addition(tmp));
            }
          }
        }
      }
      image.setName(newName);
    }
  }
}
