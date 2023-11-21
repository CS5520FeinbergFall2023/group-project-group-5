package gui.dialog;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.ImageGUIController;
import model.image.MyImage;

/**
 * This class represents the dialog windows that pops up when user try to perform image
 * compression.
 */


//public class CompressDialog extends JFrame {
//
//  private CompressionListener compressionListener;
//  private ImageGUIController imageGUIController;
//
//  public ImageGUIController getImageGUIController() {
//    return imageGUIController;
//  }
//
//  public void setCompressionListener(CompressionListener listener) {
//    this.compressionListener = listener;
//  }

public class CompressDialog extends JFrame implements PercentageInterface {
  private final JSlider compressionSlider;
//  private final ImageGUIController controller;
  //private ActionListener actionListener;


  /**
   * Constructs a new frame that is initially invisible. This constructor sets the component's
   * locale property to the value returned by.
   *
   * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns true.
   */
  public CompressDialog() throws HeadlessException {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    setTitle("Choose Compression Ratio");
    setSize(800, 200);

    // Create the label.
    JLabel sliderLabel =
          new JLabel("The ratio indicates the size by which the image is reduced.", JLabel.CENTER);
    sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    sliderLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

    // Set the slider
    compressionSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    compressionSlider.setMajorTickSpacing(10);
    compressionSlider.setMinorTickSpacing(1);
    compressionSlider.setPaintTrack(true);
    compressionSlider.setPaintTicks(true);
    compressionSlider.setPaintLabels(true);
    compressionSlider.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    compressionSlider.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Show the rate user picked
    JLabel valueLabel =
          new JLabel("Compression ratio: " + compressionSlider.getValue() + "%", JLabel.CENTER);
    compressionSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int value = ((JSlider) e.getSource()).getValue();
        valueLabel.setText("Compression ratio: " + value + "%");
      }
    });
    valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

    //confirm button
    JButton button = new JButton("Confirm");
    button.setActionCommand("Confirm");
    button.addActionListener(e -> dispose());

//    button.addActionListener(e-> {
//      int compressionValue = compressionSlider.getValue();
//      if (compressionListener != null) {
//        compressionListener.onCompressionConfirmed(compressionValue);
//      }
//      ImageGUIController controller = getImageGUIController();
//
//      controller.compressOperation(compressionValue);
//      this.dispose();
//    });
//    button.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        float selectedCompression = getPercentage();
//        ActionEvent compressEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
//              "Compress", (int)selectedCompression);
//        if (actionListener != null) {
//          actionListener.actionPerformed(compressEvent);
//        }
//      }
//    });
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.add(button);

    mainPanel.add(sliderLabel);
    mainPanel.add(compressionSlider);
    mainPanel.add(valueLabel);
    mainPanel.add(bottomPanel);

    add(mainPanel);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); // Center the frame on the screen
  }


//  public void setActionListener(ActionListener listener) {
//    this.actionListener = listener;
//  }

  /**
   * Get the percentage value in [0,1].
   *
   * @return the percentage value
   */
  @Override
  public float getPercentage() {
    return compressionSlider.getValue() / 100f;
  }

}
