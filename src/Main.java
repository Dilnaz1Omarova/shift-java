import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Long> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String fileName : inputFiles){
            File file = new File(fileName);
            if (!file.exists()){
                System.out.println("File not found: " + fileName);
                continue;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                line=line.trim();
                if (line.isEmpty()) continue;
                if (isInteger(line)){
                    integers.add(Long.parseLong(line));
                } else if (isFloat(line)) {
                    floats.add(Double.parseDouble(line));
                }
                else{
                    strings.add(line);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error reading file");
            e.printStackTrace();
        }

    }
}