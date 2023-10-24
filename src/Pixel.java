public interface Pixel {

  /**
   *
   * @param channel
   * @return
   */
  int getColorChannel(Channel channel);

  /**
   *
   * @param channel
   * @param color
   */
  void setColorChannel(Channel channel, int color);


}
