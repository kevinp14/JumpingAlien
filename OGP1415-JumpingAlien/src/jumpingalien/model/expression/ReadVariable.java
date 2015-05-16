package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class ReadVariable implements Expression{
	
	private String variableName;
	private SourceLocation sourceLocation;
	
	public ReadVariable(String variableName, Type<?> variableType,SourceLocation sourceLocation){
		this.variableName = variableName;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		return program.getObjectByName(this.variableName);
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
