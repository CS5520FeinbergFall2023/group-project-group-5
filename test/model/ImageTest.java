package model;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * This class tests the Pixel class
 */
public class ImageTest {
  static boolean testSave(Image image,String path)
  {
    try {
      image.save(path);
      File file = new File(path);
      return file.exists() && file.isFile();
    }catch (IOException e)
    {
      return false;
    }
  }
}