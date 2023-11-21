package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ImageGUIController;
import gui.dialog.ColorComponentDialog;
import gui.dialog.CompressDialog;
import gui.dialog.ImageUpdateInterface;
import gui.dialog.LevelAdjustmentDialog;

import gui.dialog.SplitOperationDialog;
import model.image.MyImage;
import model.pixel.RGBPixel;

import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;

/**
 * This class represents the frame for the image manipulation GUI.
 */
public class ImageManipulationFrame extends JFrame implements ActionListener, MouseListener,
    ImageUpdateInterface {
  private final JMenu fileMenu;
  private final JMenu helpMenu;
  private final JMenu saveMenu;

  private final JMenuItem saveJPGItem;

  private final JMenuItem savePNGItem;

  private final JMenuItem savePPMItem;
  private final JMenuItem openMenuItem;
  private final JMenuItem quitMenuItem;
  private final JMenuItem aboutMenuItem;
  private final JMenuItem instructionMenuItem;

  private JLabel imageViewHistogram;
  private JLabel imageViewProcessing;

  private ImageGUIController controller;
  private String selectedFilePath;
  private String saveFilePath;

  //todo:why it can be final
  private JLabel statusLabel;
  //private ButtonListener buttonListener;

  /**
   * Constructs an ImageManipulationFrame.
   */
  public ImageManipulationFrame() {
    super();
    ButtonListener buttonListener = new ButtonListener(controller);
    setTitle("Image Manipulation");
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel mainPanel = new JPanel(new BorderLayout());

    statusLabel = new JLabel("The bottom status bar will show instructions when mouse "
                             + "hovers on components.");
    statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    statusLabel.setBorder(new EmptyBorder(5, 0, 5, 10));
    mainPanel.add(statusLabel, BorderLayout.SOUTH);

    //menu bar
    JMenuBar menuBar = new JMenuBar();
    fileMenu = new JMenu("File");
    fileMenu.addMouseListener(this);
    openMenuItem = new JMenuItem("Open");
    openMenuItem.addActionListener(this);
    openMenuItem.addMouseListener(this);
    saveMenu = new JMenu("Save As...");
    saveJPGItem = new JMenuItem("JPG");
    saveJPGItem.addActionListener(this);
    savePNGItem = new JMenuItem("PNG");
    savePNGItem.addActionListener(this);
    savePPMItem = new JMenuItem("PPM");
    savePPMItem.addActionListener(this);
    saveMenu.add(saveJPGItem);
    saveMenu.add(savePNGItem);
    saveMenu.add(savePPMItem);
    saveMenu.addMouseListener(this);
    quitMenuItem = new JMenuItem("Quit");
    quitMenuItem.addActionListener(this);
    quitMenuItem.addMouseListener(this);
    fileMenu.add(openMenuItem);
    fileMenu.add(saveMenu);
    fileMenu.add(quitMenuItem);
    helpMenu = new JMenu("Help");
    helpMenu.addMouseListener(this);
    aboutMenuItem = new JMenuItem("About");
    aboutMenuItem.addActionListener(this);
    aboutMenuItem.addMouseListener(this);
    instructionMenuItem = new JMenuItem("Instructions");
    instructionMenuItem.addActionListener(this);
    instructionMenuItem.addMouseListener(this);
    helpMenu.add(instructionMenuItem);
    helpMenu.add(aboutMenuItem);
    menuBar.add(fileMenu);
    menuBar.add(helpMenu);
    mainPanel.add(menuBar, BorderLayout.PAGE_START);

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setDividerLocation(250);

    //the left part is for histogram and operation buttons
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.setPreferredSize(new Dimension(221, 600));

    // the histogram
    JPanel imagePanel = new JPanel();
    ImageIcon imageIcon = new ImageIcon("res/city-histogram.png");
    imageViewHistogram = new JLabel(imageIcon);
    imageViewHistogram.addMouseListener(this);
    imagePanel.add(imageViewHistogram);
    leftPanel.add(imagePanel, BorderLayout.NORTH);

    //operation buttons
    //operation names and the corresponding path to their icons
    Map<String, String> operations = new HashMap<String, String>() {{
      put("Color Component", "resources/icons/component.png");
      put("Horizontal Flip", "resources/icons/horizontal-flip.png");
      put("Vertical Flip", "resources/icons/vertical-flip.png");
      put("Blur", "resources/icons/blur.png");
      put("Sharpen", "resources/icons/sharpen.png");
      put("Greyscale", "resources/icons/greyscale.png");
      put("Sepia", "resources/icons/sepia.png");
      put("Compress", "resources/icons/compress.png");
      put("Color Correct", "resources/icons/color-correct.png");
      put("Level Adjustment", "resources/icons/level-adjustment.png");
    }};

    Map<String, String> instructions = new HashMap<String, String>() {{
      put("Color Component", "Get red or green or blue channel component of the image.");
      put("Horizontal Flip", "Horizontally flip the image.");
      put("Vertical Flip", "Vertically flip the image");
      put("Blur", "Blur the image. You can drag the guide split line to see split view.");
      put("Sharpen", "Sharpen the image. You can drag the guide split line to see split view.");
      put("Greyscale", "Greyscale the image. You can drag the guide split line to see split view.");
      put("Sepia", "Sepia the image. You can drag the guide split line to see split view.");
      put("Compress", "Compress the image with given ratio.");
      put("Color Correct", "Color Correct the image");
      put("Level Adjustment", "Perform level adjustment on the image.");
    }};

    JPanel gridPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    leftPanel.add(gridPanel, BorderLayout.CENTER);
    for (String operationName : operations.keySet()) {
      JButton button = createOperationCellButton(operationName, operations.get(operationName));
      gridPanel.add(button);
      button.getModel().addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
          ButtonModel model = (ButtonModel) e.getSource();
          if (model.isRollover()) {
            statusLabel.setText(instructions.get(operationName));
          } else {
            statusLabel.setText("The bottom status bar will show instructions when mouse hovers on "
                                + "components.");
          }
        }
      });
      button.addActionListener(buttonListener);
    }
    leftPanel.add(gridPanel, BorderLayout.CENTER);

    //the image that user is currently working on
    JScrollPane scrollPane = new JScrollPane();
    ImageIcon imageIconProcessing = new ImageIcon("resources/icons/sofa-purple-living-room-with"
                                                  + "-copy-space.jpg");
    imageViewProcessing = new JLabel(imageIconProcessing);
    imageViewProcessing.addMouseListener(this);
    scrollPane.setViewportView(imageViewProcessing);
    splitPane.setLeftComponent(leftPanel);
    splitPane.setRightComponent(scrollPane);
    mainPanel.add(splitPane, BorderLayout.CENTER);
    add(mainPanel);
  }

  public void setController(ImageGUIController controller) {
    this.controller = controller;
  }

  private JButton createOperationCellButton(String name, String iconPath) {
    ImageIcon imageIcon =
        new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(30, 30,
            Image.SCALE_DEFAULT));
    JButton button = new JButton(name, imageIcon);
    button.setVerticalTextPosition(SwingConstants.BOTTOM);
    button.setHorizontalTextPosition(SwingConstants.CENTER);
    button.setActionCommand(name);
    button.setBorderPainted(false); // Remove border for better appearance
    button.setContentAreaFilled(false); // Remove content area for better appearance
    return button;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (e.getSource() == fileMenu) {
      statusLabel.setText("Open or save an image file or exit the program.");
    } else if (e.getSource() == helpMenu) {
      statusLabel.setText("Learn more about the program and how to use it.");
    } else if (e.getSource() == saveMenu) {
      statusLabel.setText("Save the image as jpg, png or ppm.");
    } else if (e.getSource() == openMenuItem) {
      statusLabel.setText("Open an image. This will replace the current image you're working on.");
    } else if (e.getSource() == quitMenuItem) {
      statusLabel.setText("Quit the application. Your unsaved image will be lost.");
    } else if (e.getSource() == aboutMenuItem) {
      statusLabel.setText("About this application.");
    } else if (e.getSource() == instructionMenuItem) {
      statusLabel.setText("Instructions on this application.");
    } else if (e.getSource() == imageViewHistogram) {
      statusLabel.setText("The histogram of the current image. It's 256x256, and each point "
                          + "indicates the frequency of the corresponding value in the channel.");
    } else if (e.getSource() == imageViewProcessing) {
      statusLabel.setText("The current image you're working on. Click operations on the left and "
                          + "the change will be reflected instantly.");
    } else {
      //do nothing
    }
  }

  /**
   * Invoked when the mouse button has been clicked (pressed and released) on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    //do nothing
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mousePressed(MouseEvent e) {
    //do nothing
  }

  /**
   * Invoked when a mouse button has been released on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    //do nothing
  }

  /**
   * Invoked when the mouse exits a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseExited(MouseEvent e) {
    statusLabel.setText("The bottom status bar will show instructions when mouse hovers on "
                        + "components.");

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == saveJPGItem) {
      final JFileChooser fc = new JFileChooser();
      fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fc.setDialogTitle("Save as JPG");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "jpg");
      fc.setAcceptAllFileFilterUsed(false);
      fc.setFileFilter(filter);
      int savePathJPG = fc.showSaveDialog(this);
      if (savePathJPG == JFileChooser.APPROVE_OPTION) {
        saveFilePath = fc.getSelectedFile().getAbsolutePath();
        controller.actionPerformed(e);
      }
    } else if (e.getSource() == savePNGItem) {
      final JFileChooser fc = new JFileChooser();
      fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fc.setDialogTitle("Save as PNG");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("png", "png");
      fc.setAcceptAllFileFilterUsed(false);
      fc.setFileFilter(filter);
      int savePathPNG = fc.showSaveDialog(this);
      if (savePathPNG == JFileChooser.APPROVE_OPTION) {
        saveFilePath = fc.getSelectedFile().getAbsolutePath();
        controller.actionPerformed(e);
      }
    } else if (e.getSource() == savePPMItem) {
      final JFileChooser fc = new JFileChooser();
      fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fc.setDialogTitle("Save as PPM");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("ppm", "ppm");
      fc.setAcceptAllFileFilterUsed(false);
      fc.setFileFilter(filter);
      int savePathPPM = fc.showSaveDialog(this);
      if (savePathPPM == JFileChooser.APPROVE_OPTION) {
        saveFilePath = fc.getSelectedFile().getAbsolutePath();
        controller.actionPerformed(e);
      }
    } else if (e.getSource() == openMenuItem) {
      final JFileChooser fc = new JFileChooser();
      fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fc.setDialogTitle("Open");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Image (jpg,png,ppm)", "jpg",
          "png", "ppm", "jpeg");
      fc.setAcceptAllFileFilterUsed(false);
      fc.setFileFilter(filter);
      int returnPath = fc.showOpenDialog(this);
      if (returnPath == JFileChooser.APPROVE_OPTION) {
        selectedFilePath = fc.getSelectedFile().getAbsolutePath();
        controller.actionPerformed(e);
      }
    } else if (e.getSource() == quitMenuItem) {
      //todo:check if the file has been saved, if not, show warning dialog
//      JOptionPane.showConfirmDialog(null, "Are you sure to quit without saving?",
//          "You haven't saved the image", YES_NO_CANCEL_OPTION);
      controller.actionPerformed(e);
    } else if (e.getSource() == aboutMenuItem) {
      JOptionPane.showMessageDialog(null, "This collaborative assignment is completed by Jiaming Xu"
                                          + " (xu.jiami@northeastern.edu) and Jiaoyang Du (du"
                                          + ".jiao@northeastern.edu).", "About",
          JOptionPane.PLAIN_MESSAGE);
    } else if (e.getSource() == instructionMenuItem) {
      JOptionPane.showMessageDialog(null, "Use top menu bar to read in and save images. \n"
                                          + "Image diagram is shown on the top left corner. \n"
                                          + "The current image is shown on the right. \n"
                                          + "Click and choose operations from the left panel. \n"
                                          + "Hover on parts of the applications and you will see "
                                          + "more details on the bottom bar. \n",
          "Usage",
          JOptionPane.PLAIN_MESSAGE);
    } else {
      //do nothing
    }
  }

  // add getter methods.
  public JMenuItem getSaveJPGItem() {
    return saveJPGItem;
  }

  public JMenuItem getSavePNGItem() {
    return savePNGItem;
  }

  public JMenuItem getSavePPMItem() {
    return savePPMItem;
  }

  public JMenuItem getOpenMenuItem() {
    return openMenuItem;
  }

  public JMenuItem getQuitMenuItem() {
    return quitMenuItem;
  }

  public JLabel getImageViewHistogram() {
    return imageViewHistogram;
  }

  public JLabel getImageViewProcessing() {
    return imageViewProcessing;
  }

//  public ButtonListener getButtonListener() {
//    return buttonListener;
//  }

  public String getSelectedFilePath() {
    return selectedFilePath;
  }

  public String getSaveFilePath() {
    return saveFilePath;
  }

//  public void updateImageViewProcessing(ImageIcon imageIcon) {
//    imageViewProcessing.setIcon(imageIcon);
//    imageViewProcessing.revalidate();
//    imageViewProcessing.repaint();
//  }

  public Image getCurrentDisplayedImage() {
    Icon icon = imageViewProcessing.getIcon();
    if (icon instanceof ImageIcon) {
      return (((ImageIcon) icon).getImage());
    }
    return null;
  }

  public void updateImageViewProcessing(MyImage myImage) {
    BufferedImage bufferedImage = convertToBufferedImage(myImage);
    ImageIcon imageIcon = new ImageIcon(bufferedImage);
    imageViewProcessing.setIcon(imageIcon);
    imageViewProcessing.revalidate();
    imageViewProcessing.repaint();

  }

  private BufferedImage convertToBufferedImage(MyImage myImage) {
    if (myImage == null) {
      return null;
    }
    int width = myImage.getWidth();
    int height = myImage.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        RGBPixel pixel = myImage.getPixel(y,x);
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();
        int alpha = 255;
        int argb = (alpha << 24 ) | (red << 16) | (green << 8) | blue;
        bufferedImage.setRGB(x, y, argb);
      }
    }
    return bufferedImage;
  }

  private static class ButtonListener implements ActionListener {
    private ImageGUIController controller;

    public ButtonListener(ImageGUIController controller) {
      this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      switch (command) {
        case "Compress":
          CompressDialog compressDialog = new CompressDialog();
          compressDialog.setVisible(true);
          compressDialog.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              controller.actionPerformed(e);
            }
          });
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

  /**
   * Update the image that is currently being processed.
   *
   * @param image the new image that is currently being processed
   */
  @Override
  public void updateProcessingImage(BufferedImage image) {

  }

  /**
   * Update the current image diagram.
   *
   * @param diagram the new image diagram
   */
  @Override
  public void updateDiagram(BufferedImage diagram) {

  }
}
