package model;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the Pixel class.
 */
public class ImageTest {
  boolean testSave(Image image, String path) {
    try {
      image.save(path);
      File file = new File(path);
      return file.exists() && file.isFile();
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  boolean testImgArrayAddition(Image image, float[] array, String expectedString) {
    String originalString = image.toString();
    assertEquals(expectedString, image.imgArrayAddition(array).toString());
    assertEquals(originalString, image.toString());
    return true;
  }

  boolean testFiltering(Image image, float[][] kernel, String expectedString) {
    String originalString = image.toString();
    assertEquals(expectedString, image.filtering(kernel).toString());
    assertEquals(originalString, image.toString());
    return true;
  }

  boolean testMatrixMultiplication(Image image, float[][] matrix, String expectedString) {
    String originalString = image.toString();
    assertEquals(expectedString, image.matrixMultiplication(matrix).toString());
    assertEquals(originalString, image.toString());
    return true;
  }

  boolean testAddition(Image image1, Image image2, String expectedString) {
    String originalString1 = image1.toString();
    String originalString2 = image2.toString();
    assertEquals(expectedString, image1.addition(image2).toString());
    assertEquals(expectedString, image2.addition(image1).toString());
    assertEquals(originalString1, image1.toString());
    assertEquals(originalString2, image2.toString());
    return true;
  }

  boolean testProjectCoordinate(Image image, int[][] matrix, String expectedString) {
    String originalString = image.toString();
    assertEquals(expectedString, image.projectCoordinate(matrix).toString());
    assertEquals(originalString, image.toString());
    return true;
  }


}