package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class GreaterThanOrEqual extends DoubleExpression implements Expression {
	
	public GreaterThanOrEqual(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	@Override
	public Object evaluate(Program program) {
		return this.greaterThanOrEqual();
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}