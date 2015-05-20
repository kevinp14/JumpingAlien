package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class Equals extends GeneralExpression implements Expression {
	
	public Equals(Expression expr1, Expression expr2, SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	@Override
	public Object evaluate(Program program) {
		return this.equals();
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
