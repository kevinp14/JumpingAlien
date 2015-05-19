package jumpingalien.model.statement;

import java.util.List;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.type.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class Sequence implements Statement{
	
	private List<Statement> statements;
	private SourceLocation sourceLocation;
	
	public Sequence(List<Statement> statements, SourceLocation sourceLocation){
		this.statements = statements;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression<BooleanType> condition) {
		for (Statement statement: this.statements) {
			statement.execute(program, condition);
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
