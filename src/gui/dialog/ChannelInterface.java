package gui.dialog;

import model.Channel;

/**
 * This interface represents windows that needs to pass down channel argument to controller.
 */
public interface ChannelInterface {

  /**
   * Get channel.
   *
   * @return the channel
   */
  Channel getChannel();
}
