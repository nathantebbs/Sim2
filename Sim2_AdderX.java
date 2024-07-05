/*
 * AUTHOR: NATHAN TEBBS 
 */

public class Sim2_AdderX {
	public void execute() {
		// carryIn bit initialized to false assuming no carryIn from a previous -
		// full
		boolean carryIn = false;

		// loop through each element of a and b, adding them together
		for (int i = 0; i < a.length; i++) {
			add[i].a.set(a[i].get());
			add[i].b.set(b[i].get());

			// set the carryIn as the previous carryOut
			add[i].carryIn.set(carryIn);
			add[i].execute();
			sum[i].set(add[i].sum.get());

			// set the next carryIn as the carryOut of our add, NOTE: used in line 20
			carryIn = add[i].carryOut.get();
		}

		// Set the carryOut of our add to be the final carryIn value assigned
		carryOut.set(carryIn);

		// OVERFLOW = (A ^ B ^ ~C) v (~A ^ ~B ^ C)

		// A = sign of int a
		// B = sign of int b
		// C = sign of sum
		boolean A = a[a.length - 1].get();
		boolean B = b[b.length - 1].get();
		boolean C = sum[sum.length - 1].get();

		// Calculate the negation of A, B, C
		notA.in.set(A);
		notB.in.set(B);
		notC.in.set(C);
		notA.execute();
		notB.execute();
		notC.execute();

		// FIRST HALF OF THE FINAL OR
		// and1 = (A ^ B)
		and1.a.set(A);
		and1.b.set(B);
		and1.execute();
		// and2 = ((A ^ B) ^ ~C)
		and2.a.set(and1.out.get());
		and2.b.set(notC.out.get());
		and2.execute();

		// SECOND HALF OF THE FINAL OR
		// and3 = (~A ^ ~B)
		and3.a.set(notA.out.get());
		and3.b.set(notB.out.get());
		and3.execute();
		// and4 = ((~A ^ ~B) ^ C)
		and4.a.set(and3.out.get());
		and4.b.set(C);
		and4.execute();

		// FINAL OR
		// Overflow = finalOR = (and2 v and4) = ((~A ^ B) ^ C) v ((A ^ ~B) ^ ~C)
		finalOR.a.set(and2.out.get());
		finalOR.b.set(and4.out.get());
		finalOR.execute();

		overflow.set(finalOR.out.get());
	}

	// inputs
	RussWire[] a, b;

	// output
	RussWire[] sum;
	RussWire carryOut, overflow;

	// Internal Components
	Sim2_FullAdder[] add;
	AND and1;
	AND and2;
	AND and3;
	AND and4;

	NOT notA;
	NOT notB;
	NOT notC;

	OR finalOR;

	public Sim2_AdderX(int x) {
		// inputs
		a = new RussWire[x];
		b = new RussWire[x];

		// outputs
		sum = new RussWire[x];
		carryOut = new RussWire();
		overflow = new RussWire();

		// internal componenets
		add = new Sim2_FullAdder[x];

		and1 = new AND();
		and2 = new AND();
		and3 = new AND();
		and4 = new AND();

		finalOR = new OR();

		notA = new NOT();
		notB = new NOT();
		notC = new NOT();

		// Initialize x amounts of bits and fullAdders
		for (int i = 0; i < x; i++) {
			a[i] = new RussWire();
			b[i] = new RussWire();
			sum[i] = new RussWire();
			add[i] = new Sim2_FullAdder();
		}

	}
}
