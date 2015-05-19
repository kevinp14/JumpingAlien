package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class OrE extends BooleanExpression implements Expression<BooleanType> {
	
	public OrE(Expression<BooleanType> expr1, Expression<BooleanType> expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	@Override
	public Object evaluate(Program program) {
		return this.or(program);
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
