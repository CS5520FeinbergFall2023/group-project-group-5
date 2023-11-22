package gui.dialog;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.image.MyImage;

public class SplitOperationDialog extends JFrame implements ChangeListener, ImageUpdateInterface{
  //private ImageIcon splitViewImage;
  private JLabel imageViewProcessing;
  private SplitViewDialogListener splitViewDialogListener;

  public void setSplitViewDialogListener(SplitViewDialogListener splitViewDialogListener) {
    this.splitViewDialogListener = splitViewDialogListener;
  }

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
    //this.splitViewImage = splitViewImage;

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
    slider.addChangeListener(this);
    // the split view image
    //the image under process
    JScrollPane scrollPane = new JScrollPane();
    imageViewProcessing = new JLabel(splitViewImage);
    imageViewProcessing.setAlignmentX(Component.CENTER_ALIGNMENT);
    scrollPane.setViewportView(imageViewProcessing);
    //confirm button
    JButton button = new JButton("Confirm");
    button.setActionCommand("Confirm");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (splitViewDialogListener != null) {
          splitViewDialogListener.onConfirm();
        }
        dispose();
      }
    });

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.add(button);
    // add everything to the main panel
    mainPanel.add(sliderLabel);
    mainPanel.add(slider);
    mainPanel.add(scrollPane);
    mainPanel.add(bottomPanel);

    add(mainPanel);
    pack();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); // Center the frame on the screen
  }

  /**
   * Update the image that is currently being processed.
   *
   * @param image the new image that is currently being processed
   */
  @Override
  public void updateProcessingImage(BufferedImage image) {
    imageViewProcessing.setIcon(new ImageIcon(image));
  }

  /**
   * Update the current image diagram.
   *
   * @param diagram the new image diagram
   */
  @Override
  public void updateDiagram(BufferedImage diagram) {
    //do nothing
    return;
  }

  /**
   * Invoked when the target of the listener has changed its state.
   *
   * @param e a ChangeEvent object
   */
  @Override
  public void stateChanged(ChangeEvent e) {
    int value = ((JSlider) e.getSource()).getValue();
    if (splitViewDialogListener != null) {
      splitViewDialogListener.onUpdatePercentage(value / 100f, this);
    }
  }

}
