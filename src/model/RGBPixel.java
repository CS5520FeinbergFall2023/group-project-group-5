package model;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class RGBPixel implements Pixel {
  private final Map<Channel,Integer> channels;

  private final int bitDepth=8;

  /**
   * Construct an RGB pixel. Every value of the channel is in the range of [0,1]
   *
   * @param red   the value of the red channel
   * @param green the value of the green channel
   * @param blue  the value of the blue channel
   */
  public RGBPixel(int red, int green, int blue) {
    //todo:or throw exception?
    //0-255
    red= Math.max(red, 0);
    green= Math.max(green, 0);
    blue= Math.max(blue, 0);
    red=Math.min(red,2<<bitDepth-1);
    green=Math.min(green,2<<bitDepth-1);
    blue=Math.min(blue,2<<bitDepth-1);
    channels=new HashMap<>();
    channels.put(Channel.RED,red);
    channels.put(Channel.GREEN,green);
    channels.put(Channel.BLUE,blue);
  }

  private RGBPixel(Map<Channel,Integer> channels)
  {
    this.channels=channels;
  }

  /**
   * Checks if the pixel has the given channel.
   *
   * @param channel the channel to check.
   * @return if the pixel has the given channel
   */
  @Override
  public boolean containsChannel(Channel channel) {
    return channels.containsKey(channel);
  }

  /**
   * Get certain channel component of the pixel.
   *
   * @param channel the channel to split
   * @return the component pixel
   * @throws IllegalArgumentException when the given channel is not in the pixel
   */
  @Override
  public Pixel getChannelComponent(Channel channel) throws IllegalArgumentException {
    if(!containsChannel(channel))
    {
      throw new IllegalArgumentException("The pixel does not contain the channel");
    }
    Map<Channel,Integer> channels=new HashMap<>();
    for (Channel key:this.channels.keySet())
    {
      if(key==channel)
      {
        channels.put(channel,this.channels.get(channel));
      }
      else {
        channels.put(key,0);
      }
    }
    return new RGBPixel(channels);
  }

  /**
   * Calculate matrix * [r,g,b]
   *
   * @param matrix the matrix to multiply
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  @Override
  public RGBPixel linearTransformation(float[][] matrix) {
    if (matrix.length == 3 && matrix[0].length == 3 && matrix[1].length == 3
        && matrix[2].length == 3) {
//      red=red * matrix[0][0] + green * matrix[0][1] + blue * matrix[0][2];
//      green=red * matrix[1][0] + green * matrix[1][1] + blue * matrix[1][2];
//      blue=red * matrix[2][0] + green * matrix[2][1] + blue * matrix[2][2];
      int red=channels.get(Channel.RED);
      int green=channels.get(Channel.GREEN);
      int blue=channels.get(Channel.BLUE);
      return new RGBPixel(
          Math.round(red * matrix[0][0] + green * matrix[0][1] + blue * matrix[0][2]),
          Math.round(red * matrix[1][0] + green * matrix[1][1] + blue * matrix[1][2]),
          Math.round(red * matrix[2][0] + green * matrix[2][1] + blue * matrix[2][2]));
    } else {
      throw new IllegalArgumentException("The multiplying array should be 3*3 in size.");
    }
  }

  /**
   * Calculate matrix + [r,g,b]
   *
   * @param matrix the matrix to add
   * @throws IllegalArgumentException when the given matrix is not legal
   */
  @Override
  public RGBPixel addition(float[] matrix) {
    if (matrix.length == 3) {
      int red=channels.get(Channel.RED);
      int green=channels.get(Channel.GREEN);
      int blue=channels.get(Channel.BLUE);
      return new RGBPixel(Math.round(red + matrix[0]), Math.round(green + matrix[1]),
          Math.round(blue + matrix[2]));
    } else {
      throw new IllegalArgumentException("The multiplying array should be 3*3 in size.");
    }
  }

  /**
   * Calculate [r,g,b] + [r',g',b']
   *
   * @param pixel the pixel to add
   * @throws IllegalArgumentException when the given pixel is not legal
   */
  @Override
  public RGBPixel addition(Pixel pixel) throws IllegalArgumentException {
    if (!(pixel instanceof RGBPixel)) {
      throw new IllegalArgumentException("Addition between pixels require them to be of same type");
    }
    int red=channels.get(Channel.RED);
    int green=channels.get(Channel.GREEN);
    int blue=channels.get(Channel.BLUE);
    int thatRed=((RGBPixel) pixel).channels.get(Channel.RED);
    int thatGreen=((RGBPixel) pixel).channels.get(Channel.GREEN);
    int thatBlue=((RGBPixel) pixel).channels.get(Channel.BLUE);
    return new RGBPixel(red + thatRed, green + thatGreen,
        blue+thatBlue);
  }



  /**
   * Multiply the pixel with a number.
   *
   * @param number the number to multiply
   * @return the new pixel after the operation
   * @throws IllegalArgumentException when the given number is not legal
   */
  @Override
  public RGBPixel multiplyNumber(float number) throws IllegalArgumentException {
    if (number < 0) {
      throw new IllegalArgumentException("Can't multiply negative number with a pixel.");
    }
    int red=channels.get(Channel.RED);
    int green=channels.get(Channel.GREEN);
    int blue=channels.get(Channel.BLUE);
    return new RGBPixel(Math.round((red * number > 1) ? 1 : red * number),
        Math.round((green * number > 1) ? 1 : green * number),
        Math.round((blue * number > 1) ? 1 : blue * number));
  }

  /** Multiply with another matrix
   * @param matrix
   * @return
   */
  public RGBPixel arrayMultiply(float[][] matrix)
  {
    if(matrix.length!=3 ||matrix[0].length!=3||matrix[1].length!=3||matrix[2].length!=3)
    {
      throw new IllegalArgumentException("Matrix needs to be 3x3.");
    }
    int newRed;
    int newGreen;
    int newBlue;
    newRed=Math.round(matrix[0][0]*getRed()+matrix[0][1]*getGreen()+matrix[0][2]*getBlue());
    newGreen=Math.round(matrix[1][0]*getRed()+matrix[1][1]*getGreen()+matrix[1][2]*getBlue());
    newBlue=Math.round(matrix[2][0]*getRed()+matrix[2][1]*getGreen()+matrix[2][2]*getBlue());
    return new RGBPixel(newRed,newGreen,newBlue);
  }

  public RGBPixel max() {
    int max=0;
    for (Channel channel : channels.keySet()) {
      int tmp = this.channels.getOrDefault(channel, 0);
      if(tmp>max)
      {
        max=tmp;
      }
    }
    return new RGBPixel(max,max,max);
  }

  public RGBPixel avg() {
    int sum=0;
    for (Channel channel : channels.keySet()) {
      int tmp = this.channels.getOrDefault(channel, 0);
      sum+=tmp;
    }
    return new RGBPixel(Math.round(sum/3f),Math.round(sum/3f),Math.round(sum/3f));
  }

   private int getRed(){
    return channels.get(Channel.RED);
  }

  private int getBlue(){
    return channels.get(Channel.BLUE);
  }


  private int getGreen(){
    return channels.get(Channel.GREEN);
  }

  @Override
  public boolean isMonochromeOfChannel(Channel channel){
    if(containsChannel(channel))
    {
      for (Channel c:channels.keySet())
      {
        if (c==channel)
        {
          continue;
        }
        if(channels.get(c)!=0){
          return false;
        }
      }
      return true;
    }
    return false;
  }

}
