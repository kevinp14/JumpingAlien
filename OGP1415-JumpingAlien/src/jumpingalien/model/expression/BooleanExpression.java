package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class BooleanExpression {

	protected Expression expr1;
	protected Expression expr2;
	protected SourceLocation sourceLocation;
	
	public BooleanExpression(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation) {
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.sourceLocation = sourceLocation;
	}
	
	public Object and(Program program){
		boolean expr1Evaluated = (boolean) this.expr1.evaluate(program);
		boolean expr2Evaluated = (boolean) this.expr2.evaluate(program);
		return ((expr1Evaluated) && (expr2Evaluated));
	}
	
	public Object or(Program program){
		boolean expr1Evaluated = (boolean) this.expr1.evaluate(program);
		boolean expr2Evaluated = (boolean) this.expr2.evaluate(program);
		return ((expr1Evaluated) || (expr2Evaluated));
	}
}
