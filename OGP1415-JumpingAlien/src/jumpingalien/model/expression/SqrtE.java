package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to get the square root of an expression.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class SqrtE implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;

	/**
	 * Initialize the square root.
	 * 
	 * @param	expr
	 * 			The expression to get the square root of.
	 * @param	sourceLocation
	 * 			The location in the source file where this square root was called.
	 */
	public SqrtE(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the square root has to be evaluated.
	 * @return	The square root of the given expression.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		double valueExpr = (double)expr.evaluate(program);
		return Math.pow(valueExpr, 0.5);
	}

	/**
	 * @param	program
	 * 			The program in which the square root has to be evaluated.
	 * @param	object
	 * 			The object for which the square root has to be evaluated.
	 * @return	The square root of the given expression.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		double valueExpr = (double)object;
		return Math.pow(valueExpr, 0.5);
	}
	
	/**
	 * @return	The location in the source file where this square root was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
