/* 
 * AUTHOR: NATHAN TEBBS
 */

public class Sim2_MUX_8by1 {
	public void execute() {

		// local variables:

		// Control bits and their negations
		boolean A = control[2].get();
		boolean negateA;
		boolean B = control[1].get();
		boolean negateB;
		boolean C = control[0].get();
		boolean negateC;

		// input bits
		boolean in0 = in[0].get();
		boolean in1 = in[1].get();
		boolean in2 = in[2].get();
		boolean in3 = in[3].get();
		boolean in4 = in[4].get();
		boolean in5 = in[5].get();
		boolean in6 = in[6].get();
		boolean in7 = in[7].get();

		// AND operations
		boolean AND_in0;
		boolean AND_in1;
		boolean AND_in2;
		boolean AND_in3;
		boolean AND_in4;
		boolean AND_in5;
		boolean AND_in6;
		boolean AND_in7;

		boolean finalResult;

		// Calculate negations
		notA.in.set(A);
		notB.in.set(B);
		notC.in.set(C);
		notA.execute();
		notB.execute();
		notC.execute();

		// Assign negations
		negateA = notA.out.get();
		negateB = notB.out.get();
		negateC = notC.out.get();

		// MUX AND comparisons
		AND_in0 = (negateA && negateB && negateC && in0);
		AND_in1 = (negateA && negateB && C && in1);
		AND_in2 = (negateA && B && negateC && in2);
		AND_in3 = (negateA && B && C && in3);
		AND_in4 = (A && negateB && negateC && in4);
		AND_in5 = (A && negateB && C && in5);
		AND_in6 = (A && B && negateC && in6);
		AND_in7 = (A && B && C && in7);

		// Or all of our Ands to get our mux's output
		finalResult = (AND_in0 || AND_in1 || AND_in2 || AND_in3 || AND_in4 ||
				AND_in5 || AND_in6 || AND_in7);
		out.set(finalResult);
	}

	// control bits
	RussWire[] control;
	// input bits
	RussWire[] in;
	// output
	RussWire out;
	// Internal Bits
	NOT notB;
	NOT notC;
	NOT notA;

	public Sim2_MUX_8by1() {
		// inputs
		control = new RussWire[3];
		in = new RussWire[8];

		// output
		out = new RussWire();

		// internal componenets
		notA = new NOT();
		notB = new NOT();
		notC = new NOT();

		// initialize our 8 input bits for our mux
		for (int i = 0; i < 8; i++) {
			in[i] = new RussWire();
		}

		// initialize our 3 control bits for our mux
		for (int i = 0; i < 3; i++) {
			control[i] = new RussWire();
		}

	}
}
