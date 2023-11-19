package gui.dialog;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * This class represents the dialog windows that pops up when user trys to get certain color
 * component of the image.
 */
public class ColorComponentDialog extends JFrame implements ActionListener {
  private static String redString = "Red";
  private static String greenString = "Green";
  private static String blueString = "Blue";

  private JLabel picture;
  private static Map<String, String> iconPath = new HashMap<>();

  /**
   * Constructs a new frame that is initially invisible.
   * <p>
   * This constructor sets the component's locale property to the value returned by
   * <code>JComponent.getDefaultLocale</code>.
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
    ButtonGroup group = new ButtonGroup();
    group.add(redButton);
    group.add(greenButton);
    group.add(blueButton);
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
    button.addActionListener(e -> dispose());
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

  }

  @Override
  public void actionPerformed(ActionEvent e) {

    picture.setIcon(new ImageIcon(iconPath.get(e.getActionCommand())));

  }

}
