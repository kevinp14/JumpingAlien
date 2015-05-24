package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of directions.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class DirectionConstant implements Expression {
	
	private Direction direction;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the direction.
	 * 
	 * @param	direction
	 * 			The given direction.
	 * @param	sourceLocation
	 * 			The location in the source file where this direction was created.
	 */
	public DirectionConstant(Direction direction, SourceLocation sourceLocation){
		this.direction = direction;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the direction has to be created.
	 * @return	The given direction.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.direction;
	}
	
	/**
	 * @param	program
	 * 			The program in which the direction has to be created.
	 * @param	object
	 * 			The object for which the direction has to be created.
	 * @return	The given direction.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return (Direction)object;
	}
	
	/**
	 * @return	The location in the source file where this direction was created.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
}
