package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to of self expression.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class SelfE implements Expression {
	
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the self expression.
	 * 
	 * @param	sourceLocation
	 * 			The location in the source file where this self expression was called.
	 */
	public SelfE(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the self expression was called.
	 * @return	The game object of this program.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return program.getGameObject();
	}
	
	/**
	 * @param	program
	 * 			The program in which the self expression was called.
	 * @param	object
	 * 			The object for which the self expression was called.
	 * @return	The game object of this program.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return program.getGameObject();
	}

	/**
	 * @return	The location in the source file where this self expression was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
