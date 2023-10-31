import math
def normal_round(n):
    if n - math.floor(n) < 0.5:
        return math.floor(n)
    return math.ceil(n)

def apply_color_tone(image):
    result = []
    for row in image:
        new_row = []
        for pixel in row:
            r = min(normal_round(pixel[0] * 0.393 + pixel[1] * 0.769 + pixel[2] * 0.189), 255)
            g = min(normal_round(pixel[0] * 0.349 + pixel[1] * 0.686 + pixel[2] * 0.168), 255)
            b = min(normal_round(pixel[0] * 0.272 + pixel[1] * 0.534 + pixel[2] * 0.131), 255)
            new_row.append((r, g, b))
        result.append(new_row)
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

input_filename = 'test/img/city_small_sepia.ppm'
output_filename = 'test/img/city_small_sepia_sepia.ppm'

image, max_color = load_ppm_file(input_filename)
toned_image = apply_color_tone(image)
save_ppm_file(output_filename, toned_image, max_color)
