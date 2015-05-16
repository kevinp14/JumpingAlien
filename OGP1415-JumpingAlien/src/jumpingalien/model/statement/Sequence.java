package jumpingalien.model.statement;

import java.util.List;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class Sequence implements Statement{
	
	private List<Statement> statements;
	private SourceLocation sourceLocation;
	
	public Sequence(List<Statement> statements, SourceLocation sourceLocation){
		this.statements = statements;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression condition) {
		BreakS testBreakS = new BreakS(null);
		for (Statement statement: this.statements) {
			if (statement.getClass() == testBreakS.getClass()){
				statement.execute(program, condition);
			}
			else{
				statement.execute(program, condition);
			}
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
