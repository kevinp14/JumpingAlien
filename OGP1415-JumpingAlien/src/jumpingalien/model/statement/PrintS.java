package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to print out an expression.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class PrintS implements Statement {
	
	private Expression value;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the print statement.
	 * 
	 * @param	value
	 * 			The value that needs to be printed.
	 * @param	sourceLocation
	 * 			The location in the source file where this print statement was called.
	 */
	public PrintS(Expression value, SourceLocation sourceLocation){
		this.value = value;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the print statement has to be executed.
	 * @param	condition
	 * 			The condition under which the print statement has to be executed.
	 * @effect	The given value is printed out.
	 * 			| double printValue = (double) this.value.evaluate(program)
	 * 			| System.out.println(printValue)
	 */
	@Override
	public void execute(Program program, Expression condition) {
		double printValue = (double) this.value.evaluate(program);
		System.out.println(printValue);
	}

	/**
	 * @return	The location in the source file where this print statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}