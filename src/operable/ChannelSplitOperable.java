package operable;

import model.Channel;

public interface ChannelSplitOperable<T> extends MapElementOperable<T>,ComposeOperable {
  /** Split channels of the given ChannelSplitOperable
   *
   * @param channel the channel to split
   * @return the split result
   */
  ChannelSplitOperable<T> channelSplit(Channel channel);
}
