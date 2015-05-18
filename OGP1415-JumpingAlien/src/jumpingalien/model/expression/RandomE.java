package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class RandomE implements Expression<DoubleType> {
	
	private Expression<DoubleType> maxValue;
	private SourceLocation sourceLocation;
	
	public RandomE(Expression<DoubleType> maxValue, SourceLocation sourceLocation){
		this.maxValue = maxValue;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		double maxValue = (double)this.maxValue.evaluate(program);
		return Math.random() * maxValue;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
