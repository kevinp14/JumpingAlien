package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class IsJumping implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	public IsJumping(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) expr.evaluate(program);
		return (gameObject.isJumping());
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
