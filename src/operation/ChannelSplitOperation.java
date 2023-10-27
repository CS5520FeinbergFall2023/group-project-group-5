package operation;

import abstractoperation.ChannelSplitOperable;
import abstractoperation.LinearTransformOperable;
import model.Channel;
import model.Image;

/**
 * A class that represents channel splitting operations on images.
 */
public class ChannelSplitOperation implements Operation {
  private ChannelSplitOperable channelSplitOperable;
  private Channel channel;

  /** Construct a channel splitting operation on a given ChannelSplitOperable.
   * @param channelSplitOperable the ChannelSplitOperable to operate
   * @param channel the channel to split
   */
  public ChannelSplitOperation(ChannelSplitOperable channelSplitOperable, Channel channel) {
    this.channelSplitOperable = channelSplitOperable;
    this.channel = channel;
  }

  /**
   * Perform the operation.
   */
  @Override
  public ChannelSplitOperable perform() {
    return channelSplitOperable.channelSplit(channel);
  }
}
