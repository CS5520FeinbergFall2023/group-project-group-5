package abstractoperation;

import model.Channel;

public interface ChannelSplitOperable extends Operable{
  /** Split channels of the given ChannelSplitOperable
   *
   * @param channel the channel to split
   * @return the split result
   */
  ChannelSplitOperable channelSplit(Channel channel);
}
