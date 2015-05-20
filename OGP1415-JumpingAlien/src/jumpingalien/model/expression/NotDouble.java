package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class NotDouble implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	public NotDouble(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		double valueExpr = (double)this.expr.evaluate(null);
		return (-valueExpr);
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
