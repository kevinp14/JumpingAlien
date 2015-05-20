package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Addition extends DoubleExpression implements Expression {
	
	public Addition(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	@Override
	public Object evaluate(Program program) {
		return this.sum();
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
