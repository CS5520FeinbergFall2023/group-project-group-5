package gui.dialog;

/**
 * This interface sets listener to the dialog that need delivery the percentage value.
 */
public interface PercentageDialogListener {

  /**
   *
   * @param percentage the percentage value that user choose.
   */
  void onCompressionConfirmed(float percentage);
}
