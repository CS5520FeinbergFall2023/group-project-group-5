package operation;

import model.Channel;
import operable.ChannelSplitOperable;
import operable.Operable;

// eg Combine three greyscale image into a single color image whose R,G,B values come from the three images.
/**
 * A class that represents channel composing operations on images.
 */
public class ChannelCompositionOperation implements Operation{
  private ChannelSplitOperable[] channelSplitOperable;
  private Channel[] channels;

  public ChannelCompositionOperation(ChannelSplitOperable[] channelSplitOperable,
                                     Channel[] channels) {
    if(channelSplitOperable.length!=channels.length)
    {
      throw new IllegalArgumentException("The number of ChannelSplitOperable should be same as "
                                         + "channels");
    }
    this.channelSplitOperable = channelSplitOperable;
    this.channels=channels;
  }

  /**
   * Perform the operation.
   */
  @Override
  public Operable perform() {
    ChannelSplitOperable[] splits=new ChannelSplitOperable[channels.length];
    for(int i=0;i<channels.length;i++)
    {
      splits[i]=channelSplitOperable[i].channelSplit(channels[i]);
    }
    return new CompositionOperation(splits).perform();
  }
}
