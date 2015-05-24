package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of to get the number of hitpoints of a game object.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class GetHP implements Expression {
	
	private Expression expression;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the number of hitpoints calculator.
	 * 
	 * @param	expr
	 * 			The game object for which the number of hitpoints has to be calculated.
	 * @param	sourceLocation
	 * 			The location in the source file where this number of hitpoints is needed.
	 */
	public GetHP(Expression expr, SourceLocation sourceLocation){
		this.expression = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the number of hitpoints has to be evaluated.
	 * @return	The number of hitpoints of the given game object.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expression.evaluate(program);
		double hitPoints = (double)gameObject.getNbHitPoints();
		return hitPoints;
	}
	
	/**
	 * @param	program
	 * 			The program in which the number of hitpoints has to be evaluated.
	 * @param	object
	 * 			The object for which the number of hitpoints has to be evaluated.
	 * @return	The number of hitpoints of the given game object.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		GameObject gameObject = (GameObject) object;
		double hitPoints = (double)gameObject.getNbHitPoints();
		return hitPoints;
	}

	/**
	 * @return	The location in the source file where this number of hitpoints is needed.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
