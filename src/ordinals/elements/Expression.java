package ordinals.elements;

public interface Expression {
    boolean equals(Object expression);
    int compare(Expression expression);
    CNF evaluateCNF();
}
