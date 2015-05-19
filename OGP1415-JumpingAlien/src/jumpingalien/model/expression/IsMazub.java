package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Program;
import jumpingalien.model.type.BooleanType;
import jumpingalien.model.type.GameObjectType;
import jumpingalien.part3.programs.SourceLocation;

public class IsMazub implements Expression<BooleanType> {
	
	private Expression<GameObjectType> expr;
	private SourceLocation sourceLocation;
	private Mazub testMazub = new Mazub(0,0,null,null);
	
	public IsMazub(Expression<GameObjectType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expr.evaluate(program);
		return (gameObject.getClass() == testMazub.getClass());
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
