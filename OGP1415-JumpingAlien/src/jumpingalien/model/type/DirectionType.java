package jumpingalien.model.type;

import jumpingalien.part3.programs.IProgramFactory.Direction;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of direction types.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
@Value
public class DirectionType extends Type<Direction> {

	/**
	 * Initialize the direction type
	 * 
	 * @param	direction
	 * 			The value the diretion type should get.
	 */
	public DirectionType(Direction direction) {
		super(direction);
	}
}
