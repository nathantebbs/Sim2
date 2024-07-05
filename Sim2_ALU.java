/*AUTHOR: NATHAN TEBBS*/

public class Sim2_ALU {
	public void execute() {

		// initial setup of the first alu element in the first pass
		// set first alu elements aluOp values
		alus[0].aluOp[0].set(aluOp[0].get());
		alus[0].aluOp[1].set(aluOp[1].get());
		alus[0].aluOp[2].set(aluOp[2].get());

		// set first alu elements a and b values as the first element in our a and b
		// input
		alus[0].a.set(a[0].get());
		alus[0].b.set(b[0].get());

		/*
		 * set first alu elements bInvert and carryIn as bNegate.
		 * This is only for first pass of execute after this the resulting carryOut -
		 * is the next elements carryIn. NOTE: see line 35 for implementation!
		 */
		alus[0].bInvert.set(bNegate.get());
		alus[0].carryIn.set(bNegate.get());

		// execute the first alu elements first pass execute
		alus[0].execute_pass1();

		/*
		 * now we will loop through the remaining alu elements and execute their first
		 * pass executes asswell
		 */
		for (int i = 1; i < alus.length; i++) {
			// set the aluOp code for the ith alu element
			alus[i].aluOp[0].set(aluOp[0].get());
			alus[i].aluOp[1].set(aluOp[1].get());
			alus[i].aluOp[2].set(aluOp[2].get());

			// set ith bInvert to the given bNegate input
			alus[i].bInvert.set(bNegate.get());

			// set the ith carryIn to be the previous elements carryOut
			alus[i].carryIn.set(alus[i - 1].carryOut.get());

			// set the ith bit on the a nd b inputs as the input to our alu element
			alus[i].a.set(a[i].get());
			alus[i].b.set(b[i].get());

			// execute pass 1 for the ith alu element
			alus[i].execute_pass1();
		}

		// initial setup for the first alu element before the second pass execute:

		/*
		 * IMPORTANT: set the first alu elements less as the final elements add result.
		 * This is the entire reason why we seperated our passes like we did in the
		 * code.
		 * We needed to ensure that we had already calculated the final alu elements
		 * addResult before doing this!!
		 */
		alus[0].less.set(alus[alus.length - 1].addResult.get());

		// Execute pass 2
		alus[0].execute_pass2();

		// set the first bit of our result to be the result of our first alu element
		result[0].set(alus[0].result.get());

		/*
		 * now we will loop through the remaining alu elements and execute their second
		 * pass executes asswell
		 */
		for (int i = 1; i < alus.length; i++) {
			// set less to false
			alus[i].less.set(false);

			// execute second pass
			alus[i].execute_pass2();

			// set the ith bit of the result to the ith alu elements result
			result[i].set(alus[i].result.get());
		}
	}

	// inputs
	RussWire[] aluOp;
	RussWire bNegate;
	RussWire[] a, b;

	// output
	RussWire[] result;

	Sim2_ALUElement[] alus;

	public Sim2_ALU(int x) {
		// inputs
		alus = new Sim2_ALUElement[x];
		aluOp = new RussWire[3];
		bNegate = new RussWire();
		a = new RussWire[x];
		b = new RussWire[x];

		// output
		result = new RussWire[x];

		// Initialize x amounts of alus, a bits, b bits, and result bits
		for (int i = 0; i < x; i++) {
			alus[i] = new Sim2_ALUElement();
			a[i] = new RussWire();
			b[i] = new RussWire();
			result[i] = new RussWire();
		}

		// Initialize 3 bits of our aluOp to be RussWires
		for (int i = 0; i < 3; i++) {
			aluOp[i] = new RussWire();
		}
	}
}
