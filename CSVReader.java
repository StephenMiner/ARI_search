import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVReader {
    public static void readCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // for (String value : values) {
                //     // System.out.print(value + " | ");
                // }
                // System.out.println();
            }
        } catch (IOException e) {
            // System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public static boolean hasColumn(String filePath, String columnName, int columnIndex) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // read the header row
            String[] headers = line.split(",");
            if (columnIndex >= headers.length) {
                return false;
            }
            return headers[columnIndex].trim().equals(columnName);
        } catch (IOException e) {
            // System.err.println("Error reading CSV file: " + e.getMessage());
            return false;
        }
    }

  

    public static void compareAnagram(String filePath, String columnName, int columnIndex, String comparisonValue, BufferedWriter bw,int columnurl,int outputcolumnindex,String urlvalue) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // read the header row
            
            bw.write(filePath+ "\n");
            String[] headers = line.split(",");
            if (columnIndex >= headers.length) {
                return;
            }
            
            if (!headers[columnIndex].trim().equals(columnName)) {
                return;
            }
            // System.out.println("here");
            isAnagram anagram = new isAnagram();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length <= columnIndex) {
                    continue;
                }
                
                
                String column = values[columnIndex].trim();
                // System.out.println(column);

                String result = anagram.isAnagram(column, comparisonValue);
                // System.out.println(urlvalue);;
                if (urlvalue.contains("None")){
                // System.out.println(result.contains(urlvalue));
                if (anagram.verdict==true && result.contains("true")) {
                // System.out.println(result);
                if(outputcolumnindex!=0) bw.write(values[outputcolumnindex].trim() + "," + result + "\n");
                else bw.write("0" + "," + result + "\n");    
                    }
                }else{
                    // System.out.println(result.contains("true"));
                    // System.out.println(result);
                    if (values[columnurl].trim().contains(urlvalue) && result.contains("true")) {
                    // System.out.println(result);
                    if(outputcolumnindex!=0) bw.write(values[outputcolumnindex].trim() + "," + result + "\n");
                    else bw.write("0" + "," + result + "\n");    
                    }

                }
            }
        }
    }

    public static void processFiles(String directoryPath, String columnName, int columnIndex, String comparisonValue,String column,int index,String value,int op) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path");
            return;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("No files found in directory");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("result/intersection.csv"))) {
            bw.write("Index,Result\n"); // write header
            for (File file : files) {
                if (file.getName().endsWith(".CSV")) {
                    System.out.println("Processing file: " + file.getName());
                    readCSV(file.getAbsolutePath());
                    if (hasColumn(file.getAbsolutePath(), columnName, columnIndex)) {
                        // System.out.println("Column found!");
                        compareAnagram(file.getAbsolutePath(), column, index, value, bw,columnIndex,op,comparisonValue);
                    } else {
                        // System.out.println("Column not found!");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading or writing CSV file: " + e.getMessage());
        }
    }

  
}