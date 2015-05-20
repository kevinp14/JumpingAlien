package jumpingalien.model.expression;

import jumpingalien.part3.programs.SourceLocation;

public class GeneralExpression {

	protected Expression expr1;
	protected Expression expr2;
	protected SourceLocation sourceLocation;
	
	public GeneralExpression(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation) {
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.sourceLocation = sourceLocation;
	}
	
	public boolean equals(){
		return (this.expr1.evaluate(null) == this.expr2.evaluate(null));
	}
	
	public boolean notEquals(){
		return (this.expr1.evaluate(null) != this.expr2.evaluate(null));
	}

}
