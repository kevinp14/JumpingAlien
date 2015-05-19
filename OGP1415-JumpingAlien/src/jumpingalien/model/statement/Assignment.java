package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.type.BooleanType;
import jumpingalien.model.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class Assignment implements Statement{
	
	private String variableName;
	private Expression<?> value;
	private SourceLocation sourceLocation;
	
	public Assignment(String variableName, Type<?> variableType,
	Expression<?> value, SourceLocation sourceLocation){
		this.variableName = variableName;
		this.value = value;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression<BooleanType> condition) {
		Object newValue = this.value.evaluate(program);
		program.setObjectByName(this.variableName, newValue);
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
