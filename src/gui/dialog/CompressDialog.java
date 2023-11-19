package gui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class represents the dialog windows that pops up when user trys to perform image
 * compression.
 */
public class CompressDialog extends JFrame {
  /**
   * Constructs a new frame that is initially invisible.
   * <p>
   * This constructor sets the component's locale property to the value returned by
   * <code>JComponent.getDefaultLocale</code>.
   *
   * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns true.
   */
  public CompressDialog() throws HeadlessException {
    JPanel mainPanel = new JPanel(new BorderLayout());
    setTitle("Choose Compression Ratio");
    setSize(800, 200);
    //Create the label.
    JLabel sliderLabel =
        new JLabel("The ratio indicates the size by which the image is reduced. ", JLabel.CENTER);
    sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    sliderLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    //Set the slider
    JSlider compressionSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    compressionSlider.setMajorTickSpacing(10);
    compressionSlider.setMinorTickSpacing(1);
    compressionSlider.setPaintTrack(true);
    compressionSlider.setPaintTicks(true);
    compressionSlider.setPaintLabels(true);
    compressionSlider.setBorder(
        BorderFactory.createEmptyBorder(0, 10, 0, 10));
    compressionSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
    // show the rate user picked
    JLabel valueLabel = new JLabel("Compression ratio: " + compressionSlider.getValue() + "%",
        JLabel.CENTER);
    compressionSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int value = ((JSlider) e.getSource()).getValue();
        valueLabel.setText("Compression ratio: " + value + "%");
      }
    });
    valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

    JButton button=new JButton("Confirm");
    button.setActionCommand("Confirm");
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.add(button);

    mainPanel.add(sliderLabel, BorderLayout.NORTH);
    mainPanel.add(compressionSlider, BorderLayout.CENTER);
    mainPanel.add(valueLabel, BorderLayout.SOUTH);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    add(mainPanel);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
