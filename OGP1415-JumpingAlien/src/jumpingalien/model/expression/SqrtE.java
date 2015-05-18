package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class SqrtE implements Expression<DoubleType> {
	
	private Expression<DoubleType> expr;
	private SourceLocation sourceLocation;

	public SqrtE(Expression<DoubleType> expr, SourceLocation sourceLocation){
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
