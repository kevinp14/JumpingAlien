package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.type.BooleanType;
import jumpingalien.model.type.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class IsDead implements Expression<BooleanType> {
	
	private Expression<GameObjectType> expr;
	private SourceLocation sourceLocation;
	
	public IsDead(Expression<GameObjectType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expr.evaluate(program);
		return (gameObject.isDead());
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
