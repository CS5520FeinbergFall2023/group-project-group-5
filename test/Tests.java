import org.junit.Test;

public class Tests {

//  -------------------Load an image from an ASCII PPM, JPG or PNG file.----------------------------
  // remember to test both JPEG & JPG extension
  // test malformed path
  // well-formed path, but the img does not exist
  // img exist but not PPM,JPG or PNG extension and is not PPM,JPG or PNG
  // is PPM,JPG or PNG extension but actually the content is not PPM, JPG, PNG formatted
  // is actually PPM,JPG or PNG format but with different/no extension.
  // PPM, JPG & PNG, monochromatic(black,white,red,green,blue), dichromatic or trichromatic. With
  // each value 0,(a number in between 0-255),255. For trichromatic test a picture with all 256
  // colors.

//  -------------------Create images that visualize individual R,G,B components of an image.--------
  //create PPM, JPG or PNG given reasonable data
  // png with 8, 24 & 32 bit
  //create PPM, JPG or PNG given illegal input (negative or too large)
  //create small img and large img
  //create monochromatic, dichromatic and trichromatic images

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
