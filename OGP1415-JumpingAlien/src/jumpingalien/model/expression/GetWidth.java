package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class GetWidth implements Expression {
	
	private Expression expression;
	private SourceLocation sourceLocation;
	
	public GetWidth(Expression expr, SourceLocation sourceLocation){
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
