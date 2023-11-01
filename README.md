# **CS5010_Assignment4**
CS5010 Assignment4 Image Manipulation and Enhancement

## **Overview**

This collabration assignment is completed by Jiaming Xu (xu.jiami@northeastern.edu) and Jiaoyang Du (du.jiao@northeastern.edu).

The example images displayed in res/ are all photographs taken by Jiaming Xu, who authorized the use of the images as examples in the assignment.

In this document, an overview of the components of the code will be given, and our design will be explained.

## **Classes and Interfaces**
![](src-diagram.png)

```
src/
├─ util/
│  ├─ ImageUtil.java
├─ view/
│  ├─ ImageView.java
├─ controller/
│  ├─ ImageController.java
├─ model/
│  ├─ image/
│  │  ├─ Image.java
│  │  ├─ MyImage.java
│  ├─ pixel/
│  │  ├─ Pixel.java
│  ├─ Axis.java
│  ├─ Channel.java
├─ service/
│  ├─ ImageService.java
test/
├─ file/
│  ├─ test_command_files...
├─ img/
│  ├─ test_images...
├─ model/
│  ├─ ImageTest.java
│  ├─ MyImageTest.java
│  ├─ RGBPixelTest.java
├─ ImageControllerTest.java
├─ ImageServiceTest.java
├─ MockImageService.java
├─ MockImageView.java
res/
├─ sample_images...

```


We're using MVC pattern in the assignment:  
- ImageView class is about showing views to the user. ImageUtil class and ImageController class are those handling user input. 
- ImageService class is much like the service layer in Spring MVC and it helps to operate on the models when the task is too complicated to be written all in controllers. 
- Finally, we have model classes like RGBPixel and MyImage, representing RGB pixels and 8 bit RGB images. 

The package structure is as follows:


Other interfaces and design purpose is further explained down below.

## Abstract class

### Pixel

#### Purpose
This interface is used to describe pixels. All pixels use a map to store the channels it has and the values in the corresponding channel.

#### Fields

- Map<Channel, Integer> channels
    - Represents every channel and value of each channel in the pixel.

#### Methods

- public boolean containsChannel(Channel channel)
    - Check if the pixel contains the given channel.

- public abstract Pixel linearTransformation(float[][] matrix) throws IllegalArgumentException;
    - Performs linear transformation on the pixel valus. For example, for an RGB matrix, that is calculate 3x3 matrix * 3*1 [r,g,b].

