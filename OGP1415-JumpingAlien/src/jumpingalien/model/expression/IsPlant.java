package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to check if a game object is of type Plant.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mari�n (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class IsPlant implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the is plant checker.
	 * 
	 * @param	expr
	 * 			The game object that has to be checked.
	 * @param	sourceLocation
	 * 			The location in the source file where this checker was called.
	 */
	public IsPlant(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @return	True if and only if the list of plants in the world of the given program contains this
	 * 			game object.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expr.evaluate(program);
		return (gameObject.getWorld().getPlants().contains(gameObject));
	}

	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @param	object
	 * 			The object for which the checker has to be evaluated.
	 * @return	True if and only if the list of plants in the world of the given program contains this
	 * 			game object.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		GameObject gameObject = (GameObject) object;
		return (gameObject.getWorld().getPlants().contains(gameObject));
	}
	
	/**
	 * @return	The location in the source file where this checker was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}