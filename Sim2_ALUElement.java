/* 
 * AUTHOR: NATHAN TEBBS
 */

public class Sim2_ALUElement {

	public void execute_pass1() {
		// 2 Bit MUX

		/*
		 * By default our 2 bit much will not consider the MSB and the bit before it
		 * This is because we aren't accessing any mux outputs beyond the reach
		 * of a single control bit. We initialize the LSB as the value given by bInvert.
		 * This will either choose between the negated version of b or b itself.
		 */
		mux_2by1.control[2].set(false);
		mux_2by1.control[1].set(false);
		mux_2by1.control[0].set(bInvert.get());

		/*
		 * When control wire is 0, it will select the first option. We want that to be
		 * when b is not negated because bInvert would be false in that case. So here
		 * we set the first output of the mux to be just b
		 */
		mux_2by1.in[0].set(b.get());

		// Negate b for second possible output of the 2 bit mux
		notB.in.set(b.get());
		notB.execute();

		/*
		 * In the case where bInvert is true, that will select the second (or first if
		 * you are 0 indexed), when bInvert is true we want to return the negation of b
		 */
		mux_2by1.in[1].set(notB.out.get());

		// Set all other inputs to the mux as false so that they are not null
		mux_2by1.in[2].set(false);
		mux_2by1.in[3].set(false);
		mux_2by1.in[4].set(false);
		mux_2by1.in[5].set(false);
		mux_2by1.in[6].set(false);
		mux_2by1.in[7].set(false);
		mux_2by1.execute();
		// END OF 2 BIT MUX

		// OR
		/*
		 * compute the value of (a || (b or ~b)), that is b depending on the output of
		 * our mux
		 */
		or.a.set(a.get());
		or.b.set(mux_2by1.out.get());
		or.execute();
		// END OF OR

		// AND
		/*
		 * compute the value of (a && (b or ~b)), that is b depending on the output of
		 * our mux
		 */
		and.a.set(a.get());
		and.b.set(mux_2by1.out.get());
		and.execute();
		// END OF AND

		// XOR
		/*
		 * compute the value of (a XOR((b or ~b))), that is b depending on the output of
		 * our mux
		 */
		xor.a.set(a.get());
		xor.b.set(mux_2by1.out.get());
		xor.execute();
		// END OF XOR

		// ADDER
		// compute the value of our full adder adding togher a and the ouput of our mux
		add.a.set(a.get());
		add.b.set(mux_2by1.out.get());

		// set the carry in of this operation to be the given carryIn
		add.carryIn.set(carryIn.get());

		// execute the add operation
		add.execute();

		// set the carrOut of our add operation to be our carryOut return value
		carryOut.set(add.carryOut.get());

		/*
		 * IMPORTANT: set the addResult to be the sum computed of our full adder
		 * This is what makes it possible to set the first alu elements less to the
		 * adder result of our last alu element even though the add aluOp might not
		 * have been passed
		 */
		addResult.set(add.sum.get());
		// END OF ADDER

	}

	public void execute_pass2() {

		// set the input for our 5bit mux to be our give aluOp code
		for (int i = 0; i < 3; i++) {
			mux_5by1.control[i].set(aluOp[i].get());
		}

		/*
		 * Set the options for our 5 bit mux:
		 * 0: AND
		 * 1: OR
		 * 2: ADD
		 * 3: LESS
		 * 4: XOR
		 */
		mux_5by1.in[0].set(and.out.get());
		mux_5by1.in[1].set(or.out.get());
		mux_5by1.in[2].set(add.sum.get());
		mux_5by1.in[3].set(less.get());
		mux_5by1.in[4].set(xor.out.get());

		/*
		 * set the rest of the inputs of the mux to be false so they are not null,
		 * also so we mimic the behavior of a 5 bit mux with the only inputs we access
		 * being the inputs defined above. These will not be accessed
		 */
		mux_5by1.in[5].set(false);
		mux_5by1.in[6].set(false);
		mux_5by1.in[7].set(false);

		// execute our mux
		mux_5by1.execute();

		// assign our result to be the output of the mux
		result.set(mux_5by1.out.get());
	}

	// input op
	RussWire[] aluOp;

	// input
	RussWire bInvert;
	RussWire a, b;
	RussWire carryIn;
	RussWire less;

	// outputs
	RussWire result;
	RussWire addResult;
	RussWire carryOut;

	// Internal Components
	Sim2_MUX_8by1 mux_2by1;
	Sim2_MUX_8by1 mux_5by1;
	Sim2_FullAdder add;
	NOT notB;
	OR or;
	AND and;
	XOR xor;

	public Sim2_ALUElement() {
		// inputs
		aluOp = new RussWire[3];
		bInvert = new RussWire();
		a = new RussWire();
		b = new RussWire();
		carryIn = new RussWire();
		less = new RussWire();

		// internal components
		add = new Sim2_FullAdder();
		mux_2by1 = new Sim2_MUX_8by1();
		mux_5by1 = new Sim2_MUX_8by1();
		notB = new NOT();
		or = new OR();
		and = new AND();
		xor = new XOR();

		// output
		result = new RussWire();
		addResult = new RussWire();
		carryOut = new RussWire();

		// initialize our 3 bit aluOp
		for (int i = 0; i < 3; i++) {
			aluOp[i] = new RussWire();
		}
	}
}
