package cz.itexpert.adventcode2023.day1;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        Scanner scanner = new
                Scanner(Objects.requireNonNull(Runner.class.getResourceAsStream("/day1.txt")));
        int line = 1;
        int result = 0;
        String currentLine;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            LinkedList<DictionaryItem> numbers = StringText.parseNumbers(currentLine);
            System.out.print("line: ");

            System.out.print(line++);
            System.out.print(" ");
            System.out.print(currentLine);
            System.out.print(" ");
            System.out.print(Arrays.toString(numbers.toArray()));
            System.out.print(" => ");
            String str =numbers.getFirst().getValue().toString()+ numbers.getLast().getValue().toString();
            result += Integer.parseInt(str );

            System.out.print(str);
            System.out.println();
        }
        System.out.println(result);
        scanner.close();
    }
}
