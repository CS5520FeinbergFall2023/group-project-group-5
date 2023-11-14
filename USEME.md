# **CS5010_Assignment_Image-Manipulation-and-Enhancement-Usage**

The example images displayed in res/ are all photographs taken by Jiaming Xu, who authorized the use of the images as examples in the assignment.

Common conditions:
- load command should be executed before all image operation commands (such as blur, sepia, brighten...).

## **Usage-Assignment-4**

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

Examples of the script file are under the test/file folder. After the user choosing to enter the file path in console, they can enter the path of the file, the program will correctly parse the commands in the file and execute the operations. 
One of the script file(commandWithMultipleComments.txt) is like:

```
load res/city_small.png city_small
# get the city_small intensity
intensity-component city_small city_small_intensity
# get the city_small value
value-component city_small city_small_value
# get the city_small luma
luma-component city_small city_small_luma
exit
```

## **Usage-Assignment-5**

Add five new commands for the program to execute, the user can input these commands directly on the console like

```bash
compress percentage image-name dest-image-name
# Create an image named dest-image-name, which is obtained by compressing the image named image-name according to the percentage ratio.

greyscale image-name dest-image-name
# Create an image that represents the greyscale of the given image, and refer to it henceforth in the program by the given destination name.

histogram image-name dest-image-name
# Create an image that represents the histogram of the given image, and refer to it henceforth in the program by the given destination name.

color-correct image-name dest-image-name 
# Create an color-correct image by aligning the meaningful peaks of its histogram of the given image, and refer to it henceforth in the program by the given destination name. 

levels-adjust b m w image-name dest-image-name
# Create an image that adjust levels of the given image, and refer to it henceforth in the program by the given destination name. 
```

Also, add a split view of operations in blur, sharpen, sepia, greyscale, color correction and levels adjustment. At the same time, the basic commands for the above operations must also be correctly parsed by the controller. The commands' formats are like:

```bash
blur image-name dest-image split p
# Execute the blur operation where 'p' is a percentage of the width.

sharpen image-name dest-image split p
# Execute the blur operation where 'p' is a percentage of the width.

sepia image-name dest-image split p
# Execute the sepia operation where 'p' is a percentage of the width.

greyscale image-name dest-image split p
# Execute the greyscale operation where 'p' is a percentage of the width.

color-correct image-name dest-image-name split p
# Execute the color-correct operation where 'p' is a percentage of the width.

levels-adjust b m w image-name dest-image-name split p
# Execute the levels-adjust operation where 'p' is a percentage of the width.
```

Examples of the script file are scriptJar.txt and scriptTerminal.txt, which both located under the res/folder. The only difference between these two files is that the path to load the required image is different. scriptJar.txt needs to use image's path from content root, and the scriptTerminal.txt only needs to use the image's name. 
User can directly run the jar file and the ImageUtil.class(main class) to let transfer the scriptJar.txt file to program. Or, enter the "java -jar CS5010_Assignment4.jar -file scriptTerminal.txt" command in console to transfer scriptTerminal.txt file to program. 
One of the script file(scriptJar.txt) is like:

```
load res\cupcake.png cupcake
# then I want to blur the image.
blur cupcake cupcake_blurOnce
# want to see the difference between original picture and blurred picture.
blur cupcake cupcake-blur-50% split 0.5
#test sepia operation.
sepia cupcake cupcake-sepia-50% split 0.5
#test sharpen operation.
sharpen cupcake cupcake_sharpenOnce
sharpen cupcake cupcake-sharpen-50% split 0.5
#test greyscale operation.
greyscale cupcake cupcake-greyscale
greyscale cupcake cupcake-greyscale-50% split 0.5

#load another picture.
load res\city_small.png city_small
#get value operation.
value-component city_small city_small_value
#get intensity operation.
intensity-component city_small city_small_intensity
#get luma operation.
luma-component city_small city_small_luma
#get sepia operation.
sepia city_small city_small_sepia
#get red component operation.
red-component city_small city_small_red_channel_greyscale
#get green component operation.
green-component city_small city_small_green_channel_greyscale
#get blue component operation.
blue-component city_small city_small_blue_channel_greyscale
#brighten operation (increase).
brighten 40 city_small city_small+40
#brighten operation(decrease).
brighten -40 city_small city_small-40
....
```
 

