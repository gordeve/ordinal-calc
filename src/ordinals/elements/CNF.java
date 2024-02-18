package ordinals.elements;

import java.util.List;
import java.util.ArrayList;

import ordinals.elements.binary.Add;


public class CNF implements Expression {
    List<Pair> values;

    public CNF() {
        values = new ArrayList<>();
    }

    public CNF(int capacity) {
        values = new ArrayList<>(capacity);
    }

    public CNF(Expression expression) {
        values = new ArrayList<>();
        push(expression, 1);
    }

    private Pair get(int index) {
        if (index >= this.values.size()) {
            return new Pair(new Ordinal(0), 0);
        }
        return this.values.get(index);
    }

    public int compare(CNF that) {
        if (this.values.size() > that.values.size()) {
            return -1 * that.compare(this);
        }
        for (int i = 0; i < values.size(); i++) {
            Pair a = this.values.get(i);
            Pair b = that.values.get(i);
            int res = a.power.compare(b.power);
            if (res != 0) {
                return res;
            } else if (!a.digit.equals(b.digit)) {
                return a.digit - b.digit;
            }
        }
        return this.values.size() - that.values.size(); 
    }

    public boolean equals(CNF that) {
        return compare(that) == 0;
    }
    
    public static CNF add(CNF a, CNF b) {
        if (b.values.isEmpty()) {
            return a;
        }
        if (a.values.isEmpty()) {
            return b;
        }
        
        int r = 0, last = a.get(r).power.compare(b.get(0).power);
        while (last > 0 && r < a.values.size()) {
            r++;
            if (r < a.values.size()) {
                last = a.get(r).power.compare(b.get(0).power);
            } else {
                last = 1;
                break;
            }
        }
        CNF result = new CNF(r + b.values.size());
        for (int i = 0; i < r; i++) {
            result.values.add(a.get(i));
        }
        if (last == 0) {
            result.push(b.get(0).power, a.get(r).digit + b.get(0).digit);
        } else {
            result.values.add(b.get(0));
        }
        for (int i = 1; i < b.values.size(); i++) {
            result.values.add(b.get(i));
        }
        return result;
    }

    public static CNF multiply(CNF a, CNF b) {
        if (b.values.isEmpty() || a.values.isEmpty()) {
            return new CNF();
        }

        int m = b.lastDigit();
        int k = a.values.size(), q = b.values.size() + (m == 0 ? 1 : 0);

        CNF result = new CNF(q - 1 + (m != 0 ? k : 0));
        for (int i = 0; i < q - 1; i++) {
            result.push(new Add(a.get(0).power, b.get(i).power), b.get(i).digit); 
        }   
        if (m != 0) {
            result.push(a.get(0).power, a.get(0).digit * m);
            for (int i = 1; i < k; i++) {
                result.values.add(a.get(i));
            }
        }
        return result;
    }

    public static CNF finitePower(CNF a, int r) {
        if (r == 1) {
            return a;
        } else if (r == 0) { 
            return new Ordinal(1).evaluateCNF();
        }
        CNF result = finitePower(a, r / 2);
        result = multiply(result, result);
        if (r % 2 == 1) {
            result = multiply(result, a);
        }
        return result;
    }

    public static CNF power(CNF a, CNF b) {
        if (b.values.isEmpty()) {
            return new Ordinal(1).evaluateCNF();
        }
        if (a.isFinite() && a.lastDigit() <= 1) {
            return a;
        }
        int m = b.lastDigit();
        CNF result = finitePower(a, m);
        int q = b.values.size() + (m == 0 ? 1 : 0);
        for (int i = q - 2; i >= 0; i--) {
            if (a.isFinite()) {
                CNF power = new CNF(b.get(i).power.evaluateCNF());
                if (((CNF) power.get(0).power).isFinite()) {
                    power = new CNF(new Ordinal(((CNF) power.get(0).power).lastDigit() - 1));
                }
                result = multiply(finitePower(new CNF(power), b.get(i).digit), result);
            } else {
                Expression power = multiply(a.get(0).power.evaluateCNF(), new CNF(b.get(i).power));
                result = multiply(finitePower(new CNF(power), b.get(i).digit), result);
            }
        }
        return result;
    }

    private boolean isFinite() {
        return values.size() <= 1 && get(0).power.equals(new Ordinal(0));
    }

    public void push(Expression a, int b) {
        values.add(new Pair(a, b));
    }
    
    public int lastDigit() {
        if (values.isEmpty() || !get(values.size() - 1).power.equals(new Ordinal(0))) {
            return 0;
        }
        return get(values.size() - 1).digit;
    }

    static class Pair implements Comparable<Pair> {
        Expression power;
        Integer digit;

        protected Pair(Expression power, Integer digit) {
            this.power = power;
            this.digit = digit;
        }

        @Override
        public int compareTo(Pair that) {
            return -this.power.compare(that.power);
        }
    }
    @Override
    public int compare(Expression expression) {
        return compare(expression.evaluateCNF());
    }

    @Override
    public CNF evaluateCNF() {
        return this;
    }    
}
