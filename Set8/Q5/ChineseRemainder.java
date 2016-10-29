package softskills;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ChieneseRemainder {
    List<Modulo> moduloInputs;
    boolean isSolutionPossible;
    BigInteger solution;
    BigInteger M;

    public ChieneseRemainder() {
        moduloInputs = new ArrayList<Modulo>();
        isSolutionPossible = true;
    }

    public void extendedEuclideanAlgorithm() {
        BigInteger N = BigInteger.ONE;
        for (Iterator iterator = this.moduloInputs.iterator(); iterator.hasNext(); ) {
            N = N.multiply(BigInteger.valueOf(((Modulo) iterator.next()).divisor));
        }

        for (Iterator iterator = this.moduloInputs.iterator(); iterator.hasNext(); ) {
            Modulo moduloN = (Modulo) iterator.next();
            int n = moduloN.divisor;
            BigInteger[] bezoutCoefficient = calculateGCD(BigInteger.valueOf(n), N.divide(BigInteger.valueOf(n)));

            moduloN.multInv = bezoutCoefficient[1];
        }

        BigInteger x = BigInteger.ZERO;
        for (Iterator iterator = this.moduloInputs.iterator(); iterator.hasNext(); ) {
            Modulo moduloN = (Modulo) iterator.next();
            BigInteger e = moduloN.multInv.multiply(BigInteger.valueOf(moduloN.remainder)).multiply(N).divide(BigInteger.valueOf(moduloN.divisor));

            x = x.add(e);
        }

        x = leastPosEquiv(x, N);
        this.solution = x;
        this.M = N;
    }

    public BigInteger[] calculateGCD(BigInteger a, BigInteger b) {

        if (a.compareTo(b) == -1) {
            BigInteger[] coefficients = calculateGCD(b, a);
            BigInteger[] output = {coefficients[1], coefficients[0]};
            return output;
        }
        BigInteger old_r = a;
        BigInteger r = b;
        BigInteger s = BigInteger.ZERO;
        BigInteger old_s = BigInteger.ONE;
        BigInteger t = BigInteger.ONE;
        BigInteger old_t = BigInteger.ZERO;

        while (r.compareTo(BigInteger.ZERO) != 0) {
            BigInteger q = old_r.divide(r);
            BigInteger tempr = r;
            r = old_r.subtract(q.multiply(r));
            old_r = tempr;
            BigInteger temps = s;
            s = old_s.subtract(q.multiply(s));
            old_s = temps;
            BigInteger tempt = t;
            t = old_t.subtract(q.multiply(t));
            old_t = tempt;
        }

        return new BigInteger[]{old_s, old_t};
    }

	/*
	 * source:https://github.com/GregOwen/Chinese-Remainder-Theorem/blob/master/CRT.java
	 * */

    //finds the least positive integer equivalent to a mod m
    public BigInteger leastPosEquiv(BigInteger a, BigInteger m) {
        //a eqivalent to b mod -m <==> a equivalent to b mod m
        if (m.compareTo(BigInteger.ZERO) == -1)
            return leastPosEquiv(a, m.multiply(BigInteger.valueOf(-1)));
        //if 0 <= a < m, then a is the least positive integer equivalent to a mod m
        if (a.compareTo(BigInteger.ZERO) >= 0 && a.compareTo(m) == -1)
            return a;

        //for negative a, find the least negative integer equivalent to a mod m
        //then add m
        if (a.compareTo(BigInteger.ZERO) < 0)
            return leastPosEquiv(a.multiply(BigInteger.valueOf(-1)), m).multiply(BigInteger.valueOf(-1)).add(m);

        //the only case left is that of a,m > 0 and a >= m

        //take the remainder according to the Division algorithm
        BigInteger q = a.divide(m);
	    
	    /*
	     * a = qm + r, with 0 <= r < m
	     * r = a - qm is equivalent to a mod m
	     * and is the least such non-negative number (since r < m)
	     */
        return a.subtract(q.multiply(m));
    }
}

class Modulo {
    int divisor;
    int remainder;
    BigInteger multInv;

    public Modulo(int divisor, int remainder) {
        this.divisor = divisor;
        this.remainder = remainder;
    }
}