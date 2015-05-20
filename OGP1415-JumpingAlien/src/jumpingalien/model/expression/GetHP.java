package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class GetHP implements Expression {
	
	private Expression expression;
	private SourceLocation sourceLocation;
	
	public GetHP(Expression expr, SourceLocation sourceLocation){
		this.expression = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expression.evaluate(program);
		int hitPoints = gameObject.getNbHitPoints();
		return hitPoints;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
