package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of and expressions, to take the "and" of two booleans.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class AndE extends BooleanExpression implements Expression {
	
	/**
	 * Initialize the "and".
	 * 
	 * @param	expr1
	 * 			The first boolean of the "and".
	 * @param	expr2
	 * 			The second boolean of the "and".
	 * @param	sourceLocation
	 * 			The location in the source file where this "and" was called.
	 */
	public AndE(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the "and" has to be evaluated.
	 * @return	The result of the "and".
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.and(program);
	}
	
	/**
	 * @param	program
	 * 			The program in which the "and" has to be evaluated.
	 * @param	object
	 * 			The object for which the "and" has to be evaluated.
	 * @return	The result of the "and".
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return this.and(program);
	}

	/**
	 * @return	The location in the source file where this "and" was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
}
