import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Long> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String fileName : inputFiles) {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File not found: " + fileName);
                continue;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (isInteger(line)) {
                    integers.add(Long.parseLong(line));
                } else if (isFloat(line)) {
                    floats.add(Double.parseDouble(line));
                } else {
                    strings.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
            e.printStackTrace();
        }


    }

    private static boolean isInteger(String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFloat(String line) {
        try {


            Double.parseDouble(line);
            return line.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static List<String> toStringList(List<?> values) {
        List<String> result = new ArrayList<>();
        for (Object val : values) {
            result.add(String.valueOf(val));
        }
        return result;
    }
    private static void writeToFile(String dir, String name, List<String> lines, boolean append) throws IOException {
        File folder = new File(dir);
        if (!folder.exists()) folder.mkdirs();
        File file = new File(folder, name);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

}