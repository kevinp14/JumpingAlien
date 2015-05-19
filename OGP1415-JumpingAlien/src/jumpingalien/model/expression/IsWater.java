package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.BooleanType;
import jumpingalien.model.type.IntervalType;
import jumpingalien.part3.programs.SourceLocation;

public class IsWater implements Expression<BooleanType> {
	
	private Expression<IntervalType> expr;
	private SourceLocation sourceLocation;
	
	public IsWater(Expression<IntervalType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		int object = (int)this.expr.evaluate(null);
		return (object == 2);
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
