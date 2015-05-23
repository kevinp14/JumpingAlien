package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * An interface of expressions.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public interface Expression {
	
	/**
	 * Evaluate the expression
	 * 
	 * @param	program
	 * 			The program for which the expression has to be evaluated.
	 * @return	The result of the evaluation.
	 */
	Object evaluate(Program program);
	
	/**
	 * Evaluate the expression
	 * 
	 * @param	program
	 * 			The program for which the expression has to be evaluated.
	 * @param	object
	 * 			The object for which the expression has to be evaluated.
	 * @return	The result of the evaluation.
	 */
	Object evaluateForGivenObject(Program program, Object object);


	/**
	 * @return	The location in the source file where this expression was called/created.
	 * 
	 * 
	 */
	SourceLocation getSourceLocation();
}

