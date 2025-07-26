import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<String> inputFiles = new ArrayList<>();
        String outputDir = ".";
        String prefix = "";
        boolean append = false;
        boolean fullStats = false;
        boolean shortStats = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputDir = args[++i];
                    break;
                case "-p":
                    prefix = args[++i];
                    break;
                case "-a":
                    append = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                case "-s":
                    shortStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }


        List<Long> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        File file = null;
        for (String fileName : inputFiles) {
            file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File not found: " + fileName);
                continue;
            }
        }

        try {
            if (!integers.isEmpty())
                writeToFile(outputDir, prefix + "integers.txt", toStringList(integers), append);
            if (!floats.isEmpty())
                writeToFile(outputDir, prefix + "floats.txt", toStringList(floats), append);
            if (!strings.isEmpty())
                writeToFile(outputDir, prefix + "strings.txt", strings, append);
        } catch (IOException e) {
            System.out.println("Error writing output files.");
            e.printStackTrace();
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

    private static void printIntegerStats(List<Long> list, boolean full) {
        System.out.println("Integers: " + list.size());
        if (full) {
            long min = Collections.min(list);
            long max = Collections.max(list);
            long sum = 0;
            for (long x : list) sum += x;
            double avg = (double) sum / list.size();
            System.out.printf("  Min: %d, Max: %d, Sum: %d, Avg: %.2f%n", min, max, sum, avg);
        }
    }

    private static void printFloatStats(List<Double> list, boolean full) {
        System.out.println("Floats: " + list.size());
        if (full) {
            double min = Collections.min(list);
            double max = Collections.max(list);
            double sum = 0;
            for (double x : list) sum += x;
            double avg = sum / list.size();
            System.out.printf("  Min: %.5f, Max: %.5f, Sum: %.5f, Avg: %.5f%n", min, max, sum, avg);
        }
    }

    private static void printStringStats(List<String> list, boolean full) {
        System.out.println("Strings: " + list.size());
        if (full) {
            int minLen = list.stream().mapToInt(String::length).min().orElse(0);
            int maxLen = list.stream().mapToInt(String::length).max().orElse(0);
            System.out.printf("  Shortest Length: %d, Longest Length: %d%n", minLen, maxLen);
        }
    }
}