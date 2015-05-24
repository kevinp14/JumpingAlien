package jumpingalien.model.statement;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to make a game object stop ducking.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class StopDuck implements Statement {
	
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the stop duck statement.
	 * 
	 * @param	sourceLocation
	 * 			The location in the source file where this stop duck statement was called.
	 */
	public StopDuck(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the stop duck statement has to be executed.
	 * @param	condition
	 * 			The condition under which the stop duck statement has to be executed.
	 * @effect	The game object in the given program stops ducking.
	 * 			| GameObject gameObject = (GameObject) program.getGameObject()
	 * 			| gameObject.endDuck()
	 */
	@Override
	public void execute(Program program, Expression condition) {
		GameObject gameObject = (GameObject) program.getGameObject();
		gameObject.endDuck();
	}

	/**
	 * @param	program
	 * 			The program in which the stop duck statement has to be executed.
	 * @param	condition
	 * 			The condition under which the stop duck statement has to be executed.
	 * @param	object
	 * 			The object for which the stop duck statement has to be executed.
	 * @effect	The game object in the given program stops ducking.
	 * 			| GameObject gameObject = (GameObject) program.getGameObject()
	 * 			| gameObject.endDuck()
	 */
	@Override
	public void executeForGivenObject(Program program, Expression condition,
			Object object) {
		GameObject gameObject = (GameObject) object;
		gameObject.endDuck();
	}
	
	/**
	 * @return	The location in the source file where this stop duck statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
