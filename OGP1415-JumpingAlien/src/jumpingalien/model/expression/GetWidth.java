package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.type.DoubleType;
import jumpingalien.model.type.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class GetWidth implements Expression<DoubleType> {
	
	private Expression<GameObjectType> expression;
	private SourceLocation sourceLocation;
	
	public GetWidth(Expression<GameObjectType> expr, SourceLocation sourceLocation){
		this.expression = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expression.evaluate(program);
		int width = gameObject.getCurrentSprite().getWidth();
		return width;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
