package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to wait for a given amount of seconds.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class WaitS implements Statement {
	
	private Expression duration;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the wait statement.
	 * 
	 * @param	duration
	 * 			The amount of seconds the program should wait.
	 * @param	sourceLocation
	 * 			The location in the source file where this wait statement was called.
	 */
	public WaitS(Expression duration, SourceLocation sourceLocation){
		this.duration = duration;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the wait statement has to be executed.
	 * @param	condition
	 * 			The condition under which the wait statement has to be executed.
	 * @effect	Wait for 0.001 seconds.
	 * 			| long dur = this.duration.evaluate(program)
	 * 			| program.wait(dur)
	 */
	@Override
	public void execute(Program program, Expression condition) {
		long dur = (long) this.duration.evaluate(program);
		try {
			program.wait(dur);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return	The location in the source file where this wait statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	@Override
	public void executeForGivenObject(Program program, Expression condition,
			Object object) {
		if (this.duration.evaluate(program) != null){
			double dur = (double) this.duration.evaluate(program);
			long time = (long) dur;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {}
		}
	}

}
