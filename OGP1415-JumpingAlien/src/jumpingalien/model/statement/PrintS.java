package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class PrintS implements Statement{
	
	private Expression value;
	private SourceLocation sourceLocation;
	
	public PrintS(Expression value, SourceLocation sourceLocation){
		this.value = value;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression condition) {
		double printValue = (double) this.value.evaluate(program);
		System.out.println(printValue);
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}