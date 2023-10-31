import math
def normal_round(n):
    if n - math.floor(n) < 0.5:
        return math.floor(n)
    return math.ceil(n)

def apply_sharpen(image, filter):
    result = []
    filter_size = len(filter)
    offset = filter_size // 2
    
    for i in range(len(image)):
        row = []
        for j in range(len(image[i])):
            pixel_sum = [0, 0, 0]
            for x in range(filter_size):
                for y in range(filter_size):
                    xi = i - offset + x
                    yj = j - offset + y
                    if xi >= 0 and xi < len(image) and yj >= 0 and yj < len(image[i]):
                        pixel_sum[0] += filter[x][y] * image[xi][yj][0]
                        pixel_sum[1] += filter[x][y] * image[xi][yj][1]
                        pixel_sum[2] += filter[x][y] * image[xi][yj][2]
            row.append(tuple(max(0, min(255, normal_round(val))) for val in pixel_sum))
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
    [-1/8, -1/8, -1/8, -1/8, -1/8],
    [-1/8, 1/4, 1/4, 1/4, -1/8],
    [-1/8, 1/4, 1, 1/4, -1/8],
    [-1/8, 1/4, 1/4, 1/4, -1/8],
    [-1/8, -1/8, -1/8, -1/8, -1/8]
]

input_filename = 'test/img/cupcake_blur.ppm'
output_filename = 'test/img/cupcake_blurThenSharp.ppm'

image, max_color = load_ppm_file(input_filename)
sharpened_image = apply_sharpen(image, filter)
save_ppm_file(output_filename, sharpened_image, max_color)

