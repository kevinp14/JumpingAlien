package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class WaitS implements Statement{
	
	private Expression duration;
	private SourceLocation sourceLocation;
	
	public WaitS(Expression duration, SourceLocation sourceLocation){
		this.duration = duration;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression condition) {
		long dur = (long) this.duration.evaluate(program);
		try {
			program.wait(dur);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
