package command;

import model.ImageModel;

/**
 * This is a abstract class that stores some shared methods for Command interface.
 */
public abstract class CommandImpl implements Command {

  private String oldName;
  private String newName;

  protected CommandImpl(String oldName, String newName) {
    if(oldName == null || newName == null) {
      throw new IllegalArgumentException("The file names can not be null");
    }
    this.oldName = oldName;
    this.newName = newName;

  }

  public abstract void execute(ImageModel model);

}
