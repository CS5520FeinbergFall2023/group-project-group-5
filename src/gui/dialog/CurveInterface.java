package gui.dialog;

/**
 * This interface represents windows that needs to pass control point values of a curve.
 */
public interface CurveInterface {
  /**
   * Get the control point values of a curve.
   *
   * @return the control point values of a curve
   */
  float[] getControlPointValues();
}
