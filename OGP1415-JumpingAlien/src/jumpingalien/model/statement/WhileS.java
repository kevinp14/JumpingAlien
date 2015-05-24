package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to make a while statement.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class WhileS implements Statement {
	
	private Expression condition;
	private Statement body;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the while statement.
	 * 
	 * @param	condition
	 * 			The condition under which the body should be executed.
	 * @param	body
	 * 			The statement that has to be executed while the given condition is true.
	 * @param	sourceLocation
	 * 			The location in the source file where this while statement was called.
	 */
	public WhileS(Expression condition, Statement body, SourceLocation sourceLocation){
		this.condition = condition;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the while statement has to be executed.
	 * @param	condition
	 * 			The condition under which the while statement has to be executed.
	 * @effect	While the given condition is true, the given body is executed.
	 * 			| boolean cond = (boolean) this.condition.evaluate(program)
	 * 			| while (cond)
	 * 			|	this.body.execute(program, condition)
	 */
	@Override
	public void execute(Program program, Expression condition) {
		while ((boolean) this.condition.evaluate(program)){
			body.execute(program, this.condition);
		}
	}
	
	/**
	 * @param	program
	 * 			The program in which the while statement has to be executed.
	 * @param	condition
	 * 			The condition under which the while statement has to be executed.
	 * @param	object
	 * 			The object for which the while statement has to be executed.
	 * @effect	While the given condition is true, the given body is executed.
	 * 			| boolean cond = (boolean) this.condition.evaluate(program)
	 * 			| while (cond)
	 * 			|	this.body.execute(program, condition)
	 */
	@Override
	public void executeForGivenObject(Program program, Expression condition,
			Object object) {
		while ((boolean) this.condition.evaluateForGivenObject(program, object)){
			body.executeForGivenObject(program, this.condition, object);
		}
	}

	/**
	 * @return	The location in the source file where this while statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
}
