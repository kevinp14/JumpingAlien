	package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of and expressions, to take the "or" of two booleans.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class OrE extends BooleanExpression implements Expression {
	
	/**
	 * Initialize the "or".
	 * 
	 * @param	expr1
	 * 			The first boolean of the "or".
	 * @param	expr2
	 * 			The second boolean of the "or".
	 * @param	sourceLocation
	 * 			The location in the source file where this "or" was called.
	 */
	public OrE(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the "or" has to be evaluated.
	 * @return	The result of the "or".
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.or(program);
	}
	
	/**
	 * @param	program
	 * 			The program in which the "or" has to be evaluated.
	 * @param	object
	 * 			The object for which the "or" has to be evaluated.
	 * @return	The result of the "or".
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		this.expr1 = (Expression)object;
		return this.or(program);
	}
	
	/**
	 * @return	The location in the source file where this "or" was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
