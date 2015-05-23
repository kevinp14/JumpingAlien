package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to check if two expression are not equal.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class NotEquals extends GeneralExpression implements Expression {
	
	/**
	 * Initialize the not equals.
	 * 
	 * @param	expr1
	 * 			The first expression.
	 * @param	expr2
	 * 			The second expression.
	 * @param	sourceLocation
	 * 			The location in the source file where this not equals was called.
	 */
	public NotEquals(Expression expr1, Expression expr2, SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the not equals has to be evaluated.
	 * @return	The result of the not equals.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.notEquals();
	}
	
	/**
	 * @param	program
	 * 			The program in which the not equals has to be evaluated.
	 * @param	program
	 * 			The program in which the not equals has to be evaluated.
	 * @return	The result of the not equals.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return false;
	}

	/**
	 * @return	The location in the source file where this not equals was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
