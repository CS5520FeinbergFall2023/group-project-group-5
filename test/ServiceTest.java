import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import model.Axis;
import model.Channel;
import model.Image;
import model.MyImage;
import model.Pixel;
import service.ImageService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Tests {
  private ImageService imageService;
  private Image testImgae;

  @Before
  public void setUp() throws Exception {
    imageService = new ImageService();
  }

//  -------------------Load an image from an ASCII PPM, JPG or PNG file.----------------------------
  // remember to test both JPEG & JPG extension
  // test malformed path
  // well-formed path, but the img does not exist
  // img exist but not PPM,JPG or PNG extension
  // PPM, JPG & PNG, monochromatic(black,white,red,green,blue), dichromatic or trichromatic. With
  // each value 0,(a number in between 0-255),255.

  @Test
  public void testLoadJPEG() {
    try {
      testImgae = new MyImage("test/img/car.jpg");
    } catch (IllegalArgumentException e) {
      fail("Load supported jpg file should not throw exception");
    }
  }

//  @Test
//  public void testLoadPNG() {
//    try {
//      testImgae.load("img/city.png");
//    } catch (IOException e) {
//      fail("Load supported png file should not throw exception");
//    }
//  }
//
//  @Test
//  public void testLoadPPM() {
//    try {
//      testImgae.load("img/rose.ppm");
//    } catch (IOException e) {
//      fail("Load supported ppm file should not throw exception");
//    }
//  }
//
//  @Test
//  public void testLoadNotSupportedFormat() {
//    try {
//      testImgae.load("img/cake.webp");
//      fail("Load unsupported file should throw an exception");
//    } catch (IOException e) {
//      // pass
//    }
//  }
//
//  @Test
//  public void testLoadMalformedFile() {
//    try {
//      testImgae.load("img/Building.jpg");
//      fail("Load Malformed file should throw exception");
//    } catch (IOException e) {
//      // pass
//    }
//  }
//
//  @Test
//  public void testLoadMalformedPath() {
//    try {
//      // non existing path
//      testImgae.load("picture_lib///car.jpg");
//      fail("Load Malformed Path should throw exception");
//    } catch (IOException e) {
//      // pass
//    }
//  }
//
//  @Test
//  public void testLoadNonExistingFile() {
//    try {
//      // non existing file
//      testImgae.load("img/garden.jpg");
//      fail("Load non existing file should throw exception");
//    } catch (IOException e) {
//      // pass
//    }
//  }
//
//  @Test
//  public void testMonochromatic() {
//    try {
//      testImgae.load("img/monochromatic/black.jpg");
//      testImgae.load("img/monochromatic/black.png");
//      testImgae.load("img/monochromatic/black.ppm");
//      testImgae.load("img/monochromatic/blue.jpg");
//      testImgae.load("img/monochromatic/blue.png");
//      testImgae.load("img/monochromatic/blue.ppm");
//      testImgae.load("img/monochromatic/green.jpg");
//      testImgae.load("img/monochromatic/green.png");
//      testImgae.load("img/monochromatic/green.ppm");
//      testImgae.load("img/monochromatic/red.jpg");
//      testImgae.load("img/monochromatic/red.png");
//      testImgae.load("img/monochromatic/red.ppm");
//      testImgae.load("img/monochromatic/white.jpg");
//      testImgae.load("img/monochromatic/white.png");
//      testImgae.load("img/monochromatic/white.ppm");
//    } catch (IOException e) {
//      fail("Load supported monochromatic file should throw exception");
//    }
//  }
//
//  @Test
//  public void testDichromatic() {
//    try {
//      testImgae.load("img/dichromatic/woBlue.jpg");
//      testImgae.load("img/dichromatic/woBlue.png");
//      testImgae.load("img/dichromatic/woBlue.ppm");
//      testImgae.load("img/dichromatic/woGreen.jpg");
//      testImgae.load("img/dichromatic/woGreen.png");
//      testImgae.load("img/dichromatic/woGreen.ppm");
//      testImgae.load("img/dichromatic/woRed.jpg");
//      testImgae.load("img/dichromatic/woRed.png");
//      testImgae.load("img/dichromatic/woRed.ppm");
//    } catch (IOException e) {
//      fail("Load supported dichromatic file should throw exception");
//    }
//  }
//
//  @Test
//  public void testTrichromatic() {
//    try {
//      testImgae.load("img/trichromatic/all_colors_inter16.jpg");
//      testImgae.load("img/trichromatic/all_colors_inter16.png");
//      testImgae.load("img/trichromatic/all_colors_inter16.ppm");
//    } catch (IOException e) {
//      fail("Load supported trichromatic file should throw exception");
//    }
//  }

  //MyImage constructor
  //given reasonable data
  //given illegal input (negative)
  //reate monochromatic, dichromatic and trichromatic images
  @Test
  public void testMyImage_size_normal() {
    try {
      testImgae = new MyImage(200, 300);
    } catch (IllegalArgumentException e) {
      fail("create an empty image with reasonable size should not throw exception");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMyImage_size_negativeHeight() {
    testImgae = new MyImage(-200, 300);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMyImage_size_negativeWidth() {
    testImgae = new MyImage(200, -300);
  }


//  -------------------Create images that visualize individual R,G,B components of an image.--------
  @Test
  public void testSplitComponent_() {
    testImgae = new MyImage("img/car.jpg");

  }

//  -------------Create images that visualize the value, intensity or luma of an image.-------------
  //different formats
  // gets the value
  // intensity
  // luma
  // of monochromatic, dichromatic and trichromatic images. Of different values for the pixels or
  // same value for every pixel.

//  Flip an image horizontally or vertically.

  //different formats
  //flip horizontally
  //flip vertically
  //combination of the above two.
  //flip for multiple times (more than 2)

//  Brighten or darken an image.
  //different formats
  //brighten
  //darken
  //pixel that's already the darkest
  //pixel that's already the lightest
  //brighten or darken an image for multiple times & in combinations

//
//  Split a single image into 3 images representing each of the three channels.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly split

//  Combine three greyscale image into a single color image whose R,G,B values come from the three images.
  // (at least one of the)input is not legal greyscale image.
  // different formats
  // result color is black/white/red/green/blue/mixed color

//  Blur or sharpen an image as defined above.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly sharpen

//  Convert an image into sepia as defined above.
  // different formats
  // of monochromatic, dichromatic and trichromatic images
  // repeatedly made sepia


// A combination of the previous methods

//  Save an image to an ASCII PPM, JPG or PNG file (see below).
  //src image is create directly from this program
  //src image read from local and directly saved without modification
  //src image read from local and saved with modification
  //src format same as saved format
  //src format different as saved format
  //different color and size

//  Allow a user to interact with your program to use these operations, using text-based scripting (see below).
  // problematic input
  // no comment, single line on command line
  //with comment, single line
  //no comment, multiple lines
  //with comment, multiple lines
  //perform same tests as above for every function provided here
  // Load and run the script commands in the specified file.
        //- ill path
        //- ill command format
        //- no comment, single line on command line
        //- with comment, single line
        //- no comment, multiple lines
        //- with comment, multiple lines

}
