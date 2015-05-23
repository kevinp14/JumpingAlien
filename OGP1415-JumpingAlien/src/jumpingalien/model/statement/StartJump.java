package jumpingalien.model.statement;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to make a game object jump.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class StartJump implements Statement {
	
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the start jump statement.
	 * 
	 * @param	sourceLocation
	 * 			The location in the source file where this start jump statement was called.
	 */
	public StartJump(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the start jump statement has to be executed.
	 * @param	condition
	 * 			The condition under which the start jump statement has to be executed.
	 * @effect	The game object in the given program starts to jump.
	 * 			| GameObject gameObject = (GameObject) program.getGameObject()
	 * 			| gameObject.startJump()
	 */
	@Override
	public void execute(Program program, Expression condition) {
		GameObject gameObject = (GameObject) program.getGameObject();
		gameObject.startJump();
	}


	/**
	 * @return	The location in the source file where this start jump statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
