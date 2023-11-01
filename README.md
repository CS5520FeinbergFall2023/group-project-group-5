# **CS5010_Assignment4**
CS5010 Assignment4 Image Manipulation and Enhancement

## **Overview**

This collabration assignment is completed by Jiaming Xu (xu.jiami@northeastern.edu) and Jiaoyang Du (du.jiao@northeastern.edu).

The example images displayed in res/ are all photographs taken by Jiaming Xu, who authorized the use of the images as examples in the assignment.

In this document, an overview of the components of the code will be given, and our design will be explained.

## **Classes and Interfaces**
Please see src-diagram.png for the class diagram.

We're using MVC pattern in the assignment:  
- ImageView class is about showing views to the user. ImageUtil class and ImageController class are those handling user input. 
- ImageService class is much like the service layer in Spring MVC and it helps to operate on the models when the task is too complicated to be written all in controllers. 
- Finally, we have model classes like RGBPixel and MyImage, representing RGB pixels and 8 bit RGB images. 

Other interfaces and design purpose is further explained down below.

## Abstract class

### Pixel

#### Purpose
This interface is used to describe pixels. All pixels use a map to store the channels it has and the values in the corresponding channel.


### Image

#### Purpose
This interface describes images composed of pixels. It contains commom methods across all kind of images, like multiplying matrix to every pixel, adding value to pixels, etc.

The key design here is to categorize image operations according to the mechanisms of the operations.  While image class presents methods to realize the calculations described in the category column, the operations in the service class make use of them and bring more operations.

| Operation    | Category |
| -------- | ------- |
| get single channel image  | matrix multiplication (on pixel, 3x3 matrix with 3x1 rgb)   |
| value | matrix multiplication     |
| luma    | matrix multiplication    |
| intensity    | matrix multiplication    |
| greyscale    | matrix multiplication    |
| flip    | affine transformation (project coordinates according to matrix and move pixel at (x,y) to the projected position (x',y'))    |
| brighten    | pixel addition (adding values to channels)    |
| darken    | pixel addition    |
| combine   | pixel addition    |
| blur   | filtering (on the whole image)    |
| sharpen   | filtering    |
| sepia   | matrix multiplication    |

## Interface

## Enums

### Axis

#### Purpose
This enum is used to represent axis. Currently we only deal with 2D images, so we only have two axis, X and Y.

### Channel

#### Purpose
This enum is used to represent channels. Currently we only deal with RGB images, so we only have three channels, RED, GREEN, and BLUE.

## Classes

### RGBPixel 

#### Purpose
This class represents RGB pixels, that is, pixels with and only with RGB channels. It extends the Pixel abstract class.

### MyImage

#### Purpose
This class represents 8 bit depth RGB images that is consisted of RGBPixels. It extends the Image abstract class.

### ImageService

#### Purpose
This class represents image operations. It implements ImageServiceInterface interface. The key design is similar to the categorization mentioned in the part for Image interface. Here we differentiate methods by different mechanisms or by different arguments passed in to same mechanism. For example, sepia and luma all made use of matrix multiplication, but the matrices used are different.

### ImageView

#### Purpose
This class is responsible delivering views and communicating with the user by displaying messages. 

### ImageUtil

#### Purpose
This class provide the main entry point for the program and initializes the other components like controller ans service classes.

### ImageController

#### Purpose
This class deals with user's input commands or on the command-file and inteprets the order to execute corresponding operations on the specific image.


## **Usage**

After running the program, the user can either input image operation commands directly on the console like

```bash
load image-path image-name
#Load an image from the specified path and refer it to henceforth in the program by the given image name.

save image-path image-name
# Save the image with the given name to the specified path which should include the name of the file.

red-component image-name dest-image-name 
# Create an image with the red-component of the image with the given name, and refer to it henceforth in the program by the given destination name. Similar commands for green, blue, value, luma, intensity components should be supported. Note that the images for value, luma and intensity will be greyscale images.

horizontal-flip image-name dest-image-name
# Flip an image horizontally to create a new image, referred to henceforth by the given destination name.

vertical-flip image-name dest-image-name
# Flip an image vertically to create a new image, referred to henceforth by the given destination name.

brighten increment image-name dest-image-name
# brighten the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).

rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue
# split the given image into three images containing its red, green and blue components respectively. These would be the same images that would be individually produced with the red-component, green-component and blue-component commands.

rgb-combine image-name red-image green-image blue-image
# Combine the three greyscale images into a single image that gets its red, green and blue components from the three images respectively.

blur image-name dest-image-name
# blur the given image and store the result in another image with the given name.

sharpen image-name dest-image-name
# sharpen the given image and store the result in another image with the given name.

sepia image-name dest-image-name
# produce a sepia-toned version of the given image and store the result in another image with the given name.

run script-file
# Load and run the script commands in the specified file. The file has similar syntax as the above command line commands and supports single-line commands.
```

an example of the the script file can be

```
#load koala.ppm and call it 'koala'
load images/koala.ppm koala

#brighten koala by adding 10  
brighten 10 koala koala-brighter 

#flip koala vertically
vertical-flip koala koala-vertical

#flip the vertically flipped koala horizontally
horizontal-flip koala-vertical koala-vertical-horizontal

#create a greyscale using only the value component, as an image koala-greyscale
value-component koala koala-greyscale

#save koala-brighter
save images/koala-brighter.ppm koala-brighter

#save koala-greyscale
save images/koala-gs.ppm koala-greyscale

#overwrite koala with another file
load images/upper.ppm koala

#give the koala a red tint
rgb-split koala koala-red koala-green koala-blue

#brighten just the red image
brighten 50 koala-red koala-red

#combine them back, but by using the brightened red we get a red tint
rgb-combine koala-red-tint koala-red koala-green koala-blue
save images/koala-red-tint.ppm koala-red-tint
```
