package jumpingalien.model.statement;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.Direction;
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
		gameObject.endMoveHorizontally(dir);
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