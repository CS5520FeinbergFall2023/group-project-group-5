package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import gui.ImageManipulationFrame;
import gui.dialog.ColorComponentDialog;
import gui.dialog.CompressDialog;
import gui.dialog.LevelAdjustmentDialog;
import gui.dialog.SplitOperationDialog;
import model.image.MyImage;
import model.pixel.RGBPixel;
import service.ImageService;

import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;


public class ImageGUIController implements ActionListener {

  private final ImageService imageService;
  private ImageManipulationFrame imageManipulationFrame;
  public ButtonListener buttonListener;

//  public ImageGUIController (ImageService imageService, ImageManipulationFrame imageManipulationFrame) {
//    this.imageService = imageService;
//    this.imageManipulationFrame = imageManipulationFrame;
//    this.buttonListener = new ButtonListener();
//    imageManipulationFrame.setController(this);
//  }

  public ImageGUIController(ImageService imageService) {
    this.imageService = imageService;
  }
  public void setImageManipulationFrame(ImageManipulationFrame frame) {
    this.imageManipulationFrame = frame;
    this.buttonListener = new ButtonListener();
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
            + "quit without saving?", "You haven't saved the image", YES_NO_CANCEL_OPTION);
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
      imageManipulationFrame.updateImageViewProcessing(myImage);
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

  public MyImage convertToMyImage(java.awt.Image awtImage) {
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

  private static class ButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      switch (command) {
        case "Compress":
          CompressDialog compressDialog = new CompressDialog();
          compressDialog.setVisible(true);
          break;
        case "Color Component":
          ColorComponentDialog colorComponentDialog = new ColorComponentDialog();
          colorComponentDialog.setVisible(true);
          break;
        case "Level Adjustment":
          LevelAdjustmentDialog levelAdjustmentDialog = new LevelAdjustmentDialog();
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
          ImageIcon imageGreyscale = new ImageIcon("res/cupcake-greyscale-50%.png");
          SplitOperationDialog greyscaleDialog =
                new SplitOperationDialog("Greyscale", imageGreyscale);
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
        default:

      }
    }
  }

//  public void compressOperation(float compressionValue) {
//    java.awt.Image currentImage = imageManipulationFrame.getCurrentDisplayedImage();
//    MyImage myImage = convertToMyImage(currentImage);
//    MyImage compressedImage = (MyImage) imageService.haarWaveletCompress(myImage, compressionValue);
//    imageManipulationFrame.updateImageViewProcessing(compressedImage);
//  }



}
