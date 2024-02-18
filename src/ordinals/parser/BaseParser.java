package ordinals.parser;

import ordinals.exceptions.ParserException;

public class BaseParser {
    private static final char END = '\0';
    private final CharSource source;
    private char ch = 0xffff;

    protected BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean take(final String expected) {
        int pos = source.getPos();
        for (final char c : expected.toCharArray()) {
            if (!take(c)) {
                source.setPos(pos - 1);
                take();
                return false;
            }
        }
        return true;
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            if (expected == END) {
                throw error("Expected EOF, found '" + ch + "'");
            } else {
                throw error("Expected '" + expected + "', found '" + ch + "'");
            }
        }
    }

    protected void expect(final String expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected char peek() {
        return ch;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected void eof() {
        expect(END);
    }

    protected boolean isEof() {
        return (ch == END);
    }

    protected boolean isDigit() {
        return Character.isDigit(ch);
    }

    protected boolean isWhitespace() {
        return Character.isWhitespace(ch);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected ParserException error(final String message) {
        return new ParserException(message + " at pos " + source.getPos());
    }
}
