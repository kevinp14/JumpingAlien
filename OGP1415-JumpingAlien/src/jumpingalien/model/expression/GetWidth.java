package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of to get the width of a game object.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class GetWidth implements Expression {
	
	private Expression expression;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the width calculator.
	 * 
	 * @param	expr
	 * 			The game object for which the width has to be calculated.
	 * @param	sourceLocation
	 * 			The location in the source file where this width is needed.
	 */
	public GetWidth(Expression expr, SourceLocation sourceLocation){
		this.expression = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the width has to be evaluated.
	 * @return	The width of the given game object.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expression.evaluate(program);
		int width = gameObject.getCurrentSprite().getWidth();
		return width;
	}
	
	/**
	 * @param	program
	 * 			The program in which the width has to be evaluated.
	 * @param	object
	 * 			The object for which the width has to be evaluated.
	 * @return	The width of the given game object.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return false;
	}

	/**
	 * @return	The location in the source file where this width is needed.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
