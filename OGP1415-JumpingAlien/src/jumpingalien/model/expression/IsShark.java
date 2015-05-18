package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Shark;
import jumpingalien.model.Program;
import jumpingalien.model.type.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class IsShark implements Expression<GameObjectType> {
	
	private Expression<GameObjectType> expr;
	private SourceLocation sourceLocation;
	private Shark testShark = new Shark(0,0,null,null);
	
	public IsShark(Expression<GameObjectType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expr.evaluate(program);
		return (gameObject.getClass() == testShark.getClass());
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}