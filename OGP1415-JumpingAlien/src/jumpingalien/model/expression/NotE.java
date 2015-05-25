package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to get the "not" of an expression.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class NotE implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the "not".
	 * 
	 * @param	expr
	 * 			The expression to get the "not" of.
	 * @param	sourceLocation
	 * 			The location in the source file where this "not" was called.
	 */
	public NotE(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the "not" has to be evaluated.
	 * @return	True if and only if not valueExpr.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		if (this.expr.evaluate(program) == null) {
			return null;
		}
		boolean valueExpr = (boolean)this.expr.evaluate(program);
		return (!valueExpr);
	}
	
	/**
	 * @param	program
	 * 			The program in which the "not" has to be evaluated.
	 * @param	object
	 * 			The object in which the "not" has to be evaluated.
	 * @return	True if and only if not valueExpr.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		boolean valueExpr = (boolean)this.expr.evaluate(program);
		return (!valueExpr);
	}
	
	/**
	 * @return	The location in the source file where this "not" was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
