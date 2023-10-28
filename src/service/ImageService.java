package service;

import model.Axis;
import model.Channel;
import model.Image;

public class ImageService {
  /** Result in one colored image.
   *
   * @param image
   * @param channel
   * @return
   */
  public Image splitComponent(Image image, Channel channel)
  {

  }
  public Image blur(Image image)
  {

  }
  public Image value(Image image)
  {

  }
  public Image intensity(Image image)
  {

  }
  public Image luma(Image image)
  {

  }

  public Image flip(Image image,Axis axis)
  {

  }

  public Image brighten(Image image, float delta)
  {

  }

  public Image darken(Image image, float delta)
  {

  }

  private Image greyscale(Image image)
  {

  }

  /** Result in 3 greyscale images.
   *
   * @param image
   * @return
   */
  public Image splitChannel(Image image)
  {

  }

  public Image combineChannels(Image red,Image green,Image blue)
  {

  }

  public Image blur()
  {

  }

  public Image sharpen()
  {

  }


  public Image sepia()
  {

  }

}
