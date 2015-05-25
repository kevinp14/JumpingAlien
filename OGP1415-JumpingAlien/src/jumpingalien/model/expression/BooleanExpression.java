package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of boolean expressions including "and" and "or".
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class BooleanExpression {

	protected Expression expr1;
	protected Expression expr2;
	protected SourceLocation sourceLocation;
	
	/**
	 * Initialize the boolean expression.
	 * 
	 * @param	expr1
	 * 			The first boolean of the expression.
	 * @param	expr2
	 * 			The second boolean of the expression.
	 * @param	sourceLocation
	 * 			The location in the source file where this boolean expression was called.
	 */
	public BooleanExpression(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation) {
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.sourceLocation = sourceLocation;
	}
	
	/**
	 * @param	program
	 * 			The program for which the "and" has to be evaluated.
	 * @return	The result of the "and" between expr1 and expr2.
	 */
	public Object and(Program program){
		if (this.expr1.evaluate(program) == null) {
			return ((boolean) this.expr2.evaluate(program));
		}
		if (this.expr2.evaluate(program) == null) {
			return ((boolean) this.expr1.evaluate(program));
		}
		else {
			boolean expr1Evaluated = (boolean) this.expr1.evaluate(program);
			boolean expr2Evaluated = (boolean) this.expr2.evaluate(program);
			return ((expr1Evaluated) && (expr2Evaluated));
		}
	}
	
	/**
	 * @param	program
	 * 			The program for which the "or" has to be evaluated.
	 * @return	The result of the "or" between expr1 and expr2.
	 */
	public Object or(Program program){
		if (this.expr1.evaluate(program) == null) {
			return ((boolean) this.expr2.evaluate(program));
		}
		if (this.expr2.evaluate(program) == null) {
			return ((boolean) this.expr1.evaluate(program));
		}
		else {
			boolean expr1Evaluated = (boolean) this.expr1.evaluate(program);
			boolean expr2Evaluated = (boolean) this.expr2.evaluate(program);
			return ((expr1Evaluated) || (expr2Evaluated));
		}
	}
}
