import java.util.Scanner;

import ordinals.elements.Equation;
import ordinals.parser.OrdinalParser;
import ordinals.parser.StringSource;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();
            Equation eq = new OrdinalParser(new StringSource(input)).parse();
            System.out.println(eq.calculate() ? "Equal" : "Unequal");
        }
    }
}
