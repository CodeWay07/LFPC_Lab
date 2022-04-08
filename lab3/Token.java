package lab3;

public class Token {
    final TokenType type;
    final String charcombination;
    final Object literal;
    final int line;

    Token(TokenType type, String charcombination, Object literal, int line) {
        this.type = type;
        this.charcombination = charcombination;
        this.literal = literal;
        this.line = line;

    }

    @Override
    public String toString() {
        return "{" +
                "type=" + type +
                ", charcombination='" + charcombination + '\'' +
                ", literal=" + literal +
                '}';
    }

}