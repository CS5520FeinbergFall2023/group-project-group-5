package gui.dialog;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.image.MyImage;

/**
 * This class represents the dialog windows that pops up when user try to performs level
 * adjustment.
 */
public class LevelAdjustmentDialog extends JDialog implements CurveInterface, ImageUpdateInterface {
  private Point[] controlPoints = new Point[3];
  private int padding = 5;

  private CurvePanel curvePanel;
  private JLabel imageViewProcessing;

  /**
   * Constructs a new frame that is initially invisible. This constructor sets the component's
   * locale property to the value returned by
   *
   * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns true.
   */
  public LevelAdjustmentDialog() throws HeadlessException {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    setTitle("Level Adjustment");
    setSize(800, 800);
    controlPoints[0] = new Point(0, 0);
    controlPoints[1] = new Point(128, 128);
    controlPoints[2] = new Point(255, 255);


    JLabel label = new JLabel("<html>Control the curve that manipulated the image's "
                              + "histogram<br> by setting (Black,0), (Middle,128) and (White,255)."
                              + "<br>Use the slider to check the effect in split view.<br> "
                              + "It will not be saved until you press confirm. </html>",
        JLabel.CENTER);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    label.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

    //control points
    JPanel valuesPanel = new JPanel();
    valuesPanel.setLayout(new BoxLayout(valuesPanel, BoxLayout.Y_AXIS));
    SpinnerNumberModel blackSpinnerNumberModel = new SpinnerNumberModel(0.0, 0.0, 255.0, 1.0);
    SpinnerNumberModel midSpinnerNumberModel = new SpinnerNumberModel(128.0, 0.0, 255.0, 1.0);
    SpinnerNumberModel whiteSpinnerNumberModel = new SpinnerNumberModel(255.0, 0.0, 255.0, 1.0);
    blackSpinnerNumberModel.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if ((double) blackSpinnerNumberModel.getNumber() < 0) {
          blackSpinnerNumberModel.setValue(0);
        }
        if ((double) blackSpinnerNumberModel.getNumber()
            >= (double) midSpinnerNumberModel.getNumber()) {
          blackSpinnerNumberModel.setValue((double) midSpinnerNumberModel.getNumber() - 1);
        }
        controlPoints[0].setLocation((double) blackSpinnerNumberModel.getNumber(), 0);
        curvePanel.repaint();
      }
    });

    midSpinnerNumberModel.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if ((double) midSpinnerNumberModel.getNumber()
            <= (double) blackSpinnerNumberModel.getNumber()) {
          midSpinnerNumberModel.setValue((double) blackSpinnerNumberModel.getNumber() + 1);
        }
        if ((double) midSpinnerNumberModel.getNumber()
            >= (double) whiteSpinnerNumberModel.getNumber()) {
          midSpinnerNumberModel.setValue((double) whiteSpinnerNumberModel.getNumber() - 1);
        }
        controlPoints[1].setLocation((double) midSpinnerNumberModel.getNumber(), 128);
        curvePanel.repaint();

      }
    });

    whiteSpinnerNumberModel.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if ((double) whiteSpinnerNumberModel.getNumber() > 255.0) {
          whiteSpinnerNumberModel.setValue(255.0);
        }
        if ((double) whiteSpinnerNumberModel.getNumber()
            <= (double) midSpinnerNumberModel.getNumber()) {
          whiteSpinnerNumberModel.setValue((double) midSpinnerNumberModel.getNumber() + 1);
        }
        controlPoints[2].setLocation((double) whiteSpinnerNumberModel.getNumber(), 255);
        curvePanel.repaint();

      }
    });

    curvePanel = new CurvePanel();
    valuesPanel.add(curvePanel);
    addLabeledSpinner(valuesPanel, "Black", blackSpinnerNumberModel);
    addLabeledSpinner(valuesPanel, "Middle", midSpinnerNumberModel);
    addLabeledSpinner(valuesPanel, "White", whiteSpinnerNumberModel);
    valuesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//    JPanel imagePanel = new JPanel();
//    ImageIcon imageIcon = new ImageIcon("res/city-histogram.png");
//    JLabel imageView = new JLabel(imageIcon);
//    imagePanel.add(imageView);

