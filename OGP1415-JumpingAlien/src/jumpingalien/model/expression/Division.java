package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of division, to take the quotient of two doubles.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class Division extends DoubleExpression implements Expression {
	
	/**
	 * Initialize the division.
	 * 
	 * @param	expr1
	 * 			The first factor of the quotient.
	 * @param	expr2
	 * 			The second term of the quotient.
	 * @param	sourceLocation
	 * 			The location in the source file where this division was called.
	 */
	public Division(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the quotient has to be evaluated.
	 * @return	The result of the quotient.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.division(program);
	}
	
	/**
	 * @param	program
	 * 			The program in which the quotient has to be evaluated.
	 *  @param	object
	 * 			The object for which the quotient has to be evaluated.
	 * @return	The result of the quotient.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return false;
	}
	
	/**
	 * @return	The location in the source file where this division was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
}
