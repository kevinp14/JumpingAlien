package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.type.DoubleType;
import jumpingalien.model.type.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class GetY implements Expression<DoubleType> {
	
	private Expression<GameObjectType> expr;
	private SourceLocation sourceLocation;
	
	public GetY(Expression<GameObjectType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expr.evaluate(program);
		int positionX = gameObject.getPosition()[1];
		return positionX;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}