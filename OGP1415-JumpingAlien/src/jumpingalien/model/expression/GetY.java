package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class GetY implements Expression{
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	public GetY(Expression expr, SourceLocation sourceLocation){
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