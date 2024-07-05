/*
 * Author: Nathan Tebbs
*/

public class Sim2_HalfAdder {
	public void execute() {
		// set inputs for xor to a and b, this gives us our sum result
		xor.a.set(a.get());
		xor.b.set(b.get());

		// set inputs for and to a and b, this gives our carry result
		and.a.set(a.get());
		and.b.set(b.get());

		// execute xor & and to compute the results for sum and carry
		xor.execute();
		and.execute();

		// set our sum and carry values to be the outputs of xor & and
		sum.set(xor.out.get());
		carry.set(and.out.get());
	}

	// inputs
	public RussWire a, b;
	// output
	public RussWire sum;
	public RussWire carry;

	// internal components
	public AND and;
	public XOR xor;

	public Sim2_HalfAdder() {
		// inputs
		a = new RussWire();
		b = new RussWire();

		// output
		sum = new RussWire();
		carry = new RussWire();

		// internal components
		and = new AND();
		xor = new XOR();
	}

}
