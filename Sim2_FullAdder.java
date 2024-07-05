/*
 * Author: Nathan Tebbs
 */

public class Sim2_FullAdder {
	public void execute() {
		// Execute the initial half adder with given inputs a and b
		halfAdder_init.a.set(a.get());
		halfAdder_init.b.set(b.get());
		halfAdder_init.execute();

		/*
		 * Execute the second half adder, giving us the fullAdder's sum using the inputs
		 * given by the carryOut of the initial half adder and the given carryIn bit
		 */
		halfAdder_sum.a.set(carryIn.get());
		halfAdder_sum.b.set(halfAdder_init.sum.get());
		halfAdder_sum.execute();

		/*
		 * Set our carry out return to be either the carryOut of the final half adder
		 * or the carryOut of the initial halfAdder
		 */
		carryOutOr.a.set(halfAdder_sum.carry.get());
		carryOutOr.b.set(halfAdder_init.carry.get());
		carryOutOr.execute();
		carryOut.set(carryOutOr.out.get());

		// Set our sum output to be the sum of the final half adder
		sum.set(halfAdder_sum.sum.get());
	}

	// inputs
	public RussWire a, b, carryIn;

	// output
	public RussWire sum, carryOut;

	// internal components
	public Sim2_HalfAdder halfAdder_sum;
	public Sim2_HalfAdder halfAdder_init;
	public OR carryOutOr;

	public Sim2_FullAdder() {
		// inputs
		a = new RussWire();
		b = new RussWire();
		carryIn = new RussWire();

		// outputs
		sum = new RussWire();
		carryOut = new RussWire();

		// internal components
		halfAdder_sum = new Sim2_HalfAdder();
		halfAdder_init = new Sim2_HalfAdder();
		carryOutOr = new OR();
	}
}
