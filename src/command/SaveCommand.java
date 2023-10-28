package command;

import model.ImageModel;

/**
 * This is the class to represent the command that use to save a specific image in the provided
 * file path.
 */
public class SaveCommand implements Command {
  private String filePath;
  private String fileName;

  /**
   * The constructor of the command that saving the image in the provided file path.
   *
   * @param filePath the file path of the image to be stored.
   * @param fileName the file name of the image.
   * @throws IllegalArgumentException if the given filename or filepath is null.
   */
  public SaveCommand(String filePath, String fileName) {
    if(filePath == null || fileName == null) {
      throw new IllegalArgumentException("The filename or filepath could not be null");
    }
    this.filePath = filePath;
    this.fileName = fileName;
  }

  @Override
  public void execute(ImageModel model){
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    model.save(this.filePath, this.fileName);
  }
}
