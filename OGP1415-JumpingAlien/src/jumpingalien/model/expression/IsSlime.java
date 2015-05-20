package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.Slime;
import jumpingalien.part3.programs.SourceLocation;

public class IsSlime implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	private Slime testSlime = new Slime(0,0,null,null, null);
	
	public IsSlime(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.expr.evaluate(program);
		return (gameObject.getClass() == testSlime.getClass());
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}