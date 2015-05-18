package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class DoubleConstant implements Expression<DoubleType> {
	
	private double var;
	private SourceLocation sourceLocation;
	
	public DoubleConstant(double var, SourceLocation sourceLocation){
		this.var = var;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		return this.var;
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
}
