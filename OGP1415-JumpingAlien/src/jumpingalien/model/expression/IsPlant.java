package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.type.BooleanType;
import jumpingalien.model.type.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class IsPlant implements Expression<BooleanType> {
	
	private Expression<GameObjectType> expr;
	private SourceLocation sourceLocation;
	private Plant testPlant = new Plant(0,0,null,null);
	
	public IsPlant(Expression<GameObjectType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expr.evaluate(program);
		return (gameObject.getClass() == testPlant.getClass());
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}