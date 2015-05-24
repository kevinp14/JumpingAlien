package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of to get the x-position of a game object.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class GetX implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the x-position calculator.
	 * 
	 * @param	expr
	 * 			The game object for which the x-position has to be calculated.
	 * @param	sourceLocation
	 * 			The location in the source file where this x-position is needed.
	 */
	public GetX(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the x-position has to be evaluated.
	 * @return	The x-position of the given game object.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expr.evaluate(program);
		double positionX = (double)gameObject.getPosition()[0];
		return positionX;
	}
	
	/**
	 * @param	program
	 * 			The program in which the x-position has to be evaluated.
	 * @param	object
	 * 			The object for which the x-position has to be evaluated.
	 * @return	The x-position of the given game object.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		GameObject gameObject = (GameObject) object;
		double positionX = (double)gameObject.getPosition()[0];
		return positionX;
	}

	/**
	 * @return	The location in the source file where this x-position is needed.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
