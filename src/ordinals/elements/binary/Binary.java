package ordinals.elements.binary;

import ordinals.elements.*;

public abstract class Binary implements Expression {
    protected Expression a;
    protected Expression b;
    private final String operator;

    public Binary(Expression a, Expression b, String operator) {
        this.a = a;
        this.b = b;
        this.operator = operator;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Expression) {
            Expression that = (Expression) object;
            return this.evaluateCNF().equals(that.evaluateCNF());
        }
        return false;
    }

    @Override
    public abstract CNF evaluateCNF();

    public String toString() {
        return "(" + a.toString() + operator + b.toString() + ")";
    }

    @Override
    public int compare(Expression expression) {
        return this.evaluateCNF().compare(expression.evaluateCNF());
    }
}
