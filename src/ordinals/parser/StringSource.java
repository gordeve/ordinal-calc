package ordinals.parser;

public class StringSource implements CharSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public int getPos() {
        return pos;
    }

    @Override
    public void setPos(int x) {
        this.pos = x;
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public char back() {
        return data.charAt(pos--);
    }
}
