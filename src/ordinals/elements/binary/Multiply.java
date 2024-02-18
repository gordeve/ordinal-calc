package ordinals.elements.binary;

import ordinals.elements.*;

public class Multiply extends Binary {
    public Multiply(Expression a, Expression b) {
        super(a, b, "*");
    }

    @Override
    public CNF evaluateCNF() {
        return CNF.multiply(a.evaluateCNF(), b.evaluateCNF());
    }
    
}
