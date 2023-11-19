package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gui.dialog.ColorComponentDialog;
import gui.dialog.CompressDialog;

import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;

/**
 * This class represents the frame for the image manipulation GUI.
 */
public class ImageManipulationFrame extends JFrame implements ActionListener {
  private final JMenuItem saveMenuItem;
  private final JMenuItem openMenuItem;
  private final JMenuItem quitMenuItem;
  private final JMenuItem aboutMenuItem;
  private final JMenuItem instructionMenuItem;
  private final ButtonActionListener buttonActionListener = new ButtonActionListener();

  /**
   * Constructs an ImageManipulationFrame.
   */
  public ImageManipulationFrame() {
    super();
    setTitle("Image Manipulation");
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel mainPanel = new JPanel(new BorderLayout());

    //menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    openMenuItem = new JMenuItem("Open");
    openMenuItem.addActionListener(this);
    saveMenuItem = new JMenuItem("Save As");
    saveMenuItem.addActionListener(this);
    quitMenuItem = new JMenuItem("Quit");
    quitMenuItem.addActionListener(this);
    fileMenu.add(openMenuItem);
    fileMenu.add(saveMenuItem);
    fileMenu.add(quitMenuItem);
    JMenu helpMenu = new JMenu("Help");
    aboutMenuItem = new JMenuItem("About");
    aboutMenuItem.addActionListener(this);
    instructionMenuItem = new JMenuItem("Instructions");
    instructionMenuItem.addActionListener(this);
    helpMenu.add(instructionMenuItem);
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
      JButton button = createOperationCellButton(operationName, operations.get(operationName));
      gridPanel.add(button);
      button.addActionListener(buttonActionListener);
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
    statusLabel.setBorder(new EmptyBorder(5, 0, 5, 10));
    mainPanel.add(statusLabel, BorderLayout.SOUTH);

    add(mainPanel);
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
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == saveMenuItem) {
      final JFileChooser fc = new JFileChooser();
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      fc.setDialogTitle("Save as");
      fc.showSaveDialog(this);
    } else if (e.getSource() == openMenuItem) {
      final JFileChooser fc = new JFileChooser();
      fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fc.setDialogTitle("Open");
      fc.showSaveDialog(this);
    } else if (e.getSource() == quitMenuItem) {
      //todo:check if the file has been saved, if not, show warning dialog
      JOptionPane.showConfirmDialog(null, "Are you sure to quit without saving?",
          "You haven't saved the image", YES_NO_CANCEL_OPTION);
    } else if (e.getSource() == aboutMenuItem) {
      JOptionPane.showMessageDialog(null, "This collaborative assignment is completed by Jiaming Xu"
                                          + " (xu.jiami@northeastern.edu) and Jiaoyang Du (du"
                                          + ".jiao@northeastern.edu).", "About",
          JOptionPane.PLAIN_MESSAGE);
    } else if (e.getSource() == instructionMenuItem) {
      JOptionPane.showMessageDialog(null, "Use top menu bar to read in and save images. \n"
                                          + "Image diagram is shown on the top left corner. \n"
                                          + "The current image is shown on the right. You can "
                                          + "drag and use ctrl+mouse scroll to enlarge or shrink "
                                          + "the size. \n"
                                          + "Click and choose operations from the left panel. \n"
                                          + "Hover on parts of the applications and you will see "
                                          + "more details on the bottom bar. \n",
          "Usage",
          JOptionPane.PLAIN_MESSAGE);
    } else {
      //do nothing
    }
  }


  private static class ButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      System.out.println(command);
      switch (command) {
        case "Compress":
          CompressDialog compressDialog = new CompressDialog();
          compressDialog.setVisible(true);
          break;
        case "Color Component":
          ColorComponentDialog colorComponentDialog=new ColorComponentDialog();
          colorComponentDialog.setVisible(true);
          break;
        default:
      }
    }
  }
}
