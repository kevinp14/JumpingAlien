package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.type.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class GetHeight implements Expression<GameObjectType> {
	
	private Expression<GameObjectType> expression;
	private SourceLocation sourceLocation;
	
	public GetHeight(Expression<GameObjectType> expr, SourceLocation sourceLocation){
		this.expression = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expression.evaluate(program);
		int height = gameObject.getCurrentSprite().getHeight();
		return height;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
