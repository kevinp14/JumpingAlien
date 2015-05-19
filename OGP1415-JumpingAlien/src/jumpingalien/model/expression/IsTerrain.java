package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.model.type.BooleanType;
import jumpingalien.model.type.IntervalType;

public class IsTerrain implements Expression<BooleanType> {
	
	private Expression<IntervalType> expr;
	private SourceLocation sourceLocation;
	
	public IsTerrain(Expression<IntervalType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		IsAir air = new IsAir(this.expr, this.sourceLocation);
		boolean isAir = (boolean)air.evaluate(program);
		IsWater water = new IsWater(this.expr, this.sourceLocation);
		boolean isWater = (boolean)water.evaluate(program);
		IsMagma magma = new IsMagma(this.expr, this.sourceLocation);
		boolean isMagma = (boolean)magma.evaluate(program);
		IsPassable passable = new IsPassable(this.expr, this.sourceLocation);
		boolean isPassable = (boolean)passable.evaluate(program);
		return ((isAir) || (isWater) || (isMagma) || (!isPassable));
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}