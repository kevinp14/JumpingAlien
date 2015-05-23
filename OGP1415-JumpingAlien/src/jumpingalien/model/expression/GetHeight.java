package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of to get the height of a game object.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class GetHeight implements Expression {
	
	private Expression expression;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the height calculator.
	 * 
	 * @param	expr
	 * 			The game object for which the height has to be calculated.
	 * @param	sourceLocation
	 * 			The location in the source file where this height is needed.
	 */
	public GetHeight(Expression expr, SourceLocation sourceLocation){
		this.expression = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the height has to be evaluated.
	 * @return	The height of the given game object.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expression.evaluate(program);
		int height = gameObject.getCurrentSprite().getHeight();
		return height;
	}

	/**
	 * @param	program
	 * 			The program in which the height has to be evaluated.
	 * @param	object
	 * 			The object for which the height has to be evaluated.
	 * @return	The height of the given game object.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return false;
	}
	
	/**
	 * @return	The location in the source file where this height is needed.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
