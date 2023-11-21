package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import gui.ImageManipulationFrame;
import model.image.Image;
import model.image.MyImage;
import model.pixel.RGBPixel;
import service.ImageService;


public class ImageGUIController implements ActionListener {

  private final ImageService imageService;
  private final ImageManipulationFrame imageManipulationFrame;

  public ImageGUIController (ImageService imageService, ImageManipulationFrame imageManipulationFrame) {
    this.imageService = imageService;
    this.imageManipulationFrame = imageManipulationFrame;
    imageManipulationFrame.setController(this);
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
    } else if (e.getSource() == imageManipulationFrame.getSaveMenuItem()) {
      String path = imageManipulationFrame.getSaveFilePath();
      saveFile(path);
    } else if (e.getSource() == imageManipulationFrame.getQuitMenuItem()) {
      System.exit(0);
    }

  }

  public void loadImage() {
    String filePath = imageManipulationFrame.getSelectedFilePath();
    if (filePath != null && !filePath.isEmpty()) {
      //Image loadedImage = new MyImage(filePath);
      ImageIcon imageIcon = new ImageIcon(filePath);
      imageManipulationFrame.updateImageViewProcessing(imageIcon);
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

    // 将 java.awt.Image 转换为 BufferedImage
    BufferedImage bufferedImage = new BufferedImage(awtImage.getWidth(null),
          awtImage.getHeight(null),
          BufferedImage.TYPE_INT_ARGB);
    Graphics2D bGr = bufferedImage.createGraphics();
    bGr.drawImage(awtImage, 0, 0, null);
    bGr.dispose();

    // 从 BufferedImage 中提取像素数据并创建 MyImage 对象
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    MyImage myImage = new MyImage(height, width);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int argb = bufferedImage.getRGB(x, y);
        // 假设您的 RGBPixel 构造函数接受 ARGB 值
        int red = (argb >> 16) & 0xff;
        int green = (argb >> 8) & 0xff;
        int blue = argb & 0xff;
        RGBPixel pixel = new RGBPixel(red, green, blue);
        myImage.setPixel(y, x, pixel);
      }
    }
    return myImage;
  }

}
