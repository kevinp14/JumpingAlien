package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class SqrtE implements Expression{
	
	private Expression expr;
	private SourceLocation sourceLocation;

	public SqrtE(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		double valueExpr = (double)expr.evaluate(program);
		return Math.pow(valueExpr, 2);
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
