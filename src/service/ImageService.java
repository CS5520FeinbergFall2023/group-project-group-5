package service;

import java.util.function.Function;

import model.Axis;
import model.Channel;
import model.Image;
import model.Pixel;

public class ImageService {
  /** Result in one colored image.
   *
   * @param image
   * @param channel
   * @return
   */
  public Image splitComponent(Image image, Channel channel)
  {
    return image.channelSplit(channel);
  }
  public Image blur(Image image)
  {
    float[][] blurFilter=new float[][]{
        {0.0625f,0.125f,0.0625f},
        {0.125f,0.25f,0.125f},
        {0.0625f,0.125f,0.0625f}
    };
    return image.filtering(blurFilter);
  }
  public Image value(Image image)
  {
    return image.mapElement(Pixel::avg);
  }
  public Image intensity(Image image)
  {
    return image.mapElement(Pixel::max);
  }
  public Image luma(Image image)
  {
    float[][] luma=new float[][]{
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f}
    };
    return image.arrayMultiplication(luma);
  }

  public Image flip(Image image,Axis axis)
  {
    int[][] matrix;
    if(axis==Axis.X) {
      matrix = new int[][]{
          {-1,0, image.getWidth()-1},
          {0,1,0}
      };
    }
    else{
      matrix = new int[][]{
          {1,0,0},
          {0,-1, image.getHeight()-1}
      };
    }
    return image.projectCoordinate(matrix);
  }

  public Image brighten(Image image, float delta)
  {
    if(delta<0)
    {
      throw new IllegalArgumentException("delta should not be negative for brightening");
    }
    float[] matrix=new float[]{delta,delta,delta};
    return image.imgArrayAddition(matrix);
  }

  public Image darken(Image image, float delta)
  {
    if(delta>0)
    {
      throw new IllegalArgumentException("delta should not be positive for darkening");
    }
    float[] matrix=new float[]{delta,delta,delta};
    return image.imgArrayAddition(matrix);
  }

  private Image greyscale(Image image)
  {
    //todo:same as luma?
    float[][] greyscale=new float[][]{
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f},
        {0.2126f,0.7152f,0.722f}
    };
    return image.arrayMultiplication(greyscale);
  }

  /** Result in $channelCount greyscale images.
   *
   * @param image
   * @return
   */
  public Image[] splitChannel(Image image)
  {
    Image[] result=new Image[image.getChannels().length];
    for (int i=0;i< result.length;i++)
    {
      result[i]=greyscale(image.channelSplit(image.getChannels()[i]));
    }
    return result;
  }

  public Image combineChannels(Image red,Image green,Image blue)
  {
    return red.addition(green).addition(blue);
  }

  public Image sharpen(Image image)
  {
    float[][] sharpen=new float[][]{
        {-0.125f,-0.125f,-0.125f,-0.125f,-0.125f},
        {-0.125f,0.25f,0.25f,0.25f,0.25f},
        {-0.125f,0.25f,1f,0.25f,-0.125f},
        {-0.125f,-0.25f,0.25f,0.25f,-0.125f},
        {-0.125f,-0.125f,-0.125f,-0.125f,-0.125f},
    };
    return image.filtering(sharpen);
  }


  public Image sepia(Image image)
  {
    float[][] sepia=new float[][]
        {
            {0.393f,0.769f,0.189f},
            {0.349f,0.686f,0.168f},
            {0.272f,0.534f,0.131f}
        };
    return image.arrayMultiplication(sepia);
  }

}
