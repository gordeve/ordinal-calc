package ordinals.parser;

import ordinals.elements.*;
import ordinals.elements.binary.*;

public class OrdinalParser extends BaseParser {
    public OrdinalParser(CharSource source) {
        super(source);
    }

    public Equation parse() {
        Expression a = parseExpression();
        expect('=');
        Expression b = parseExpression();
        eof();
        return new Equation(a, b);
    }

    private Expression parseExpression() {
        Expression result = parseAddendum();
        while (take('+')) {
            result = new Add(result, parseAddendum());
        }
        return result;
    }

    private Expression parseAddendum() {
        Expression result = parseFactor();
        while (take('*')) {
            result = new Multiply(result, parseFactor());
        }
        return result;
    }

    private Expression parseFactor() {
        Expression result = parseTerm();
        if (take('^')) {
            result = new Power(result, parseFactor());
        }
        return result;
    }

    private Expression parseTerm() {
        Expression result;
        if (take('(')) {
            result = parseExpression();
            expect(')');
        } else if (take('w')) {
            result = new Ordinal("w");
        } else if (isDigit()) {
            StringBuilder s = new StringBuilder();
            while (isDigit()) {
                s.append(take());
            }
            try {
                result = new Ordinal(s.toString());
            } catch (NumberFormatException e) {
                throw error("Could not parse number");
            }
        } else {
            throw error("Could not parse term");
        }
        return result;
    }
}
