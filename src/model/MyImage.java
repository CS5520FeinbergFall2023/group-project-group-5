package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import abstractoperation.ChannelSplitOperable;
import operation.Operation;

public class MyImage implements Image {
  private List<Operation> operationList;
  private Pixel[][] pixels;
  private int height;
  private int width;
  private String name;

  public MyImage(int height, int width, String name) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid width and height.");
    }
    if (name == null) {
      throw new IllegalArgumentException("The name cannot be null");
    }
    this.height = height;
    this.width = width;
    this.name = name;
    operationList = new ArrayList<>();
    pixels = new RGBPixel[height][width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        pixels[row][col] = new RGBPixel(0, 0, 0);
      }
    }
  }

  public MyImage(int height, int width, String name, Pixel[][] pixels) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid width and height.");
    }
    if (name == null) {
      throw new IllegalArgumentException("The name cannot be null");
    }
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels cannot be null");
    }
    this.height = height;
    this.width = width;
    this.name = name;
    this.pixels = pixels;
    operationList = new ArrayList<>();
  }

  @Override
  // 实现加载图像的代码
  public void load(String fileName) throws IOException {

  }

  @Override
  // 实现保存图像的代码
  public void save(String fileName) throws IOException {

  }

  /**
   * @return
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * @return
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  public void takeOperation(Operation operation) {
    operationList.add(operation);
  }

  public void performOperations() {
    for (Operation operation : operationList) {
      operation.perform();
    }
    operationList.clear();
  }

  /**
   * Get the image's name.
   *
   * @return the image's name.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Set the image's name.
   */
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Pixel getPixel(int x, int y) {
    if (x < 0 || x > this.width || y < 0 || y > this.height) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    return pixels[x][y];

  }

  @Override
  public void setPixel(int x, int y, Pixel pixel) {
    if (pixel == null) {
      throw new IllegalArgumentException("The pixel cannot be null.");
    }
    if (x < 0 || x > this.width || y < 0 || y > this.height) {
      throw new IllegalArgumentException("The x or y is out of bound.");
    }
    pixels[x][y] = pixel;
  }

  /** Perform array addition an image with given matrix. Modification is made in-place.
   *
   * @param matrix the given matrix (1x3)
   */
  @Override
  public MyImage imgArrayAddition(float[] matrix)
  {
    String newName= this.getName()+"-new";
    MyImage result=new MyImage(this.height,this.width,newName);
    for (int i=0;i< this.getWidth();i++){
      for(int j=0;j< this.getHeight();j++)
      {
        result.setPixel(i,j,this.getPixel(i,j).addition(matrix));
      }
    }
    return result;
  }

  /** Perform filtering an image with given matrix. Modification is made in-place.
   *
   * @param kernel the given kernel
   * @throws IllegalArgumentException when the given argument is not legal
   */
  @Override
  public MyImage imgFiltering(float[][] kernel)
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

    String newName= this.getName()+"-new";
    MyImage result=new MyImage(this.height,this.width,newName);

    for (int i=0;i< this.getWidth();i++){
      for(int j=0;j< this.getHeight();j++)
      {
        //i,j is where the current kernel center lies relative to the coordinate of the image
        //row start and end are the area the kernel covers relative to the coordinate of the image
        int rowStart=Math.max(0,i-kernelCenterX);
        int rowEnd=Math.min(this.getWidth()-1,i+kernelCenterX );
        int colStart=Math.max(0,j-kernelCenterY);
        int colEnd=Math.min(this.getHeight()-1,i+kernelCenterY);
        //traver all pixels on the image in this area
        for (int x=rowStart;x<=rowEnd;x++)
        {
          for (int y=colStart;y<=colEnd;y++)
          {
            Pixel tmp=
                this.getPixel(x,y).multiplyNumber(kernel[x-(i-kernelCenterX)][y-(j-kernelCenterY)]);
            result.setPixel(i,j,result.getPixel(i,j).addition(this.getPixel(i,j).addition(tmp)));
          }
        }
      }
    }
    return result;
  }

  /** Perform linear transformation on an image with given matrix. Modification is made in-place.
   *
   * @param matrix the given matrix
   * @return the new image after modification
   */
  @Override
  public Image arrayImageMultiplication(float[][] matrix)
  {
    String newName= this.getName()+"-new";
    MyImage result=new MyImage(this.height,this.width,newName);
    for (int i=0;i< this.getWidth();i++){
      for(int j=0;j< this.getHeight();j++)
      {
        result.setPixel(i,j,this.getPixel(i,j).linearTransformation(matrix));
      }
    }
    return result;
  }

  /** Split channels of the given ChannelSplitOperable
   *
   * @param channel the channel to split
   * @return the split result
   * @throws IllegalArgumentException when the given channel is illegal
   */
  @Override
  public ChannelSplitOperable channelSplit(Channel channel) throws IllegalArgumentException{
    String newName= this.getName()+"-new";
    MyImage result=new MyImage(this.height,this.width,newName);
    for (int i=0;i<this.width;i++)
    {
      for(int j=0;j<this.height;j++)
      {
        Pixel pixel=this.getPixel(i,j);
        if (!pixel.containsChannel(channel))
        {
          throw new IllegalArgumentException("The RGB image does not have the given channel.");
        }
        result.setPixel(i,j, result.getPixel(i,j).getChannelComponent(channel));
      }
    }
    return result;
  }
}
