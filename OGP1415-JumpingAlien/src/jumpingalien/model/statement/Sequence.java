package jumpingalien.model.statement;

import java.util.List;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to execute a sequence of statements.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class Sequence implements Statement {
	
	private List<Statement> statements;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the print statement.
	 * 
	 * @param	statements
	 * 			A list/sequence of statements.
	 * @param	sourceLocation
	 * 			The location in the source file where this sequence of statements was called.
	 */
	public Sequence(List<Statement> statements, SourceLocation sourceLocation){
		this.statements = statements;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the sequence of statements has to be executed.
	 * @param	condition
	 * 			The condition under which the sequence of statements has to be executed.
	 * @effect	Every statement in the sequence is executed.
	 * 			| for (Statement statement: this.statements)
	 * 			|	statement.execute(program, condition)
	 */
	@Override
	public void execute(Program program, Expression condition) {
		for (Statement statement: this.statements) {
			statement.execute(program, condition);
		}
	}

	/**
	 * @return	The location in the source file where this sequence of statements was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
