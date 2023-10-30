package service;

import model.Axis;
import model.Channel;
import model.Image;

public interface imageServiceInterface {
  Image splitComponent(Image image, Channel channel);

  Image blur(Image image);

  Image getValue(Image image);
  Image getIntensity(Image image);
  Image getLuma(Image image);
  Image flip(Image image, Axis axis);
  Image brighten(Image image, float delta);
  Image darken(Image image, float delta);
  Image[] splitChannel(Image image);
  Image combineChannels(Channel[] channels, Image[] images);
  Image sharpen(Image image);
  Image getSepia(Image image);

}
