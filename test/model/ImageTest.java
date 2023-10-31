package model;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * This class tests the Pixel class
 */
public class ImageTest {
  protected boolean testSave(Image image,String path)
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

  protected boolean testAddition(Image image1,Image image2,String expectedString)
  {
    String originalString1=image1.toString();
    String originalString2=image2.toString();
    assertEquals(expectedString,image1.addition(image2).toString());
    assertEquals(expectedString,image2.addition(image1).toString());
    assertEquals(originalString1,image1.toString());
    assertEquals(originalString2,image2.toString());
    return true;
  }



}