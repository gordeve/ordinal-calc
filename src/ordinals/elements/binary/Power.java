package ordinals.elements.binary;

import ordinals.elements.*;

public class Power extends Binary {
    public Power(Expression a, Expression b) {
        super(a, b, "^");
    }

    @Override
    public CNF evaluateCNF() {
        return CNF.power(a.evaluateCNF(), b.evaluateCNF());
    }
    
}
