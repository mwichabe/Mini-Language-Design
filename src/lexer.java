import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lexer {
    private final String input;
    private int currentPosition;
    private ArrayList<Token> tokens;

    public lexer(String input) {
        this.input = input;
        this.currentPosition = 0;
        this.tokens = new ArrayList<>();
    }

    public ArrayList<Token> tokenize() {
        while (currentPosition < input.length()) {
            char currentChar = input.charAt(currentPosition);

            // Skip whitespaces
            if (Character.isWhitespace(currentChar)) {
                currentPosition++;
                continue;
            }

            // Try to match each token pattern
            boolean matched = false;
            for (TokenType tokenType : TokenType.values()) {
                Pattern pattern = Pattern.compile(tokenType.getPattern());
                Matcher matcher = pattern.matcher(input.substring(currentPosition));

                if (matcher.find()) {
                    String lexeme = matcher.group();
                    tokens.add(new Token(tokenType, lexeme));
                    currentPosition += lexeme.length();
                    matched = true;
                    break;
                }
            }

            // If no token pattern is matched, report an error
            if (!matched) {
                System.err.println("Error: Unexpected character at position " + currentPosition);
                break;
            }
        }

        return tokens;
    }

    public static void main(String[] args) {
        String inputCode = "2 + 3 * (4 - 1);";
        lexer lexer = new lexer(inputCode);
        ArrayList<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}

enum TokenType {
    NUMBER("\\d+"),
    ADDITION("\\+"),
    SUBTRACTION("-"),
    MULTIPLICATION("\\*"),
    DIVISION("/"),
    LEFT_PARENTHESIS("\\("),
    RIGHT_PARENTHESIS("\\)"),
    SEMICOLON(";");

    private final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}

class Token {
    private final TokenType type;
    private final String lexeme;

    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexeme='" + lexeme + '\'' +
                '}';
    }

    public TokenType getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    public String getLexeme() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLexeme'");
    }
}
