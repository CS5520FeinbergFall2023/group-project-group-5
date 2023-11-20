package gui.dialog;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SplitOperationDialog extends JFrame {
  private ImageIcon splitViewImage;
  private JLabel imageViewProcessing;

  /**
   * Creates a new, initially invisible <code>Frame</code> with the specified title.
   * <p>
   * This constructor sets the component's locale property to the value returned by
   * <code>JComponent.getDefaultLocale</code>.
   *
   * @param title the title for the frame
   * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns true.
   */
  public SplitOperationDialog(String title, ImageIcon splitViewImage)
      throws HeadlessException {
    super(title);
    this.splitViewImage = splitViewImage;

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    // Create the label for instructions.
    String instructions="<html> Move the slider to see split view effect.<br/> It will not be "
                        + "saved until you press confirm.</html>";
    JLabel sliderLabel = new JLabel(instructions, JLabel.CENTER);
    sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    sliderLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    //slider to control split view percentage
    JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTrack(true);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    slider.setAlignmentX(Component.CENTER_ALIGNMENT);
    // the split view image
    imageViewProcessing = new JLabel(splitViewImage);
    imageViewProcessing.setAlignmentX(Component.CENTER_ALIGNMENT);
    //confirm button
    JButton button = new JButton("Confirm");
    button.setActionCommand("Confirm");
    button.addActionListener(e -> dispose());
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.add(button);
    // add everything to the main panel
    mainPanel.add(sliderLabel);
    mainPanel.add(slider);
    mainPanel.add(imageViewProcessing);
    mainPanel.add(bottomPanel);

    add(mainPanel);
    pack();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); // Center the frame on the screen
  }


}
