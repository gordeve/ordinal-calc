package ordinals.parser;

public interface CharSource {
    boolean hasNext();
    char next();
    char back();
    int getPos();
    void setPos(int x);
}
