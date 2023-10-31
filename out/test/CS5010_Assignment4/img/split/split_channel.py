def split_red(input_file, output_file):
    with open(input_file, 'r') as f:
        lines = f.readlines()

    header = lines[0:3]  # Assuming the header is always 3 lines

    data = lines[3:]
    new_data = []

    for line in data:
        pixels = line.split()
        new_pixels = []

        for i in range(0, len(pixels), 3):
            pixels[i+1] = '0'
            pixels[i+2] = '0'
            new_pixels.extend(pixels[i:i+3])

        new_data.append(' '.join(new_pixels))
        new_data.append(' ')

    with open(output_file, 'w') as f:
        f.writelines(header + new_data)

def split_green(input_file, output_file):
    with open(input_file, 'r') as f:
        lines = f.readlines()

    header = lines[0:3]  # Assuming the header is always 3 lines

    data = lines[3:]
    new_data = []

    for line in data:
        pixels = line.split()
        new_pixels = []

        for i in range(0, len(pixels), 3):
            pixels[i] = '0'
            pixels[i+2] = '0'
            new_pixels.extend(pixels[i:i+3])

        new_data.append(' '.join(new_pixels))
        new_data.append(' ')

    with open(output_file, 'w') as f:
        f.writelines(header + new_data)

def split_blue(input_file, output_file):
    with open(input_file, 'r') as f:
        lines = f.readlines()

    header = lines[0:3]  # Assuming the header is always 3 lines

    data = lines[3:]
    new_data = []

    for line in data:
        pixels = line.split()
        new_pixels = []

        for i in range(0, len(pixels), 3):
            pixels[i] = '0'
            pixels[i+1] = '0'
            new_pixels.extend(pixels[i:i+3])

        new_data.append(' '.join(new_pixels))
        new_data.append(' ')

    with open(output_file, 'w') as f:
        f.writelines(header + new_data)

input_filename = "./rose.ppm"
onlyRed_filename = "./rose_onlyRed.ppm"
onlyBlue_filename = "./rose_onlyBlue.ppm"
onlyGreen_filename = "./rose_onlyGreen.ppm"

split_red(input_filename, onlyRed_filename)
split_green(input_filename, onlyGreen_filename)
split_blue(input_filename, onlyBlue_filename)
