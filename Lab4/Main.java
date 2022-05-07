package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {

    public static String[] Vn;
    public static String[] Vt;
    public static HashMap<String, HashSet<String>> productions = new HashMap<>();
    static String nextLine;
    static Scanner scanner;

    public static void main (String[] args) {
        try {
            File file = new File("/Users/mbugaescu/IdeaProjects/Lab4/src/Lab4/grammar.txt");
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        nextLine = scanner.nextLine();
        Vn = nextLine.split(" ");

        nextLine = scanner.nextLine();
        Vt = nextLine.split(" ");

        while (scanner.hasNextLine()){

            nextLine =scanner.nextLine();
            String[] str = nextLine.split(" ");
            HashSet<String> set = productions.get(str[0]);

            if(set==null){
                set = new HashSet<>();
                productions.put(str[0],set);
            }

            for (int i = 1; i < str.length; i++) {
                set.add(str[i]);
            }
        }

        System.out.println(productions);
        EpsilonRemove epsilon = new EpsilonRemove(Vn, Vt, productions);
    }
}
