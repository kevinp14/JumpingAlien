package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Shark;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class IsShark implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	private Shark testShark = new Shark(0,0,null,null);
	
	public IsShark(Expression expr, SourceLocation sourceLocation){
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