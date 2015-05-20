package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class SkipS implements Statement {
	
	private SourceLocation sourceLocation;
	
	public SkipS(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression condition) {
		long duration = (long)0.001;
		try {
			program.wait(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
