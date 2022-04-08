package lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int curr = 0;
    private int line = 1;

    private static final Map<String, TokenType> keywords;
    private static final Map<Character, TokenType> characters;

    static {
        keywords = new HashMap<String, TokenType>();
        //FUNCTION, VOID, RETURN, IF, ELSE,
        keywords.put("return", TokenType.RETURN);
        keywords.put("string", TokenType.STRING);
        keywords.put("else", TokenType.ELSE);
        keywords.put("void", TokenType.VOID);
        keywords.put("float", TokenType.FLOAT);
        keywords.put("if", TokenType.IF);
        keywords.put("int", TokenType.INT);


        characters = new HashMap<>();
        characters.put('(', TokenType.LEFT_PARENTHESES);
        characters.put(')', TokenType.RIGHT_PARENTHESES);
        characters.put('[', TokenType.LEFT_BRACKET);
        characters.put(']', TokenType.RIGHT_BRACKET);
        characters.put('{', TokenType.LEFT_CURLY_BRACKET);
        characters.put('}', TokenType.RIGHT_CURLY_BRACKET);
        characters.put(',', TokenType.COMMA);
        characters.put(';', TokenType.SEMICOLON);
        characters.put(':', TokenType.COLON);
        characters.put('+', TokenType.PLUS);
        characters.put('-', TokenType.MINUS);
        characters.put('*', TokenType.STAR);
    }

    Lexer(String source) {
        this.source = source;
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, curr);
        tokens.add(new Token(type, text, literal, line));
    }

    private boolean match(char expected) {
        if (isAtEnd())
            return false;
        if (source.charAt(curr) != expected)
            return false;

        curr++;
        return true;
    }

    private boolean isAtEnd() {
        return curr >= source.length();
    }

    private char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(curr);
    }

    private char peekNext() {
        if (curr + 1 >= source.length())
            return '\0';
        return source.charAt(curr + 1);
    }

    private char advance() {
        return source.charAt(curr++);
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n')
                line++;
            advance();
        }

        if (isAtEnd()) {
            System.out.println("Unterminated string" + " (Line " + line + ")");
        }

        advance();

        String val = source.substring(start + 1, curr - 1);
        addToken(TokenType.STRING, val);
    }

    private void number() {
        while ('0' <= peek() && peek() <= '9') advance();

        boolean isFloat = false;
        if (peek() == '.' && ('0' <= peekNext() && peekNext() <= '9')) {
            isFloat = true;
            advance();
            while ('0' <= peek() && peek() <= '9')
                advance();
        }

        if (isFloat)
            addToken(TokenType.FLOAT, Float.parseFloat(source.substring(start, curr)));
        else
            addToken(TokenType.INT, Integer.parseInt(source.substring(start, curr)));
    }

    private void identifier() {
        while (Character.isLetterOrDigit(peek()))
            advance();

        String text = source.substring(start, curr);
        TokenType type = keywords.get(text);
        if (type == null)
            type = TokenType.IDENTIFIER;

        addToken(type);
    }

    private void scanToken() {
        char c = source.charAt(curr++);

        switch (c) {
            case '/':
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd())
                        advance();
                }
                else {
                    addToken(TokenType.SLASH);
                }
                break;
            case '!':
                addToken(match('=') ? TokenType.NOT_EQUAL : TokenType.NOT);
                break;
            case '=':
                addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
                break;
            case '<':
                addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
                break;
            case '>':
                addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            case '"':
                string();
                break;
            default:
                if (characters.containsKey(c)) {
                    addToken(characters.get(c));
                }
                else if (Character.isDigit(c)) {
                    number();
                }
                else if (Character.isLetter(c)) {
                    identifier();
                }
                else {
                    System.out.println("Unknown character" + " (Line " + line + ")");
                }
                break;
        }
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = curr;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }
}