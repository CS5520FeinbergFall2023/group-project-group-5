package concreteoperation;

import abstractoperation.LinearTransformOperation;
import model.Channel;

public class ChannelSplitOperation implements LinearTransformOperation {
  // 还没realize，等接口和parent class注释写好了再用intellij自动implement就不需要手动复制doc了
  public void getColorChannel(Channel channel) {
    switch (channel) {
      case RED:
        break;
      case GREEN:
        break;
      case BLUE:
        break;
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }
}
