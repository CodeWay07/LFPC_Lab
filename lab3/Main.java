package lab3;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String inputFile = "/Users/mbugaescu/IdeaProjects/lab3/src/source.txt";

        String source = null;
        try {
            source = Files.readString(Paths.get(inputFile), StandardCharsets.UTF_8);
        }
        catch (IOException message) {
            System.out.println("An error occurred.");
            message.printStackTrace();
        }

        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
