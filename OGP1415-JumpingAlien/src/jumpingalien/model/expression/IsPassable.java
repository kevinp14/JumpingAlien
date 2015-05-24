package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.World;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to check if a tile is passable.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class IsPassable implements Expression {

	private Expression expr;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the is passable checker.
	 * 
	 * @param	expr
	 * 			The tile that has to be checked.
	 * @param	sourceLocation
	 * 			The location in the source file where this checker was called.
	 */
	public IsPassable(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @return	True if and only if not the tile is not passable.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		int[] tile = (int[])this.expr.evaluate(program);
		World world = program.getGameObject().getWorld();
		return (!world.isNotPassable(world.getGeologicalFeature(tile[0], tile[1])));
	}

	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @param	object
	 * 			The object for which the checker has to be evaluated.
	 * @return	True if and only if not the tile is not passable.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		int[] tile = (int[])object;
		World world = program.getGameObject().getWorld();
		return (!world.isNotPassable(world.getGeologicalFeature(tile[0], tile[1])));
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
