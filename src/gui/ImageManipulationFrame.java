package gui;

import java.util.HashMap;
import java.util.Map;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * This class represents the frame for the image manipulation GUI.
 */
public class ImageManipulationFrame extends JFrame {
  @SuppressWarnings("checkstyle:WhitespaceAfter")
  public ImageManipulationFrame() {
    super();
    setTitle("Image Manipulation");
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel mainPanel = new JPanel(new BorderLayout());

    //menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openMenuItem = new JMenuItem("Open");
    JMenu saveMenuItem = new JMenu("Save As...");
    saveMenuItem.add(new JMenuItem("JPG"));
    saveMenuItem.add(new JMenuItem("PNG"));
    saveMenuItem.add(new JMenuItem("PPM"));
    JMenuItem quitMenuItem = new JMenuItem("Quit");
    fileMenu.add(openMenuItem);
    fileMenu.add(saveMenuItem);
    fileMenu.add(quitMenuItem);
    JMenu helpMenu = new JMenu("Help");
    JMenuItem aboutMenuItem = new JMenuItem("About");
    helpMenu.add(aboutMenuItem);
    menuBar.add(fileMenu);
    menuBar.add(helpMenu);
    mainPanel.add(menuBar, BorderLayout.PAGE_START);

    //
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setDividerLocation(250);

    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.setPreferredSize(new Dimension(221, 600));

    JPanel imagePanel = new JPanel();
    ImageIcon imageIcon = new ImageIcon("res/city-histogram.png");
    JLabel imageView = new JLabel(imageIcon);
    imagePanel.add(imageView);
    leftPanel.add(imagePanel, BorderLayout.NORTH);

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

    JPanel gridPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    leftPanel.add(gridPanel, BorderLayout.CENTER);


    for (String operationName : operations.keySet()) {
      JPanel cellPanel = new JPanel(new BorderLayout());
      JLabel label = new JLabel(operationName, SwingConstants.CENTER);
      ImageIcon imageIconOperation =
          new ImageIcon(
              new ImageIcon(operations.get(operationName)).getImage().getScaledInstance(30, 30,
                  Image.SCALE_DEFAULT));
      JLabel imageLabel = new JLabel(imageIconOperation);
      cellPanel.add(imageLabel, BorderLayout.CENTER);
      cellPanel.add(label, BorderLayout.SOUTH);
      gridPanel.add(cellPanel);
    }
    leftPanel.add(gridPanel, BorderLayout.CENTER);

    JScrollPane scrollPane = new JScrollPane();
    ImageIcon imageIconProcessing = new ImageIcon("res/city.png");
    // path
    JLabel imageViewProcessing = new JLabel(imageIconProcessing);
    scrollPane.setViewportView(imageViewProcessing);

    splitPane.setLeftComponent(leftPanel);
    splitPane.setRightComponent(scrollPane);

    mainPanel.add(splitPane, BorderLayout.CENTER);

    JLabel statusLabel = new JLabel("The bottom status bar will show instructions when mouse "
                                    + "hovers on components.");
    statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    statusLabel.setBorder(new EmptyBorder(5,0,5,10));
    mainPanel.add(statusLabel,BorderLayout.SOUTH);

    add(mainPanel);
  }
}
