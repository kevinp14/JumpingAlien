package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of to check if two expressions are equal.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class Equals extends GeneralExpression implements Expression {
	
	/**
	 * Initialize the equal checker.
	 * 
	 * @param	expr1
	 * 			The first expression.
	 * @param	expr2
	 * 			The second expression.
	 * @param	sourceLocation
	 * 			The location in the source file where this equal checker was called.
	 */
	public Equals(Expression expr1, Expression expr2, SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the equal checker has to be evaluated.
	 * @return	The result of the equal checker.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.equals(program);
	}
	
	/**
	 * @param	program
	 * 			The program in which the equal checker has to be evaluated.
	 * @param	object
	 * 			The object for which the equal checker has to be evaluated.
	 * @return	The result of the equal checker.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return false;
	}
	
	/**
	 * @return	The location in the source file where this addition was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
