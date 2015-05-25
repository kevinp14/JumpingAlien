package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to check if a tile is water.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class IsWater implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the is water checker.
	 * 
	 * @param	expr
	 * 			The tile that has to be checked.
	 * @param	sourceLocation
	 * 			The location in the source file where this checker was called.
	 */
	public IsWater(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @return	True if and only if the geological feature at the tile is 2.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		int[] tile = (int[])this.expr.evaluate(null);
		return (program.getGameObject().getWorld().getGeologicalFeature(tile[0], tile[1]) == 2);
	}
	
	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @param	object
	 * 			The object for which the checker has to be evaluated.
	 * @return	True if and only if the geological feature at the tile is 2.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		int[] tile = (int[])this.expr.evaluate(null);
		GameObject gameObject = (GameObject)object;
		return (gameObject.getWorld().getGeologicalFeature(tile[0], tile[1]) == 2);
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
