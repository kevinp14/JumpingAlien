package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * An interface of statements.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public interface Statement {
	
	/**
	 * Execute the statement.
	 * 
	 * @param	program
	 * 			The program in which the statement has to be executed.
	 * @param	condition
	 * 			The condition under which the statement has to be executed.
	 */
	void execute(Program program, Expression condition);
	
	/**
	 * Execute the statement.
	 * 
	 * @param	program
	 * 			The program in which the statement has to be executed.
	 * @param	condition
	 * 			The condition under which the statement has to be executed.
	 * @param	object
	 * 			The object for which the statement has to be executed.
	 */
	void executeForGivenObject(Program program, Expression condition, Object object);
	
	/**
	 * @return	The location in the source file where this statement was called.
	 * 
	 */
	SourceLocation getSourceLocation();
}
