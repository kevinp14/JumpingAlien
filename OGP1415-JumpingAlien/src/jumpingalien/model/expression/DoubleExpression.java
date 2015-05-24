package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Util;

/**
 * A class of double expressions including sum and, difference, multiplication, division and comparison.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class DoubleExpression {
	
	protected Expression expr1;
	protected Expression expr2;
	protected SourceLocation sourceLocation;
	
	/**
	 * Initialize the double expression.
	 * 
	 * @param	expr1
	 * 			The first double of the expression.
	 * @param	expr2
	 * 			The second double of the expression.
	 * @param	sourceLocation
	 * 			The location in the source file where this double expression was called.
	 */
	public DoubleExpression(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation){
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.sourceLocation = sourceLocation;
	}
	
	/**
	 * @param	program
	 * 			The program for which the sum has to be evaluated.
	 * @return	The result of the sum between expr1 and expr2.
	 */
	public Object sum(Program program){
		if (this.expr1 == null){
			return this.expr2.evaluate(program);
		}
		if (this.expr2.evaluate(program) == null){
			return this.expr1.evaluate(program);
		}
		else{
			double valueExpr1 = (double)this.expr1.evaluate(program);
			double valueExpr2 = (double)this.expr2.evaluate(program);
			return (valueExpr1 + valueExpr2);
		}
	}
	
	/**
	 * @param	program
	 * 			The program for which the difference has to be evaluated.
	 * @return	The result of the difference between expr1 and expr2.
	 */
	public Object difference(Program program){
		double valueExpr1 = (double)this.expr1.evaluate(program);
		double valueExpr2 = (double)this.expr2.evaluate(program);
		return (valueExpr1 - valueExpr2);
	}
	
	/**
	 * @param	program
	 * 			The program for which the multiplication has to be evaluated.
	 * @return	The result of the multiplication between expr1 and expr2.
	 */
	public Object multiplication(Program program){
		double valueExpr1 = (double)this.expr1.evaluate(program);
		double valueExpr2 = (double)this.expr2.evaluate(program);
		return (valueExpr1 * valueExpr2);
	}
	
	/**
	 * @param	program
	 * 			The program for which the division has to be evaluated.
	 * @return	The result of the division between expr1 and expr2.
	 */
	public Object division(Program program){
		double valueExpr1 = (double)this.expr1.evaluate(program);
		double valueExpr2 = (double)this.expr2.evaluate(program);
		return (valueExpr1 / valueExpr2);
	}
	
	/**
	 * @param	program
	 * 			The program for which the comparison has to be evaluated.
	 * @return	True if and only if expr1 is less than expr2.
	 */
	public boolean lessThan(Program program){
		return (!Util.fuzzyGreaterThanOrEqualTo((double)this.expr1.evaluate(program),
				(double)this.expr2.evaluate(program)));
	}
	
	/**
	 * @param	program
	 * 			The program for which the comparison has to be evaluated.
	 * @return	True if and only if expr1 is less than or equal to expr2.
	 */
	public boolean lessThanOrEqual(Program program){
		return (Util.fuzzyLessThanOrEqualTo((double)this.expr1.evaluate(program),
				(double)this.expr2.evaluate(program)));
	}
	
	/**
	 * @param	program
	 * 			The program for which the comparison has to be evaluated.
	 * @return	True if and only if expr1 is greater than expr2.
	 */
	public boolean greaterThan(Program program){
		return (!Util.fuzzyLessThanOrEqualTo((double)this.expr1.evaluate(program),
				(double)this.expr2.evaluate(program)));
	}
	
	/**
	 * @param	program
	 * 			The program for which the comparison has to be evaluated.
	 * @return	True if and only if expr1 is greater than or equal to expr2.
	 */
	public boolean greaterThanOrEqual(Program program){
		return (Util.fuzzyGreaterThanOrEqualTo((double)this.expr1.evaluate(program),
				(double)this.expr2.evaluate(program)));
	}
}






















