package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to skip/wait for 0.001 seconds.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class SkipS implements Statement {
	
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the skip statement.
	 * 
	 * @param	sourceLocation
	 * 			The location in the source file where this skip statement was called.
	 */
	public SkipS(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the skip statement has to be executed.
	 * @param	condition
	 * 			The condition under which the skip statement has to be executed.
	 * @effect	Wait for 0.001 seconds.
	 * 			| long duration = (long)0.001
	 * 			| program.wait(duration)
	 */
	@Override
	public void execute(Program program, Expression condition) {
		long duration = (long)0.001;
		try {
			program.wait(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return	The location in the source file where this skip statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
