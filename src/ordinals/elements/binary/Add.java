package ordinals.elements.binary;

import ordinals.elements.*;

public class Add extends Binary {
    public Add(Expression a, Expression b) {
        super(a, b, "+");
    }

    @Override
    public CNF evaluateCNF() {
        return CNF.add(a.evaluateCNF(), b.evaluateCNF());
    }
    
}
