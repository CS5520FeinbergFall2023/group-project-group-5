import math
def normal_round(n):
    if n - math.floor(n) < 0.5:
        return math.floor(n)
    return math.ceil(n)

def apply_gaussian_blur(image, filter):
    result = []
    for i in range(len(image)):
        row = []
        for j in range(len(image[i])):
            pixel_sum = [0, 0, 0]
            for x in range(len(filter)):
                for y in range(len(filter[x])):
                    xi = i - 1 + x
                    yj = j - 1 + y
                    if xi >= 0 and xi < len(image) and yj >= 0 and yj < len(image[i]):
                        pixel_sum[0] += filter[x][y] * image[xi][yj][0]
                        pixel_sum[1] += filter[x][y] * image[xi][yj][1]
                        pixel_sum[2] += filter[x][y] * image[xi][yj][2]
            row.append(tuple(min((normal_round(val), 255)) for val in pixel_sum))
        result.append(row)
    return result

def load_ppm_file(filename):
    with open(filename, 'r') as file:
        data = file.read().split()
        width, height = int(data[1]), int(data[2])
        max_color = int(data[3])
        pixels = [tuple(map(int, data[i:i+3])) for i in range(4, len(data), 3)]
        image = [[pixels[i * width + j] for j in range(width)] for i in range(height)]
        return image, max_color

def save_ppm_file(filename, image, max_color):
    with open(filename, 'w') as file:
        file.write(f'P3\n{len(image[0])} {len(image)}\n{max_color}\n')
        for row in image:
            for pixel in row:
                file.write(f'{pixel[0]} {pixel[1]} {pixel[2]} ')
            file.write('\n')

filter = [
    [1/16, 1/8, 1/16],
    [1/8, 1/4, 1/8],
    [1/16, 1/8, 1/16]
]

input_filename = 'test/img/cupcake_blur.ppm'
output_filename = 'test/img/cupcake_blurTwice.ppm'

image, max_color = load_ppm_file(input_filename)
blurred_image = apply_gaussian_blur(image, filter)
save_ppm_file(output_filename, blurred_image, max_color)
