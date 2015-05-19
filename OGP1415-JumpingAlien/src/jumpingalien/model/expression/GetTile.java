package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class GetTile extends DoubleExpression implements Expression<DoubleType> {
	
	public GetTile(Expression<DoubleType> expr1, Expression<DoubleType> expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	@Override
	public Object evaluate(Program program) {
		int positionX = (int) this.expr1.evaluate(null);
		int positionY = (int) this.expr2.evaluate(null);
		int tileLength = program.getGameObject().getWorld().getTileLength();
		int[] tile = new int[] { (positionX / tileLength), (positionY / tileLength) };
		return tile;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
