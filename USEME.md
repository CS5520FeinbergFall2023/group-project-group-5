# **CS5010_Assignment_Image-Manipulation-and-Enhancement-Usage**

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

an example of the script file can be:

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

an example of the script file can be:

```
#load koala.ppm and call it 'koala'
load images/koala.ppm koala

#blur the koala and split in half of the image width.  
blur koala koala-blurred split 0.5 

#sharpen the koala and split in 80% of the image width.
sharpen koala koala-sharpen split 0.8

#color correct the koala image.
color-correct koala koala-color-correct

#compress the koala image which is after the color correct operation, the percentage is 40%.
compress 0.4 koala-color-correct koala-color-corrrect-compress-0.4

#do the greyscale operation and split in 70% of the image width.
greyscale koala koala-greyscale split 0.7;

#sepia the koala and split in 30% of the image width.
sepia koala koala-sepia split 0.3

#acquire the histogram of the koala.
histogram koala koala-histogram 

#do the level-adjust operation, b equals to 20, m equals to 100, w equals to 255 and split in 65.5% of the image width.
levels-adjust 20 100 255 koala koala-levels-adjust split 0.655

save images/koala-greyscale.ppm koala-greyscale
```

Common conditions:

load command should be executed before all image operation commands (such as blur, sepia, brighten...).
 

