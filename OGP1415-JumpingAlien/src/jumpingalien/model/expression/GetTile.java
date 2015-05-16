package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class GetTile extends DoubleExpression implements Expression{
	
	public GetTile(Expression expr1, Expression expr2, SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	@Override
	public Object evaluate(Program program) {
		int positionX = (int) this.expr1.evaluate(null);
		int positionY = (int) this.expr2.evaluate(null);
		return program.getGameObject().getWorld().getBottomLeftPixelOfTile(positionX, positionY);
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
