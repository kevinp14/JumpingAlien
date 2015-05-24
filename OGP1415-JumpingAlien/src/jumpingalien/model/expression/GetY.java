package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of to get the y-position of a game object.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class GetY implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the y-position calculator.
	 * 
	 * @param	expr
	 * 			The game object for which the y-position has to be calculated.
	 * @param	sourceLocation
	 * 			The location in the source file where this y-position is needed.
	 */
	public GetY(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the y-position has to be evaluated.
	 * @return	The y-position of the given game object.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expr.evaluate(program);
		int positionX = gameObject.getPosition()[1];
		return positionX;
	}
	
	/**
	 * @param	program
	 * 			The program in which the y-position has to be evaluated.
	 * @param	object
	 * 			The object for which the y-position has to be evaluated.
	 * @return	The y-position of the given game object.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		GameObject gameObject = (GameObject) object;
		int positionX = gameObject.getPosition()[1];
		return positionX;
	}

	/**
	 * @return	The location in the source file where this y-position is needed.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}