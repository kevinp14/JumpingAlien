package jumpingalien.model.statement;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.SelfMadeDirection;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to make a game object stop running.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class StopRun implements Statement {
	
	private Expression direction;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the stop duck statement.
	 * 
	 * @param	direction
	 * 			The direction in which the game object has to stop running.
	 * @param	sourceLocation
	 * 			The location in the source file where this stop run statement was called.
	 */
	public StopRun(Expression direction, SourceLocation sourceLocation){
		this.direction = direction;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the stop run statement has to be executed.
	 * @param	condition
	 * 			The condition under which the stop run statement has to be executed.
	 * @effect	The game object in the given program stops running in the given direction.
	 * 			| GameObject gameObject = (GameObject) program.getGameObject()
	 * 			| Direction dir = (Direction) this.direction.evaluate(program)
	 * 			| gameObject.endMoveHorizontally(dir)
	 */
	@Override
	public void execute(Program program, Expression condition) {
		GameObject gameObject = (GameObject) program.getGameObject();
		Direction dir = (Direction) this.direction.evaluate(program);
		SelfMadeDirection selfMadeDir = this.convertDirection(dir);
		gameObject.endMoveHorizontally(selfMadeDir);
	}
	
	/**
	 * @param	program
	 * 			The program in which the stop run statement has to be executed.
	 * @param	condition
	 * 			The condition under which the stop run statement has to be executed.
	 * @param	object
	 * 			The object for which the stop run statement has to be executed.
	 * @effect	The game object in the given program stops running in the given direction.
	 * 			| GameObject gameObject = (GameObject) program.getGameObject()
	 * 			| Direction dir = (Direction) this.direction.evaluate(program)
	 * 			| gameObject.endMoveHorizontally(dir)
	 */
	@Override
	public void executeForGivenObject(Program program, Expression condition,
			Object object) {
		GameObject gameObject = (GameObject) object;
		Direction dir = (Direction) this.direction.evaluate(program);
		SelfMadeDirection selfMadeDir = this.convertDirection(dir);
		gameObject.endMoveHorizontally(selfMadeDir);
	}
	
	public SelfMadeDirection convertDirection(Direction direction){
		if (direction == Direction.DOWN){
			return SelfMadeDirection.DOWN;
		}
		else if (direction == Direction.LEFT){
			return SelfMadeDirection.LEFT;
		}
		else if (direction == Direction.RIGHT){
			return SelfMadeDirection.RIGHT;
		}
		else{
			return SelfMadeDirection.UP;
		}
	}

	/**
	 * @return	The location in the source file where this stop run statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
