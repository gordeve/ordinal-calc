package ordinals.elements;

public class Equation {
    private final Expression a;
    private final Expression b;

    public Equation(Expression a, Expression b) {
        this.a = a;
        this.b = b;
    }

    public boolean calculate() {
        return a.equals(b);
    } 

    public String toString() {
        return a + " = " + b;
    }
 }