- public abstract Pixel addition(float[] matrix) throws IllegalArgumentException;
    - Adds matrix to pixel. E.g. calculate pixel[r',g',b'] = matrix[x,y,z] + pixel[r,g,b].
- public abstract Pixel addition(Pixel pixel) throws IllegalArgumentException;
    - Adds two pixels. E.g. calculate [r,g,b] + [r',g',b'] for two RGB pixels.

- abstract Pixel getChannelComponent(Channel channel);
    - Get certain channel component of the pixel, which means the result pixel has same value as the original one on the given channel, but with 0 on the other channels.

- public abstract Pixel max();
    - Calculate the max value among all channels of the pixel and get a pixel with all channels this value.

- public abstract Pixel avg();
    - Calculate the average value among all channels of the pixel and get a pixel with all channels this value.
- public boolean isGreyscale()
    - Check if this pixel is greyscale by checking if each channel has the same value. The default implementation traverse all values in channels to see if they are all same.

- public String toString()
    - Returns a string representation of the pixel by printing out the channels and corresponding values.


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

#### Fields
- Pixel[][] pixels
    - 2D Pixel array that represents every pixel in the image
- int height
    - int that represents the height of the image
- int width
    - int that represents the width of the image

#### Methods
- public abstract void save(String path) throws IllegalArgumentException;
    - Save image to local file.
- public int getHeight()
    - Get height of the image.
- public int getWidth()
    - Get width of the image.

- Pixel getPixel(int x, int y) throws IllegalArgumentException
    - Package-private method to get pixel at certain position.

- void setPixel(int x, int y, Pixel pixel) throws IllegalArgumentException
    - Package-private method to set pixel at certain position.

- public boolean isGreyscale()
    - Check if an image is greyscale by checking if all of its pixels are greyscale. 

- public Image matrixMultiplication(float[][] matrix) throws IllegalArgumentException
    - Perform matrix multiplication on each of the pixel in the image and return image with the result pixels. This fuction achived by making use of mapElement(pixel -> pixel.linearTransformation(matrix)). Please see down below for introduction to function mapElement.

- public abstract Image channelSplit(Channel channel) throws IllegalArgumentException;
    - Split image of the given channel (resulted in one colored image).

- public abstract Image filtering(float[][] kernel);
    - Perform filtering on the image

- public abstract Image mapElement(Function<Pixel, Pixel> function);
    - Gets a new image that is resulted from performing the same function to every pixel in the original image in the corresponding position through row and column traverse. This fuction helps with code reuse when many image operations are traversing all pixels in the image and doing the same thing to each of the pixel. 

- public abstract Image projectCoordinate(int[][] projectMatrix);
    - Project coordinate of the original component element and actually move the pixels. For example,

        1 0 w

        0 -1 h

    
        projects (x,y) to (x+w,-y+h), and project accordingly pixels in the old image to the new one.

- int[][][] projectCoordinateCal(int[][] projectMatrix) throws IllegalArgumentException
    - Project coordinate of the original component element but only calculate the coordinate projection relations. projectResult[y][x] = [newY,newX]. Though abstract class can not really make new instances, this function has shared code about coordinate projection and can be used by concrete-class-implemented projectCoordinate to help return projected image.

- public abstract Image addition(Image that);
    - Perform addition with another image.

- public Image imgArrayAddition(float[] matrix)
    - Perform array addition an image with given matrix. This is acheived by performing array addition on every pixel, by utilizing mapElement(pixel -> pixel.addition(matrix))

- public abstract Channel[] getChannels()
    - Get channels of pixels in the image.

- public String toString()
    - Returns a string representation of the object.

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

#### Fields
- Besides the inhereted Map<Channel, Integer> channels, it also has a constant private final int bitDepth = 8.

#### Methods
- public RGBPixel(int red, int green, int blue)
    - Constructor with red, green and blue values. It will automatically clamp the values, i.e., set value to zero if it's negative, and set value to 2^bitDepth-1 if it's larger than that. It also uses EnumMap to store this channel-value relation ship which makes operation quicker.
- private RGBPixel(Map<Channel, Integer> channels)
    - Private constructor that takes given channels map.
- public boolean isGreyscale()
    - Check if this pixel is greyscale by checking if the rgb channel values are all same.
- public Pixel getChannelComponent(Channel channel) throws IllegalArgumentException
    - Get certain channel component of the pixel by traversing all channels and put corresponding value to corresponding channel in the result pixel while keeping other channels zero.
- public RGBPixel linearTransformation(float[][] matrix) throws IllegalArgumentException
    - Calculate 3x3 matrix * [r,g,b] following matrix operation definition.
- public RGBPixel addition(float[] matrix) throws IllegalArgumentException
    - Calculate pixel[r',g',b'] = matrix[x,y,z] + pixel[r,g,b] following matrix operation definition.

- public RGBPixel addition(Pixel pixel) throws IllegalArgumentException
    - Calculate pixel[r,g,b] + pixel[r',g',b']

- public RGBPixel max()
    -  Calculate the max value among all channels of the pixel and get a pixel with all channels this value.

- public RGBPixel avg()
    -  Calculate the average value among all channels of the pixel and get a pixel with all channels this value.

- public int getRed()
    - Get the value of the red channel by getting from the EnumMap.

- public int getBlue()
    - Get the value of the red channel by getting from the EnumMap.

- public int getGreen()
    - Get the value of the red channel by getting from the EnumMap.

- public boolean equals(Object o)
    - Compare if two objects are equal. If both are RGB pixel, compare the channels.

- public int hashCode()
    - Get the hashcode of the object.


### MyImage

#### Purpose
This class represents 8 bit depth RGB images that is consisted of RGBPixels. It extends the Image abstract class.

#### Fields
- RGBPixel[][] pixels
    - 2D RGBPixel array that represents every pixel in the image
- int height
    - int that represents the height of the image
- int width
    - int that represents the width of the image

#### Methods
-  public MyImage(int height, int width)
    - Construct all black image with given height and width.
-  private MyImage(Pixel[][] pixels)
    - Private constructor that takes 2d array of pixels and checks if the type are RGBPixels and construct with given pixels array.

-  public MyImage(String path) throws IllegalArgumentException
    - Construct an image with given local path. It calls different tool functions to read from local file according to the extension.

-  private void loadPPM(String path) throws IOException, IllegalArgumentException
    - Private helper functions that loads from local PPM file.

-  private void loadJPGPNG(String path) throws IOException, IllegalArgumentException
    - Private helper functions that loads from local JPG/PNG file.

-  public void save(String path) throws IllegalArgumentException
    - Save image to local file. It calls different tool functions to save to local file according to the extension.

-  private void savePPM(String path) throws IOException, IllegalArgumentException
    - Private helper functions that saves MyImage to local PPM file. 

-  private void saveJPGPNG(String path, String format)
    -Private helper functions that saves MyImage to local JPG/PNG file. 

-  void setPixel(int x, int y, Pixel pixel)
    - Set pixel at position x, y in this image to the given pixel.

-   RGBPixel getPixel(int x, int y)
    - Gets the pixel at x,y

-  public MyImage filtering(float[][] kernel) throws IllegalArgumentException
    - Perform filtering an image with given matrix. By traversing and putting the center of the kernel to each position on the image and calculates the area it covers, it adds the values when corresponding pixel and kernel element multiply to make the final result of the pixel where the kernel now lies.

-  public MyImage channelSplit(Channel channel) throws IllegalArgumentException
    - Split channels of the given image by making use of the pixel's getChannelComponent function.

-  public MyImage imgArrayAddition(float[] matrix) throws IllegalArgumentException
    - Perform array addition an image with given matrix.

-  public MyImage addition(Image that) throws IllegalArgumentException
    - Perform addition with another image by adding every pair of pixels.

-  public MyImage projectCoordinate(int[][] projectMatrix)
    - Project coordinate of the original component element. For example, 1 0 w 0 -1 h projects (x,y) to (x+w,-y+h), and project accordingly pixels in the old image to the new one. It made use of the parent's projectCoordinateCal tool function and projects the pixels to the result new image.

-  public MyImage mapElement(Function<Pixel, Pixel> function)
    - Map all pixels in the image with given pixel function with traversal.

-  public Channel[] getChannels()
    - Get channels of pixels in the image. For this case, Channel.RED, Channel.GREEN and Channel.BLUE

-  public boolean equals(Object o)
    - Check if two objects are identical.

-  public int hashCode()
    - Returns a hash code value for the object. It takes into consideration height, width and the pixel array.


### ImageService

#### Purpose
This class represents image operations. It implements ImageServiceInterface interface. The key design is similar to the categorization mentioned in the part for Image interface. Here we differentiate methods by different mechanisms or by different arguments passed in to same mechanism. For example, sepia and luma all made use of matrix multiplication, but the matrices used are different.

#### Fields
None

#### Methods
- public Image splitComponent(Image image, Channel channel) throws IllegalArgumentException
    - Get one certain channel of the image (result in one colored image) by calling image.channelSplit(channel).

- public Image blur(Image image) throws IllegalArgumentException
    - Blur the image by using the blur matrix in image.filtering(blur)
        ```
        {{0.0625f, 0.125f, 0.0625f},
        {0.125f, 0.25f, 0.125f},
        {0.0625f, 0.125f, 0.0625f}}
        ```
- public Image getValue(Image image) throws IllegalArgumentException 
    - Get value of the image by calling image.mapElement(Pixel::max).

- public Image getIntensity(Image image) throws IllegalArgumentException
    - Get intensity of the image by calling image.mapElement(Pixel::avg).
- public Image getLuma(Image image) throws IllegalArgumentException
    - Get luma of an image by using the luma matrix in image.filtering(blur).
    ```
    {
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f},
        {0.2126f, 0.7152f, 0.0722f}
    }
    ```
- public Image flip(Image image, Axis axis) throws IllegalArgumentException
    - Flip an image by calling image.projectCoordinate(matrix). If it's axis == Axis.Y (horitontal flip), then the matrix is
    ```
    {
        {-1, 0, image.getWidth() - 1},
        {0, 1, 0}
    };
    ```
    else it's
    ```
    {
        {1, 0, 0},
        {0, -1, image.getHeight() - 1}
    };
    ```
- public Image brighten(Image image, float delta) throws IllegalArgumentException
    - Brighten/darken an image with given delta. If delta is negative, then it's darken; if delta is positive, it's brighten. It's achieved by calling  
    return image.imgArrayAddition(matrix) with matrix = {delta, delta, delta}.

- public Image[] splitChannel(Image image) throws IllegalArgumentException
    - Split image channels and result in $channelCount greyscale images by calling channelSplit first then multiply by a matrix whose column index corresponds to each channel (like column 0 for red in rgb) is one (other than that is 0).

- public Image combineChannels(Channel[] channels, Image[] images) throws IllegalArgumentException
    - Combine greyscale images each representing one channel to one multicolor image by first splitting the greyscale images and get single channel images, than add them all using Arrays.stream(splits).reduce(Image::addition) .
- public Image sharpen(Image image) throws IllegalArgumentException
    - Sharpen an image with image.filtering(sharpen), where sharpen matrix is
    ```
    {
        {-0.125f, -0.125f, -0.125f, -0.125f, -0.125f},
        {-0.125f, 0.25f, 0.25f, 0.25f, -0.125f},
        {-0.125f, 0.25f, 1f, 0.25f, -0.125f},
        {-0.125f, 0.25f, 0.25f, 0.25f, -0.125f},
        {-0.125f, -0.125f, -0.125f, -0.125f, -0.125f},
    };
    ```
    .
- public Image getSepia(Image image) throws IllegalArgumentException
    -

### ImageView

#### Purpose
This class is responsible delivering views and communicating with the user by displaying messages. 

#### Methods
- `public ImageView(Reader reader, Appendable output) ` 
    - Initializes an image viewer which reads information from the specified input and displays messages to a designated output.
- `public String getUserCommand()`  
    - Fetches a command entered by the user from the input source.
- `public void displayMessage(String message)`  
    - Shows the provided message on the screen.
- `public int getModeChoice()`  
    - Retrieves the mode selected by the user, which should be a number.
- `public String getFilePath()`  
    - Prompts the user for a file path and reads it from the input source.

### ImageUtil

#### Purpose
This class provide the main entry point for the program and initializes the other components like controller ans service classes.

#### Methods
- `public static void main(String[] args) throws IOException ` 
    - This is the starting point for the image application. It sets up the necessary components for the program: the service for images, the view to interact with the user, and the controller that links them. It asks the user to choose how they want to input commands: directly through the console or from a file.

### ImageController

#### Purpose
This class deals with user's input commands or on the command-file and inteprets the order to execute corresponding operations on the specific image.

#### Methods
- `public ImageController(ImageService imageService, ImageView imageView) ` 
    - Sets up a controller that connects the image service (which handles image operations) with the view (which interacts with the user).
- `public void start()`  
    - Begins an interaction loop with the user, where the user is continuously prompted to enter commands. These commands are then executed to perform operations on images. If the command starts with "#", it's seen as a comment and ignored. The interaction continues until the user enters the "exit" command.
- `public void startFromFile(String filePath) throws IOException`  
    - Starts processing commands from a provided file. Each line in the file is treated as a command which gets executed one after the other. Similar to the direct interaction, commands starting with "#" are considered comments and thus ignored. If the file contains the "exit" command, the processing stops.
- `public void executeCommand(String command)`  
    - Processes and executes commands related to image operations. It first parses the provided command, then performs the specified image operation based on the command's name. The results or status messages are displayed to the user via the imageView. In the event of an invalid command or if a referenced image is not found, error messages are displayed.



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
