package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of to get the tile of a position.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class GetTile extends DoubleExpression implements Expression {
	
	/**
	 * Initialize the tile calculator.
	 * 
	 * @param	expr
	 * 			The game object for which the tile has to be calculated.
	 * @param	sourceLocation
	 * 			The location in the source file where this tile is needed.
	 */
	public GetTile(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the tile has to be evaluated.
	 * @return	The tile in which the given position is positioned.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		int positionX = (int) this.expr1.evaluate(null);
		int positionY = (int) this.expr2.evaluate(null);
		int tileLength = program.getGameObject().getWorld().getTileLength();
		int[] tile = new int[] { (positionX / tileLength), (positionY / tileLength) };
		return tile;
	}
	
	/**
	 * @param	program
	 * 			The program in which the tile has to be evaluated.
	 * @param	object
	 * 			The object for which the tile has to be evaluated.
	 * @return	The tile in which the given position is positioned.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		int[] tilePosition = (int[])object;
		int positionX = (int) tilePosition[0];
		int positionY = (int) tilePosition[1];
		int tileLength = program.getGameObject().getWorld().getTileLength();
		int[] tile = new int[] { (positionX / tileLength), (positionY / tileLength) };
		return tile;
	}

	/**
	 * @return	The location in the source file where this tile is needed.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
