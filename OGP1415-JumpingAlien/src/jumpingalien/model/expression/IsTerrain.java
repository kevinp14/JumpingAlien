package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to check if a tile is terrain.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class IsTerrain implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the is terrain checker.
	 * 
	 * @param	expr
	 * 			The tile that has to be checked.
	 * @param	sourceLocation
	 * 			The location in the source file where this checker was called.
	 */
	public IsTerrain(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}
	
	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @return	True if and only if the tile is air, water, magma or not passable.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		IsAir air = new IsAir(this.expr, this.sourceLocation);
		boolean isAir = (boolean)air.evaluate(program);
		IsWater water = new IsWater(this.expr, this.sourceLocation);
		boolean isWater = (boolean)water.evaluate(program);
		IsMagma magma = new IsMagma(this.expr, this.sourceLocation);
		boolean isMagma = (boolean)magma.evaluate(program);
		IsPassable passable = new IsPassable(this.expr, this.sourceLocation);
		boolean isPassable = (boolean)passable.evaluate(program);
		return ((isAir) || (isWater) || (isMagma) || (!isPassable));
	}

	/**
	 * @param	program
	 * 			The program in which the checker has to be evaluated.
	 * @param	object
	 * 			The object for which the checker has to be evaluated.
	 * @return	True if and only if the tile is air, water, magma or not passable.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		IsAir air = new IsAir(this.expr, this.sourceLocation);
		boolean isAir = (boolean)air.evaluateForGivenObject(program, object);
		IsWater water = new IsWater(this.expr, this.sourceLocation);
		boolean isWater = (boolean)water.evaluateForGivenObject(program, object);
		IsMagma magma = new IsMagma(this.expr, this.sourceLocation);
		boolean isMagma = (boolean)magma.evaluateForGivenObject(program, object);
		IsPassable passable = new IsPassable(this.expr, this.sourceLocation);
		boolean isPassable = (boolean)passable.evaluateForGivenObject(program, object);
		return ((isAir) || (isWater) || (isMagma) || (!isPassable));
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