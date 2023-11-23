import pandas as pd
import json

def json_to_csv(json_file, csv_file):
    # Read JSON file into a pandas DataFrame
    with open(json_file, 'r') as f:
        data = json.load(f)
    df = pd.DataFrame.from_records(data)

    # Write DataFrame to CSV
    df.to_csv(csv_file, index_label='id')

def main():
    # Replace 'input.json' with the path to your JSON file
    json_file_path = 'IKEA US products dataset/ikea_sample_file.json'

    # Replace 'output.csv' with the desired output CSV file path
    csv_file_path = 'converted.csv'

    # Convert JSON to CSV
    json_to_csv(json_file_path, csv_file_path)

if __name__ == "__main__":
    main()