//    JLayeredPane layeredPane = new JLayeredPane();
//    layeredPane.setLayout(new BorderLayout());
//    layeredPane.add(imagePanel);
//    layeredPane.add(curvePanel);


    JPanel splitViewPanel = new JPanel();
    splitViewPanel.setLayout(new BoxLayout(splitViewPanel, BoxLayout.Y_AXIS));
    //the slider to control split view percentage
    JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTrack(true);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    slider.setAlignmentX(Component.CENTER_ALIGNMENT);

    //the image under process
    JScrollPane scrollPane = new JScrollPane();
//    ImageIcon imageIconProcessing = new ImageIcon("resources/icons/sofa-purple-living-room-with"
//                                                  + "-copy-space.jpg");
    ImageIcon imageIconProcessing = new ImageIcon("res/city-level-adjustment-100-150-200.png");
    imageViewProcessing = new JLabel(imageIconProcessing);
    scrollPane.setViewportView(imageViewProcessing);
    splitViewPanel.add(slider);
    splitViewPanel.add(scrollPane);

    JPanel operationPanel = new JPanel(new BorderLayout());
    operationPanel.add(valuesPanel, BorderLayout.WEST);
    operationPanel.add(splitViewPanel, BorderLayout.CENTER);
    //confirm button
    JButton button = new JButton("Confirm");
    button.setActionCommand("Confirm");
    button.addActionListener(e -> dispose());
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.add(button);

    mainPanel.add(label);
//    mainPanel.add(valuesPanel);
//    mainPanel.add(slider);
//    mainPanel.add(scrollPane);
    mainPanel.add(operationPanel);
    mainPanel.add(bottomPanel);
    add(mainPanel);
    setLocationRelativeTo(null); // Center the frame on the screen
  }

  private JSpinner addLabeledSpinner(Container c, String label, SpinnerModel model) {
    JLabel l = new JLabel(label);
    c.add(l);
    JSpinner spinner = new JSpinner(model);
    l.setLabelFor(spinner);
    JPanel spinnerPanel = new JPanel();
    spinnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    spinnerPanel.add(spinner);
    c.add(spinnerPanel);
    return spinner;
  }

  private class CurvePanel extends JPanel {
    public Dimension getPreferredSize() {
      return new Dimension(256 + 2 * padding, 256 + 2 * padding);
    }

    @Override
    protected void paintComponent(Graphics g) {
      setBackground(Color.white);
//      setOpaque(false);
      setSize(256 + 2 * padding, 256 + 2 * padding);
//      setAlignmentX(Component.CENTER_ALIGNMENT);
//      setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


      // Draw control points
      g.setColor(Color.RED);
      for (Point point : controlPoints) {
        int x = point.x + padding;
        int y = 255 - point.y + padding;
        g.fillOval(x - padding, y - padding, 2 * padding, 2 * padding);
      }
      // Set the stroke
      g2d.setStroke(new BasicStroke(2));
      // Draw quadratic curve
      double black = controlPoints[0].getX();
      double mid = controlPoints[1].getX();
      double white = controlPoints[2].getX();
      double denominator =
          black * black * (mid - white) - black * (mid * mid - white * white) + white * mid * mid
          - mid * white * white;
      double aa = -black * (128 - 255) + 128 * white - 255 * mid;
      double ab = black * black * (128 - 255) + 255 * mid * mid - 128 * white * white;
      double ac =
          black * black * (255 * mid - 128 * white) - black * (255 * mid * mid
                                                               - 128 * white * white);
      double a = aa / denominator;
      double b = ab / denominator;
      double c = ac / denominator;

      GeneralPath path = new GeneralPath();
      double x = black;
      double y = 0;
      path.moveTo(x + padding, 255 - y + padding);
      for (int t = 0; t <= 100; t++) {
        x = black + t * (white - black) / 100.0;
        y = a * x * x + b * x + c;
        if (y < 0) {
          y = 0;
        }
        if (y > 255) {
          y = 255;
        }
        path.lineTo(x + padding, 255 - y + padding);
      }
      g2d.setColor(Color.BLACK);
      g2d.draw(path);
    }
  }

  /**
   * Get the control point values of a curve.
   *
   * @return the control point values of a curve
   */
  @Override
  public float[] getControlPointValues() {
    return new float[]{(float) controlPoints[0].getX(), (float) controlPoints[1].getX(),
        (float) controlPoints[2].getX()};
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
   * Update the image that is currently being processed.
   *
   * @param myImage the new image that is currently being processed
   */
  @Override
  public void updateImageViewProcessing(MyImage myImage) {

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
}

