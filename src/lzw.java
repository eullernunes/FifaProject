import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Lzw {
    public static void compress(String inputFile, String outputFile) throws IOException {
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char) i, i);
        }

        InputStream inputStream = new FileInputStream(inputFile);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

        int nextCode = 256;
        String currentString = "" + (char) inputStream.read();
        int currentChar = -1;
        while ((currentChar = inputStream.read()) != -1) {
            String newString = currentString + (char) currentChar;
            if (dictionary.containsKey(newString)) {
                currentString = newString;
            } else {
                outputStream.write(dictionary.get(currentString));
                dictionary.put(newString, nextCode++);
                currentString = "" + (char) currentChar;
            }
        }

        if (!currentString.equals("")) {
            outputStream.write(dictionary.get(currentString));
        }

        inputStream.close();
        outputStream.close();
    }

    public static void decompress(String inputFile, String outputFile) throws IOException {
        Map<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char) i);
        }

        InputStream inputStream = new FileInputStream(inputFile);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

        int nextCode = 256;
        int currentCode = inputStream.read();
        String currentString = dictionary.get(currentCode);
        outputStream.write(currentString.getBytes());

        int previousCode = currentCode;
        while ((currentCode = inputStream.read()) != -1) {
            String newString;
            if (dictionary.containsKey(currentCode)) {
                newString = dictionary.get(currentCode);
            } else {
                newString = dictionary.get(previousCode) + currentString.charAt(0);
            }

            outputStream.write(newString.getBytes());
            dictionary.put(nextCode++, dictionary.get(previousCode) + newString.charAt(0));
            previousCode = currentCode;
            currentString = newString;
        }

        inputStream.close();
        outputStream.close();
    }
}