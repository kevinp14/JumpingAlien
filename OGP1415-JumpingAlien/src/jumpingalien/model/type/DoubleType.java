package jumpingalien.model.type;

import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of double types.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
@Value
public class DoubleType extends Type<Double> {

	/**
	 * Initialize the double type
	 * 
	 * @param	value
	 * 			The value (given as a double) the double type should get.
	 */
	public DoubleType(Double value) {
		super(value);
	}
	
	/**
	 * Initialize the double type
	 * 
	 * @param	value
	 * 			The value (given as an integer) the double type should get.
	 */
	public DoubleType(Integer value) {
		super(new Double(value));
	}
}
