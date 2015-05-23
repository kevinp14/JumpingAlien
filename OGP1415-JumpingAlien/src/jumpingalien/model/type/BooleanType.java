package jumpingalien.model.type;

import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of boolean types.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
@Value
public class BooleanType extends Type<Boolean> {
	
	/**
	 * Initialize the boolean type
	 * 
	 * @param	value
	 * 			The value the boolean type should get.
	 */
	public BooleanType(Boolean value) {
		super(value);
	}
}
