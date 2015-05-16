package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class IsTerrain implements Expression{
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	public IsTerrain(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		Object gameObject = this.expr.evaluate(program);
		return false;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}