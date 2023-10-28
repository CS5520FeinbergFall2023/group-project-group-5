package command;

import model.ImageModel;

/**
 * This is the class to represent the command that use to load a new image or overwrite a old image
 * with another file.
 */
public class LoadCommand implements Command{
  private String filePath;
  private String fileName;

  /**
   * The constructor of the command that loading a new image or overwriting the existing image that
   * has the provided file path.
   *
   * @param filePath the file path of the new image or overwritten image.
   * @param fileName the file name of the new image or overwritten image.
   * @throws IllegalArgumentException if the given filename or filepath is null.
   */
  public LoadCommand(String filePath, String fileName) {
    if (fileName == null || filePath == null) {
      throw new IllegalArgumentException("The filename or filepath could not be null");
    }
    this.fileName = fileName;
    this.filePath = filePath;
  }

  @Override
  public void execute(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }
    model.load(this.filePath, this.fileName);
  }
}
