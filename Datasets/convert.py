import pandas as pd
import json

def xlsx_to_json(xlsx_file):
    # Read Excel file into a pandas DataFrame
    df = pd.read_excel(xlsx_file, header=0)

    # Convert DataFrame to JSON with the specified format
    json_data = {}
    for index, row in df.iterrows():
        row_data = {col: row[col] for col in df.columns}
        json_data[str(index)] = row_data

    return json_data

def convert(excel_file_path):

    # Convert XLSX to JSON
    result_json = xlsx_to_json(excel_file_path)

    output_file_path = 'furniture.json'
    with open(output_file_path, 'w') as f:
        json.dump(result_json, f, indent=2)

if __name__ == "__main__":
    convert("furniture.xlsx")
