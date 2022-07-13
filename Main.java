import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        initialVerification(args);
        Map<String, String> map = createMap(args);
        String text = replaceData(loadTemplate(args), map);
        saveToNewFile(text, args);
        Path path = Paths.get(args[2]);
        System.out.printf("Utworzono plik \"%s\" \n", args[2]);
        System.out.print("Ścieżka do pliku: " + path.toAbsolutePath());


    }

    private static void initialVerification(String[] args) {

        try {
            if (args[0] != null) ;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Nie podano parametrów 1, 2 i 3");
            exitProgram();
        }
        try {
            if (args[0].equals(args[1])) {
                System.out.println("Ścieżka do pliku z listą parametrów nie może być taka sama jak ścieżka do pliku szablonu! \nPopraw parametry i uruchom program ponownie\n");
                exitProgram();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Nie podano parametrów 2 i 3");
            exitProgram();
        }
        try {
            if (args[1].equals(args[2])) {
                System.out.println("Ścieżka do pliku szablonu nie może być taka sama jak ścieżka do pliku zapisu! \nPopraw parametry i uruchom program ponownie\n");
                exitProgram();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Nie podano parametru 3");
            exitProgram();
        }
    }

    public static Map<String, String> createMap(String[] args) {
        Map<String, String> map = new HashMap<>();
        String text = readFile(args, 0);
        if (text.length() == 0) {
            System.out.println("Plik z listą parametrów nie może być pusty!\nNie udało się utworzyć pliku wyjściowego\n");
            exitProgram();
        }
        String[] strings = text.split("=|\n");
        for (int i = 0; i < strings.length; i = i + 2) {
            map.put(strings[i], strings[i + 1]);
        }
        return map;
    }

    public static String loadTemplate(String[] args) {
        String text = readFile(args, 1);
        if (text.length() == 0) {
            System.out.println("Plik szablonu nie może być pusty!\nNie udało się utworzyć pliku wyjściowego\n");
            exitProgram();
        }
        return text;
    }

    private static String replaceData(String string, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (string.contains(entry.getKey())) {
                string = string.replace(entry.getKey(), entry.getValue());
            }
        }
        return string;
    }


    private static void saveToNewFile(String text, String[] args) {
        try {
            File file = new File(args[2]);

            if (file.exists()) {
                Scanner scanner = new Scanner(System.in);
                String answer;
                do {
                    System.out.println(args[2] + " istnieje. Czy nadpisać? [T/N]");
                    answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("T")) {
                        PrintWriter writer = new PrintWriter(args[2]);
                        writer.println(text);
                        writer.close();
                    } else if (answer.equalsIgnoreCase("N")) {
                        System.out.println("Nie nadpisano pliku");
                        exitProgram();
                    } else {
                        System.out.println("Brak takiej opcji");
                    }
                } while (!answer.equalsIgnoreCase("T"));


            } else {
                PrintWriter writer = new PrintWriter(args[2], "UTF-8");
                writer.println(text);
                writer.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Ścieżka do zapisu musi istnieć i użytkownik systemu operacyjnego musi mieć prawo do odczytu \nNie udało się utworzyć pliku wyjściowego\n");
            exitProgram();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Nieprawidłowy format kodowania \nNie udało się utworzyć pliku wyjściowego\n");
            exitProgram();
        }
    }

    private static void exitProgram() {
        System.out.print("Program zakończył działanie");
        System.exit(1);
    }

    private static String readFile(String[] args, int parameter) {
        String text = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[parameter]))) {
            StringBuffer textBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                textBuffer.append(line);
                textBuffer.append("\n");
            }
            text = textBuffer.toString();
        } catch (IOException e) {
            System.out.printf("Brak pliku %s \nNie udało się utworzyć pliku wyjściowego \n", args[parameter]);
            exitProgram();
        }
        return text;
    }

}
