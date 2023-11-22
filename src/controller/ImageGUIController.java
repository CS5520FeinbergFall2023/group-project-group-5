package controller;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import gui.ImageManipulationFrame;
import gui.dialog.ChannelDialogListener;
import gui.dialog.ColorComponentDialog;
import gui.dialog.CompressDialog;
import gui.dialog.CompressionDialogListener;
import gui.dialog.ImageUpdateInterface;
import gui.dialog.LevelAdjustmentDialog;
import gui.dialog.LevelAdjustmentDialogListener;
import gui.dialog.SplitOperationDialog;
import gui.dialog.SplitViewDialogListener;
import model.Axis;
import model.Channel;
import model.image.MyImage;
import model.pixel.RGBPixel;
import service.ImageService;

import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;


public class ImageGUIController implements ActionListener {

  private final ImageService imageService;
  private ImageManipulationFrame imageManipulationFrame;
  public ButtonListener buttonListener;

  public ImageGUIController(ImageService imageService) {
    this.imageService = imageService;
  }

  public void setImageManipulationFrame(ImageManipulationFrame imageManipulationFrame) {
    this.imageManipulationFrame = imageManipulationFrame;
    this.buttonListener = new ButtonListener(imageManipulationFrame, this.imageService);
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == imageManipulationFrame.getOpenMenuItem()) {
      loadImage();
    } else if (e.getSource() == imageManipulationFrame.getSaveJPGItem()) {
      String pathJPG = imageManipulationFrame.getSaveFilePath() + ".jpg";
      saveFile(pathJPG);
    } else if (e.getSource() == imageManipulationFrame.getSavePNGItem()) {
      String pathPNG = imageManipulationFrame.getSaveFilePath() + ".png";
      saveFile(pathPNG);
    } else if (e.getSource() == imageManipulationFrame.getSavePPMItem()) {
      String pathPPM = imageManipulationFrame.getSaveFilePath() + ".ppm";
      saveFile(pathPPM);
    } else if (e.getSource() == imageManipulationFrame.getQuitMenuItem()) {
      int response = JOptionPane.showConfirmDialog(null, "Are you sure to "
                                                         + "quit without saving?",
          "You haven't saved the image", YES_NO_CANCEL_OPTION);
      if (response == JOptionPane.YES_OPTION) {
        System.exit(0);
      }
    }
  }

  public ActionListener getButtonListener() {
    return buttonListener;
  }

  public void loadImage() {
    String filePath = imageManipulationFrame.getSelectedFilePath();
    if (filePath != null && !filePath.isEmpty()) {
      MyImage myImage = new MyImage(filePath);
      BufferedImage loadedImage = convertToBufferedImage(myImage);
      imageManipulationFrame.updateProcessingImage(loadedImage);
      MyImage myImageHistogram = (MyImage) imageService.getHistogram(myImage);
      BufferedImage loadedImageHistogram = convertToBufferedImage(myImageHistogram);
      imageManipulationFrame.updateDiagram(loadedImageHistogram);
    }
  }

  public void saveFile(String path) {
    if (path != null) {
      java.awt.Image currentImage = imageManipulationFrame.getCurrentDisplayedImage();
      MyImage myImage = convertToMyImage(currentImage);
      if (myImage != null) {
        myImage.save(path);
      }
    }
  }


  /**
   * Transfer the awtImage type picture into MyImage type picture.
   *
   * @param awtImage Image
   * @return
   */
  public static MyImage convertToMyImage(java.awt.Image awtImage) {
    if (awtImage == null) {
      return null;
    }

    // transfer java.awt.Image into BufferedImage.
    BufferedImage bufferedImage = new BufferedImage(awtImage.getWidth(null),
        awtImage.getHeight(null),
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D bGr = bufferedImage.createGraphics();
    bGr.drawImage(awtImage, 0, 0, null);
    bGr.dispose();

    // Acquire pixel and create MyImage object.
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    MyImage myImage = new MyImage(height, width);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int argb = bufferedImage.getRGB(x, y);
        int red = (argb >> 16) & 0xff;
        int green = (argb >> 8) & 0xff;
        int blue = argb & 0xff;
        RGBPixel pixel = new RGBPixel(red, green, blue);
        myImage.setPixel(y, x, pixel);
      }
    }
    return myImage;
  }

  /**
   * Transfer the MyImage type into BufferedImage type.
   *
   * @param myImage MyImage object picture.
   * @return the bufferedImage type.
   */
  public static BufferedImage convertToBufferedImage(MyImage myImage) {
    if (myImage == null) {
      return null;
    }
    int width = myImage.getWidth();
    int height = myImage.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        RGBPixel pixel = myImage.getPixel(y, x);
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();
        int alpha = 255;
        int argb = (alpha << 24) | (red << 16) | (green << 8) | blue;
        bufferedImage.setRGB(x, y, argb);
      }
    }
    return bufferedImage;
  }

  private static class ButtonListener implements ActionListener {

    private ImageManipulationFrame imageManipulationFrame;
    private ImageService imageService;


    public ButtonListener(ImageManipulationFrame imageManipulationFrame,
                          ImageService imageService) {
      this.imageManipulationFrame = imageManipulationFrame;
      this.imageService = imageService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      switch (command) {
        case "Compress":
          CompressDialog compressDialog = new CompressDialog();
//          compressDialog.setCompressionDialogListener(new CompressDialog.CompressionDialogListener() {
//            @Override
//            public void onCompressionConfirmed(float compressionValue) {
//              java.awt.Image currentImage = imageManipulationFrame.getCurrentDisplayedImage();
//              MyImage currentMyImage = ImageGUIController.convertToMyImage(currentImage);
//              MyImage compressedImage = (MyImage) imageService.haarWaveletCompress(currentMyImage, compressionValue);
//              BufferedImage compressedBufferedImage = ImageGUIController.convertToBufferedImage(compressedImage);
//              imageManipulationFrame.updateProcessingImage(compressedBufferedImage);
//              MyImage compressedImageHistogram = (MyImage) imageService.getHistogram(compressedImage);
//              BufferedImage compressedBufferedHistogram = ImageGUIController.convertToBufferedImage(compressedImageHistogram);
//              imageManipulationFrame.updateDiagram(compressedBufferedHistogram);
//            }
//          });
          compressDialog.setPercentageDialogListener(new CompressionDialogListener() {
            @Override
            public void onCompressionConfirmed(float percentage) {
              java.awt.Image currentImage = imageManipulationFrame.getCurrentDisplayedImage();
              MyImage currentMyImage = ImageGUIController.convertToMyImage(currentImage);
              MyImage compressedImage =
                  (MyImage) imageService.haarWaveletCompress(currentMyImage, percentage);
              BufferedImage compressedBufferedImage =
                  ImageGUIController.convertToBufferedImage(compressedImage);
              imageManipulationFrame.updateProcessingImage(compressedBufferedImage);
              MyImage compressedImageHistogram =
                  (MyImage) imageService.getHistogram(compressedImage);
              BufferedImage compressedBufferedHistogram =
                  ImageGUIController.convertToBufferedImage(compressedImageHistogram);
              imageManipulationFrame.updateDiagram(compressedBufferedHistogram);
            }
          });
          compressDialog.setVisible(true);
          break;
        case "Color Component":
          ColorComponentDialog colorComponentDialog = new ColorComponentDialog();
          colorComponentDialog.setChannelDialogListener(new ChannelDialogListener() {
            @Override
            public void onColorComponentConfirmed(Channel channel) {
              java.awt.Image currentImage = imageManipulationFrame.getCurrentDisplayedImage();
              MyImage currentMyImage = ImageGUIController.convertToMyImage(currentImage);
              MyImage componentImage = (MyImage) imageService.splitComponent(currentMyImage, channel);
              BufferedImage componentBufferedImage = ImageGUIController.convertToBufferedImage(componentImage);
              imageManipulationFrame.updateProcessingImage(componentBufferedImage);
              MyImage componentImageHistogram = (MyImage) imageService.getHistogram(componentImage);
              BufferedImage componentBufferedHistogram = ImageGUIController.convertToBufferedImage(componentImageHistogram);
              imageManipulationFrame.updateDiagram(componentBufferedHistogram);
            }
          });
          colorComponentDialog.setVisible(true);
          break;
        case "Level Adjustment":
          LevelAdjustmentDialog levelAdjustmentDialog = new LevelAdjustmentDialog();
          levelAdjustmentDialog.setLevelAdjustmentListener(new LevelAdjustmentDialogListener() {
            @Override
            public void onLevelAdjustmentConfirmed(float[] ControlPointValues) {
              java.awt.Image currentImage = imageManipulationFrame.getCurrentDisplayedImage();
              MyImage currentMyImage = ImageGUIController.convertToMyImage(currentImage);
              //todo: directly set percentage = 1 and splitAxis = X?
              MyImage levelAdjustImage = (MyImage) imageService.levelAdjustment(currentMyImage, ControlPointValues[0], ControlPointValues[1], ControlPointValues[2], 1, Axis.X);
              BufferedImage levelAdjustBufferedImage = ImageGUIController.convertToBufferedImage(levelAdjustImage);
              imageManipulationFrame.updateProcessingImage(levelAdjustBufferedImage);
              MyImage levelAdjustImageHistogram = (MyImage) imageService.getHistogram(currentMyImage);
              BufferedImage levelAdjustBufferedHistogram = ImageGUIController.convertToBufferedImage(levelAdjustImageHistogram);
              imageManipulationFrame.updateDiagram(levelAdjustBufferedHistogram);
            }
          });
          levelAdjustmentDialog.setVisible(true);
          break;
        case "Sepia":
          ImageIcon imageSepia = new ImageIcon("res/cupcake-sepia-50%.png");
          SplitOperationDialog sepiaDialog =
              new SplitOperationDialog("Sepia", imageSepia);
          sepiaDialog.setVisible(true);
          break;
        case "Blur":
          ImageIcon imageBlur = new ImageIcon("res/cupcake-blur-50%.png");
          SplitOperationDialog blurDialog =
              new SplitOperationDialog("Blur", imageBlur);
          blurDialog.setVisible(true);
          break;
        case "Greyscale":
          ImageIcon imageGreyscale = new ImageIcon(imageManipulationFrame.getCurrentDisplayedImage());
          SplitOperationDialog greyscaleDialog =
              new SplitOperationDialog("Greyscale", imageGreyscale);
          SplitViewDialogListener splitViewDialogListener = new SplitViewDialogListener() {
            @Override
            public void onUpdatePercentage(float percentage,
                                           ImageUpdateInterface imageUpdateInterface) {
              java.awt.Image currentImage = imageManipulationFrame.getCurrentDisplayedImage();
              MyImage currentMyImage = ImageGUIController.convertToMyImage(currentImage);
              MyImage greyscaledImage =
                  (MyImage) imageService.greyscale(currentMyImage, percentage, Axis.X);
              BufferedImage compressedBufferedImage =
                  ImageGUIController.convertToBufferedImage(greyscaledImage);
              imageUpdateInterface.updateProcessingImage(compressedBufferedImage);
            }

            @Override
            public void onConfirm() {
              java.awt.Image currentImage = imageManipulationFrame.getCurrentDisplayedImage();
              MyImage currentMyImage = ImageGUIController.convertToMyImage(currentImage);
              MyImage greyscaledImage =
                  (MyImage) imageService.greyscale(currentMyImage, 1, Axis.X);
              BufferedImage compressedBufferedImage =
                  ImageGUIController.convertToBufferedImage(greyscaledImage);
              imageManipulationFrame.updateProcessingImage(compressedBufferedImage);
              MyImage compressedImageHistogram =
                  (MyImage) imageService.getHistogram(greyscaledImage);
              BufferedImage compressedBufferedHistogram =
                  ImageGUIController.convertToBufferedImage(compressedImageHistogram);
              imageManipulationFrame.updateDiagram(compressedBufferedHistogram);
            }
          };

          greyscaleDialog.setSplitViewDialogListener(splitViewDialogListener);

          greyscaleDialog.setVisible(true);
          break;
        case "Sharpen":
          ImageIcon imageSharpen = new ImageIcon("res/cupcake-sharpen-50%.png");
          SplitOperationDialog sharpenDialog =
              new SplitOperationDialog("Sharpen", imageSharpen);
          sharpenDialog.setVisible(true);
          break;
        case "Color Correct":
          ImageIcon imageColorCorrect = new ImageIcon("res/cupcake-sharpen-50%.png");
          SplitOperationDialog colorCorrectDialog =
              new SplitOperationDialog("Correct", imageColorCorrect);
          colorCorrectDialog.setVisible(true);
          break;
        case "Vertical Flip":
          java.awt.Image currentImage = imageManipulationFrame.getCurrentDisplayedImage();
          MyImage currentMyImage = ImageGUIController.convertToMyImage(currentImage);
          MyImage verticalImage = (MyImage) imageService.flip(currentMyImage, Axis.X);
          BufferedImage verticalBufferedImage =
              ImageGUIController.convertToBufferedImage(verticalImage);
          imageManipulationFrame.updateProcessingImage(verticalBufferedImage);
          MyImage verticalImageHistogram = (MyImage) imageService.getHistogram(verticalImage);
          BufferedImage verticalBufferedHistogram =
              ImageGUIController.convertToBufferedImage(verticalImageHistogram);
          imageManipulationFrame.updateDiagram(verticalBufferedHistogram);
          break;
        case "Horizontal Flip":
          java.awt.Image nowImage = imageManipulationFrame.getCurrentDisplayedImage();
          MyImage nowMyImage = ImageGUIController.convertToMyImage(nowImage);
          MyImage horizontalImage = (MyImage) imageService.flip(nowMyImage, Axis.Y);
          BufferedImage horizontalBufferedImage =
              ImageGUIController.convertToBufferedImage(horizontalImage);
          imageManipulationFrame.updateProcessingImage(horizontalBufferedImage);
          MyImage horizontalImageHistogram = (MyImage) imageService.getHistogram(horizontalImage);
          BufferedImage horizontalBufferedHistogram =
              ImageGUIController.convertToBufferedImage(horizontalImageHistogram);
          imageManipulationFrame.updateDiagram(horizontalBufferedHistogram);
          break;
        default:

      }
    }
  }

}
