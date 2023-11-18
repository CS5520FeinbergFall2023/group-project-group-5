package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * This class represents the frame for the image manipulation GUI.
 */
public class ImageManipulationFrame extends JFrame {
  public ImageManipulationFrame() {
    super();
    setTitle("Image Manipulation");
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel mainPanel = new JPanel(new BorderLayout());

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openMenuItem = new JMenuItem("Open...");
    JMenuItem saveMenuItem = new JMenuItem("Save");
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

    add(mainPanel);
  }
}
