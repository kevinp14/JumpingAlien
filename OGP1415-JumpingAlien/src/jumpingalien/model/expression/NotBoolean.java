package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class NotBoolean implements Expression<BooleanType> {
	
	private Expression<BooleanType> expr;
	private SourceLocation sourceLocation;
	
	public NotBoolean(Expression<BooleanType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		boolean valueExpr = (boolean)this.expr.evaluate(null);
		return (!valueExpr);
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
