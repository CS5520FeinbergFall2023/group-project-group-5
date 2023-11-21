package gui.dialog;

/**
 * This interface represents windows that needs to pass percentage values.
 */
public interface PercentageInterface {
  /**
   * Get the percentage value in [0,1].
   *
   * @return the percentage value
   */
  int getPercentage();
}
