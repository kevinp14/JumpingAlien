package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to check if a game object is moving in a given direction.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class IsMoving implements Expression {
	
	private Expression object;
	private Expression direction;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the is air checker.
	 * 
	 * @param	object
	 * 			The game object that has to be checked.
	 * @param	direction
	 * 			The direction in which the game object should be moving.
	 * @param	sourceLocation
	 * 			The location in the source file where this checker was called.
	 */
	public IsMoving(Expression object, Expression direction, 
			SourceLocation sourceLocation){
		this.object = object;
		this.direction = direction;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @return	True if and only if the game object is moving horizontally in case the given direction
	 * 			is left or right, falling in case it was down, and jumping in case it was up.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.object.evaluate(program);
		Direction direction = (Direction) this.direction.evaluate(program);
		if ((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
			return (gameObject.isMovingHorizontally());
		}
		else if (direction == Direction.DOWN) {
			return (gameObject.isFalling());
		}
		else if (direction == Direction.UP) {
			return (gameObject.isJumping());
		}
		return false;
	}
	
	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @param	object
	 * 			The object for which the checker has to be evaluated.
	 * @return	True if and only if the game object is moving horizontally in case the given direction
	 * 			is left or right, falling in case it was down, and jumping in case it was up.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		GameObject gameObject = (GameObject) object;
		Direction direction = (Direction) this.direction.evaluate(null);
		if ((direction == Direction.LEFT) || (direction == Direction.RIGHT)){
			return (gameObject.getHorizontalVelocity() != 0);
		}
		else if (direction == Direction.DOWN) {
			return (gameObject.isFalling());
		}
		else if (direction == Direction.UP) {
			return (gameObject.isJumping());
		}
		return false;
	}

	/**
	 * @return	The location in the source file where this checker was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
