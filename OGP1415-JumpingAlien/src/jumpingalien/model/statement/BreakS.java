package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.expression.NotE;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to immediately terminate a while- or for-loop.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class BreakS implements Statement {
	
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the break statement.
	 * 
	 * @param	sourceLocation
	 * 			The location in the source file where this break statement was called.
	 */
	public BreakS(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the break statement has to be executed.
	 * @param	condition
	 * 			The condition under which the break statement has to be executed.
	 * @post	The given condition is set to the contrary of the condition.
	 * 			| Expression notCondition = new NotE(condition, this.sourceLocation)
	 * 			| condition = notCondition;
	 */
	@Override
	public void execute(Program program, Expression condition) {
		SourceLocation srcLoc = condition.getSourceLocation();
		this.sourceLocation = srcLoc;
		Expression notCondition = new NotE(condition, this.sourceLocation);
		condition = notCondition;
	}
	
	/**
	 * @param	program
	 * 			The program in which the break statement has to be executed.
	 * @param	condition
	 * 			The condition under which the break statement has to be executed.
	 * @param	object
	 * 			The object for which the break statement has to be executed.
	 * @post	The given condition is set to the contrary of the condition.
	 * 			| Expression notCondition = new NotE(condition, this.sourceLocation)
	 * 			| condition = notCondition;
	 */
	@Override
	public void executeForGivenObject(Program program, Expression condition,
			Object object) {
		SourceLocation srcLoc = (SourceLocation)object;
		this.sourceLocation = srcLoc;
		Expression notCondition = new NotE(condition, this.sourceLocation);
		condition = notCondition;
	}

	/**
	 * @return	The location in the source file where this break statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
