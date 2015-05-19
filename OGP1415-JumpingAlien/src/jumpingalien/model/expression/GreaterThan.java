package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.BooleanType;
import jumpingalien.model.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class GreaterThan extends DoubleExpression implements Expression<BooleanType> {
	
	public GreaterThan(Expression<DoubleType> expr1, Expression<DoubleType> expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	@Override
	public Object evaluate(Program program) {
		return this.greaterThan();
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}