package jumpingalien.model.statement;

import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.type.BooleanType;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;

public class ForEach implements Statement{
	
	private String variableName;
	private Kind variableKind;
	private Expression<BooleanType> where;
	private Expression<BooleanType> sort;
	private SortDirection sortDirection;
	private Statement body;
	private SourceLocation sourceLocation;
	
	public ForEach(String variableName, Kind variableKind,
			Expression<BooleanType> where, Expression<BooleanType> sort, SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		this.variableName = variableName;
		this.variableKind = variableKind;
		this.where = where;
		this.sort = sort;
		this.sortDirection = sortDirection;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression<BooleanType> condition) {
		World world = program.getGameObject().getWorld();
		if (((boolean)this.sort.evaluate(program)) 
				&& (this.sortDirection == SortDirection.ASCENDING)) {
			if (this.variableKind == Kind.PLANT) {
				for (Plant plant: world.getPlants()) {
					body.execute(program, this.where);
				}
			}
			else if (this.variableKind == Kind.SHARK) {
				for (Shark shark: world.getSharks()) {
					body.execute(program, this.where);
				}
			}
			else if (this.variableKind == Kind.SLIME) {
				for (Slime slime: world.getSlimes()) {
					body.execute(program, this.where);
				}
			}
		}
		else if (((boolean)this.sort.evaluate(program)) 
				&& (this.sortDirection == SortDirection.DESCENDING)) {
			if (this.variableKind == Kind.PLANT) {
				for (Plant plant: world.getPlants()) {
					body.execute(program, this.where);
				}
			}
			else if (this.variableKind == Kind.SHARK) {
				for (Shark shark: world.getSharks()) {
					body.execute(program, this.where);
				}
			}
			else if (this.variableKind == Kind.SLIME) {
				for (Slime slime: world.getSlimes()) {
					body.execute(program, this.where);
				}
			}
		}
		else {
			if (this.variableKind == Kind.PLANT) {
				for (Plant plant: world.getPlants()) {
					body.execute(program, this.where);
				}
			}
			else if (this.variableKind == Kind.SHARK) {
				for (Shark shark: world.getSharks()) {
					body.execute(program, this.where);
				}
			}
			else if (this.variableKind == Kind.SLIME) {
				for (Slime slime: world.getSlimes()) {
					body.execute(program, this.where);
				}
			}
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
}
