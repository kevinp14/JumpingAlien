package jumpingalien.model.statement;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to make a game object stop jumping.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class StopJump implements Statement {
	
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the stop jump statement.
	 * 
	 * @param	sourceLocation
	 * 			The location in the source file where this stop jump statement was called.
	 */
	public StopJump(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the stop jump statement has to be executed.
	 * @param	condition
	 * 			The condition under which the stop jump statement has to be executed.
	 * @effect	The game object in the given program stops jumping.
	 * 			| GameObject gameObject = (GameObject) program.getGameObject()
	 * 			| gameObject.endJump()
	 */
	@Override
	public void execute(Program program, Expression condition) {
		GameObject gameObject = (GameObject) program.getGameObject();
		gameObject.endJump();
	}
	
	/**
	 * @return	The location in the source file where this stop jump statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	@Override
	public void executeForGivenObject(Program program, Expression condition,
			Object object) {
		GameObject gameObject = (GameObject) object;
		gameObject.stopJump();
	}

}
