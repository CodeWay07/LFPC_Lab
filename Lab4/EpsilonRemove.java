package Lab4;

import java.util.HashMap;
import java.util.HashSet;

public class EpsilonRemove {
    public String[] Vn;
    public String[] Vt;
    public HashMap<String, HashSet<String>> productions;
    public HashMap<String, HashSet<String>> productions2;
    public HashSet<String> toChange;
    public boolean hasChanged = true;
    String letterToChange;

    public EpsilonRemove(String[] Vn, String[] Vt, HashMap<String, HashSet<String>> productions) {
        this.Vn = Vn;
        this.Vt = Vt;
        this.productions = productions;
        toChange = new HashSet<>();
        productions2 = new HashMap<>();

        this.nextState();
    }


    public void substitute() {

        for (String key : productions.keySet()) {
            for (String setElement : productions.get(key)) {
                if (contains(setElement, toChange)) {
                    add(productions2, key, setElement, letterToChange);
                }
            }
        }

        for (String key : productions2.keySet()) {
            HashSet<String> tempSet = new HashSet<>() {
                {
                    addAll(productions.get(key));
                    addAll(productions2.get(key));
                }
            };

            productions.put(key, tempSet);
        }
        productions2 = new HashMap<>();

        for (String key : toChange)
            productions.get(key).remove("*");
        System.out.println();
        System.out.println("Step 1. After epsilon elimination:");
        System.out.println(productions);
    }

    public void add (HashMap<String, HashSet<String>> map, String key, String str, String letter) {
        char character = letter.charAt(0);

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == character) {
                HashSet<String> set = map.get(key);

                if (set == null) {
                    set = new HashSet<>();
                    map.put(key, set);
                }
                String strToAdd = "";

                if (i != 0) {
                    strToAdd = strToAdd + str.substring(0, i);
                }
                strToAdd += str.substring(i + 1);

                if (strToAdd.length() > 0)
                    set.add(strToAdd);
                else {
                    strToAdd = "*";
                    set.add(strToAdd);
                }
                System.out.println(key + ": new production after Epsilon Elimination:" + strToAdd);
            }
        }
    }

    public boolean contains(String myStr, HashSet<String> set) {

        for (String s : set) {
            if (myStr.contains(s)) {
                letterToChange = s;
                return true;
            }
        }
        return false;
    }

    public void check() {
        for (String key : productions.keySet()) {
            for (String setElement : productions.get(key)) {
                if (setElement.equals("*")) {
                    hasChanged = true;
                    toChange.add(key);
                }
            }
        }
    }

    public void nextState() {
        while (hasChanged) {
            hasChanged = false;
            toChange = new HashSet<>();
            check();
            substitute();
        }
        UnitProductiveRemove unit = new UnitProductiveRemove(Vn, Vt, productions);
    }
}
