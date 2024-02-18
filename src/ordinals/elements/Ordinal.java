package ordinals.elements;

public class Ordinal implements Expression {
    private final int value;
   
    public Ordinal(String s) throws NumberFormatException {
        value = (s.equals("w") ? -1 : Integer.parseInt(s));
    }

    public Ordinal(int value) {
        this.value = value;
    }

    @Override
    public CNF evaluateCNF() {
        CNF result = new CNF(1);
        if (value == -1) {
            result.push(new Ordinal(1), 1);
        } else if (value != 0) {
            result.push(new Ordinal(0), value);
        }
        return result;
    }   

    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Expression) {
            Expression that = (Expression) object;
            return (this.compare(that) == 0);
        }
        return false;
    }

    @Override
    public int compare(Expression expression) {
        if (expression instanceof Ordinal) {
            Ordinal that = (Ordinal) expression;
            if (this.value == that.value) {
                return 0;
            } else if (this.value == -1 || this.value > that.value) {
                return 1;
            } else {
                return -1;
            }
        }
        return this.evaluateCNF().compare(expression.evaluateCNF());
    }
}
