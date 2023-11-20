package gui.dialog;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class represents the dialog windows that pops up when user trys to performs level
 * adjustment.
 */
public class LevelAdjustmentDialog extends JFrame {
  private Point[] controlPoints = new Point[3];
  private int padding = 5;


  /**
   * Constructs a new frame that is initially invisible. This constructor sets the component's
   * locale property to the value returned by
   *
   * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns true.
   */
  public LevelAdjustmentDialog() throws HeadlessException {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    setTitle("Level Adjustment");
    setResizable(false);
    controlPoints[0] = new Point(0, 0);
    controlPoints[1] = new Point(128, 128);
    controlPoints[2] = new Point(255, 255);


    JLabel label = new JLabel("<html>Control the curve that manipulated the image's "
                              + "histogram<br> by "
                              + "setting (Black,0), (Middle,128) and (White,255).</html>",
        JLabel.CENTER);
    label.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

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
      }
    });


    addLabeledSpinner(valuesPanel, "Black", blackSpinnerNumberModel);
    addLabeledSpinner(valuesPanel, "Middle", midSpinnerNumberModel);
    addLabeledSpinner(valuesPanel, "White", whiteSpinnerNumberModel);
    valuesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//    JPanel imagePanel = new JPanel();
//    ImageIcon imageIcon = new ImageIcon("res/city-histogram.png");
//    JLabel imageView = new JLabel(imageIcon);
//    imagePanel.add(imageView);
    CurvePanel curvePanel = new CurvePanel();

//    JLayeredPane layeredPane = new JLayeredPane();
//    layeredPane.setLayout(new BorderLayout());
//    layeredPane.add(imagePanel);
//    layeredPane.add(curvePanel);

    mainPanel.add(label, BorderLayout.NORTH);
    mainPanel.add(curvePanel, BorderLayout.CENTER);
    mainPanel.add(valuesPanel, BorderLayout.WEST);
    add(mainPanel);
    pack();
    setLocationRelativeTo(null); // Center the frame on the screen
  }

  private JSpinner addLabeledSpinner(Container c, String label, SpinnerModel model) {
    JLabel l = new JLabel(label);
    c.add(l);
    JSpinner spinner = new JSpinner(model);
    l.setLabelFor(spinner);
    c.add(spinner);
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
      setAlignmentX(Component.CENTER_ALIGNMENT);
      setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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

//      g2d.draw(new QuadCurve2D.Float(
//          controlPoints[0].x + padding, 255-controlPoints[0].y + padding,
//          controlPoints[1].x + padding, 255-controlPoints[1].y + padding,
//          controlPoints[2].x + padding, 255-controlPoints[2].y + padding));
      GeneralPath path = new GeneralPath();
      double x = black;
      double y = 0;
      path.moveTo(x + padding, 255 - y + padding);
      for (double t = 0.0; t <= 1.0; t += 0.01) {
        x = black + t * (white - black);
        y = a * x * x + b * x + c;
        if (y < 0) {
          y = 0;
        }
        if (y > 255) {
          y = 255;
        }
        // Adjust coordinates for Java Swing painting
//        double adjustedY = getHeight() - y; // Assuming top-left is (0, 0)
        path.lineTo(x + padding, 255 - y + padding);
      }
      g2d.setColor(Color.BLACK);
      g2d.draw(path);
    }
  }

}

