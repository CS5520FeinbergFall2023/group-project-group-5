package gui.dialog;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import model.Channel;

/**
 * This class represents the dialog windows that pops up when user try to get certain color
 * component of the image.
 */
public class ColorComponentDialog extends JDialog implements ActionListener, ChannelDialogListener {
  private static String redString = "Red";
  private static String greenString = "Green";
  private static String blueString = "Blue";

  private ButtonGroup radioButtonGroup;

  private JLabel picture;
  private static Map<String, String> iconPath = new HashMap<>();

  private ChannelDialogListener channelListener;

  public void setChannelListener(ChannelDialogListener channelListener) {
    this.channelListener = channelListener;
  }

  /**
   * Constructs a new frame that is initially invisible. This constructor sets the component's
   * locale property to the value returned by
   *
   * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns true.
   */
  public ColorComponentDialog() throws HeadlessException {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    setTitle("Get color component");
    setSize(400, 400);

    //radio button group
    JRadioButton redButton = new JRadioButton(redString);
    redButton.setActionCommand(redString);
    redButton.setSelected(true);
    JRadioButton greenButton = new JRadioButton(greenString);
    greenButton.setActionCommand(greenString);
    JRadioButton blueButton = new JRadioButton(blueString);
    blueButton.setActionCommand(blueString);
    radioButtonGroup = new ButtonGroup();
    radioButtonGroup.add(redButton);
    radioButtonGroup.add(greenButton);
    radioButtonGroup.add(blueButton);
    //Register a listener for the radio buttons.
    redButton.addActionListener(this);
    greenButton.addActionListener(this);
    blueButton.addActionListener(this);
    //Put the radio buttons in a column in a panel.
    JPanel radioPanel = new JPanel(new GridLayout(0, 1));
    radioPanel.add(redButton);
    radioPanel.add(greenButton);
    radioPanel.add(blueButton);
    radioPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 10));

    //confirm button
    JButton button = new JButton("Confirm");
    button.setActionCommand("Confirm");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Channel channel = getChannel();
        System.out.println(channel);
        if (channelListener != null) {
          channelListener.onColorComponentConfirmed(channel);
        }
        dispose();
      }
    });
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.add(button);

    iconPath.put(redString, "resources/icons/red-component.png");
    iconPath.put(greenString, "resources/icons/green-component.png");
    iconPath.put(blueString, "resources/icons/blue-component.png");
    picture = new JLabel(new ImageIcon(iconPath.get(redString)));

    add(radioPanel, BorderLayout.LINE_START);
    add(picture, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); // Center the frame on the screen

  }

  @Override
  public void actionPerformed(ActionEvent e) {

    picture.setIcon(new ImageIcon(iconPath.get(e.getActionCommand())));

  }

  public Channel getChannel() {
    switch (radioButtonGroup.getSelection().getActionCommand()) {
      case "Red":
        return Channel.RED;
      case "Green":
        return Channel.GREEN;
      case "Blue":
        return Channel.BLUE;
      default:
        throw new IllegalArgumentException("Channel selected is illegal");
    }
  }

  @Override
  public void onColorComponentConfirmed(Channel channel) {
    // do nothing.
  }
}
