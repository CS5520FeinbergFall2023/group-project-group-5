public class RGBPixel implements Pixel {
  private int red;
  private int green;
  private int blue;

  public RGBPixel(int red, int green, int blue) {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("Colors are invalid");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;

  }

  @Override
  public int getColorChannel(Channel channel) {
    switch (channel) {
      case Red:
        return this.red;
      case Green:
        return this.green;
      case Blue:
        return this.blue;
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }


  @Override
  public void setColorChannel(Channel channel, int color) {
    switch (channel) {
      case Blue:
        this.blue = color;
        break;
      case Red:
        this.red = color;
        break;
      case Green:
        this.green = color;
        break;
      default:
        throw new IllegalArgumentException("Invalid color channel");

    }
  }
}
