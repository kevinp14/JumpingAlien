package jumpingalien.model.expression;

import jumpingalien.model.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Util;

public class DoubleExpression {
	
	protected Expression<DoubleType> expr1;
	protected Expression<DoubleType> expr2;
	protected SourceLocation sourceLocation;
	
	public DoubleExpression(Expression<DoubleType> expr1, Expression<DoubleType> expr2, 
			SourceLocation sourceLocation){
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.sourceLocation = sourceLocation;
	}
	
	public Object sum(){
		String testString = "test";
		if (this.expr1.evaluate(null).getClass() == testString.getClass()){
			String valueExpr1 = (String)this.expr1.evaluate(null);
			String valueExpr2 = (String)this.expr2.evaluate(null);
			return (valueExpr1 + valueExpr2);
		}
		else{
			double valueExpr1 = (double)this.expr1.evaluate(null);
			double valueExpr2 = (double)this.expr2.evaluate(null);
			return (valueExpr1 + valueExpr2);
		}
	}
	
	public Object difference(){
		double valueExpr1 = (double)this.expr1.evaluate(null);
		double valueExpr2 = (double)this.expr2.evaluate(null);
		return (valueExpr1 - valueExpr2);
	}
	
	public Object multiplication(){
		double valueExpr1 = (double)this.expr1.evaluate(null);
		double valueExpr2 = (double)this.expr2.evaluate(null);
		return (valueExpr1 * valueExpr2);
	}
	
	public Object division(){
		double valueExpr1 = (double)this.expr1.evaluate(null);
		double valueExpr2 = (double)this.expr2.evaluate(null);
		return (valueExpr1 / valueExpr2);
	}
	
	public boolean lessThan(){
		return (!Util.fuzzyGreaterThanOrEqualTo((double)this.expr1.evaluate(null),
				(double)this.expr2.evaluate(null)));
	}
	
	public boolean lessThanOrEqual(){
		return (Util.fuzzyLessThanOrEqualTo((double)this.expr1.evaluate(null),
				(double)this.expr2.evaluate(null)));
	}
	
	public boolean greaterThan(){
		return (!Util.fuzzyLessThanOrEqualTo((double)this.expr1.evaluate(null),
				(double)this.expr2.evaluate(null)));
	}
	
	public boolean greaterThanOrEqual(){
		return (Util.fuzzyGreaterThanOrEqualTo((double)this.expr1.evaluate(null),
				(double)this.expr2.evaluate(null)));
	}
}






















