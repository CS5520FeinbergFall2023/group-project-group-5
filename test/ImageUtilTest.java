import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import util.ImageUtil;

import static org.junit.Assert.assertTrue;

/**
 * This test is used to test the behavior of the ImageUtil class, including checking the main
 * program's response to command line arguments and user interaction.
 */
public class ImageUtilTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private ByteArrayInputStream inContent;

  /**
   * Set up a stream before each test to capture and redirect the output of System.out.
   */
  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  /**
   * Resume the stream after each test, restoring System.out to its original state.
   */
  @After
  public void restoreStreams() {
    System.setOut(originalOut);
    if (inContent != null) {
      try {
        inContent.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Test whether the main program can correctly handle command line parameters.
   */
  @Test
  public void testMainWithCommandLineArgs() {
    String[] args = {"-file", "test\\file\\script.txt"};
    ImageUtil.main(args);
    String output = outContent.toString();
    assertTrue(output.contains("Loading new image: cupcake"));
    assertTrue(output.contains("Image blurred"));
    assertTrue(output.contains("Sharpen image"));
  }

  /**
   * Test whether the main program can start normally and handle user interactive input(console).
   */
  @Test
  public void testMainWithoutArgs1() {
    inContent = new ByteArrayInputStream("1\n exit\n".getBytes());
    System.setIn(inContent);

    String[] args = {};
    ImageUtil.main(args);

    String output = outContent.toString();
    assertTrue(
        output.contains("Please select which mode you want to input the commands: 1. Console"));
  }

  /**
   * Test whether the main program can start normally and handle user interactive input(from file).
   */
  @Test
  public void testMainWithoutArgs2() {
    //
    inContent = new ByteArrayInputStream("2\nexit\n".getBytes());
    System.setIn(inContent);

    String[] args = {};
    ImageUtil.main(args);

    String output = outContent.toString();
    assertTrue(output.contains("2. From file"));
  }

  /**
   * Test whether the main program can start normally and handle user interactive input(Exit).
   */
  @Test
  public void testMainWithoutArgs3() {
    inContent = new ByteArrayInputStream("3\nexit\n".getBytes());
    System.setIn(inContent);

    String[] args = {};
    ImageUtil.main(args);

    String output = outContent.toString();
    assertTrue(output.contains("3. Exit"));
  }
}
