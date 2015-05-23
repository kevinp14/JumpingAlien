package jumpingalien.model.statement;

import java.util.ArrayList;
import java.util.Comparator;

import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.GameObject;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;
//TODO commentaar
public class ForEach implements Statement {
	
	private String variableName;
	private Kind variableKind;
	private Expression where;
	private Expression sortExpression;
	private SortDirection sortDirection;
	private Statement body;
	private SourceLocation sourceLocation;
	
	public ForEach(String variableName, Kind variableKind,
			Expression where, Expression sortExpression, SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		this.variableName = variableName;
		this.variableKind = variableKind;
		this.where = where;
		this.sortExpression = sortExpression;
		this.sortDirection = sortDirection;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}
	
	@Override
	public void execute(Program program, Expression condition) {
		World world = program.getGameObject().getWorld();
		ArrayList<Plant> plants = world.getPlants();
		ArrayList<Shark> sharks = world.getSharks();
		ArrayList<Slime> slimes = world.getSlimes();
		ArrayList<GameObject> allGameObjects = new ArrayList<GameObject>();
		allGameObjects.addAll(plants);
		allGameObjects.addAll(sharks);
		allGameObjects.addAll(slimes);
		allGameObjects.add(world.getMazub());
		allGameObjects.add(world.getBuzam());
		Comparator<GameObject> ascendingComparator = new Comparator<GameObject>() {
			public int compare(GameObject gameObject1, GameObject gameObject2){
				int a = ((int)ForEach.this.sortExpression.evaluateForGivenObject(program, gameObject1) 
						- (int)ForEach.this.sortExpression.evaluateForGivenObject(program, gameObject2));
				if (a < 0) {
					return -1;
				}
				if (a > 0)
					return 1;
				else {
					return 0;
				}
			}
		};
		Comparator<GameObject> descendingComparator = new Comparator<GameObject>() {
			public int compare(GameObject gameObject1, GameObject gameObject2){
				int a = ((int)ForEach.this.sortExpression.evaluateForGivenObject(program, gameObject2) 
						- (int)ForEach.this.sortExpression.evaluateForGivenObject(program, gameObject1));
				if (a < 0) {
					return -1;
				}
				if (a > 0)
					return 1;
				else {
					return 0;
				}
			}
		};
		if (this.variableKind == Kind.MAZUB) {
			body.evaluateForGivenObject(program, world.getMazub());
		}
		if (this.variableKind == Kind.BUZAM) {
			body.evaluateForGivenObject(program, world.getBuzam());
		}
		if (this.sortDirection == SortDirection.ASCENDING) {
			if (this.variableKind == Kind.PLANT) {
				plants.sort(ascendingComparator);
				for (Plant plant: plants) {
					if ((boolean)this.where.evaluateForGivenObject(program, plant)) {
						body.evaluateForGivenObject(program, plant);
					}
				}
			}
			else if (this.variableKind == Kind.SHARK) {
				sharks.sort(ascendingComparator);
				for (Shark shark: sharks) {
					if ((boolean)this.where.evaluateForGivenObject(program, shark)) {
						body.evaluateForGivenObject(program, shark);
					}
				}
			}
			else if (this.variableKind == Kind.SLIME) {
				slimes.sort(ascendingComparator);
				for (Slime slime: slimes) {
					if ((boolean)this.where.evaluateForGivenObject(program, slime)) {
						body.evaluateForGivenObject(program, slime);
					}
				}
			}
			else {
				allGameObjects.sort(ascendingComparator);
				for (GameObject gameObject: allGameObjects) {
					if ((boolean)this.where.evaluateForGivenObject(program, gameObject)) {
						body.evaluateForGivenObject(program, gameObject);
					}
				}
			}
		}
		else if (this.sortDirection == SortDirection.DESCENDING) {
			if (this.variableKind == Kind.PLANT) {
				plants.sort(descendingComparator);
				for (Plant plant: plants) {
					if ((boolean)this.where.evaluateForGivenObject(program, plant)) {
						body.evaluateForGivenObject(program, plant);
					}
				}
			}
			else if (this.variableKind == Kind.SHARK) {
				sharks.sort(descendingComparator);
				for (Shark shark: sharks) {
					if ((boolean)this.where.evaluateForGivenObject(program, shark)) {
						body.evaluateForGivenObject(program, shark);
					}
				}
			}
			else if (this.variableKind == Kind.SLIME) {
				slimes.sort(descendingComparator);
				for (Slime slime: slimes) {
					if ((boolean)this.where.evaluateForGivenObject(program, slime)) {
						body.evaluateForGivenObject(program, slime);
					}
				}
			}
			else {
				allGameObjects.sort(descendingComparator);
				for (GameObject gameObject: allGameObjects) {
					if ((boolean)this.where.evaluateForGivenObject(program, gameObject)) {
						body.evaluateForGivenObject(program, gameObject);
					}
				}
			}
		}
		else {
			if (this.variableKind == Kind.PLANT) {
				for (Plant plant: plants) {
					if ((boolean)this.where.evaluateForGivenObject(program, plant)) {
						body.evaluateForGivenObject(program, plant);
					}
				}
			}
			else if (this.variableKind == Kind.SHARK) {
				for (Shark shark: sharks) {
					if ((boolean)this.where.evaluateForGivenObject(program, shark)) {
						body.evaluateForGivenObject(program, shark);
					}
				}
			}
			else if (this.variableKind == Kind.SLIME) {
				for (Slime slime: slimes) {
					if ((boolean)this.where.evaluateForGivenObject(program, slime)) {
						body.evaluateForGivenObject(program, slime);
					}
				}
			}
			else {
				for (GameObject gameObject: allGameObjects) {
					if ((boolean)this.where.evaluateForGivenObject(program, gameObject)) {
						body.evaluateForGivenObject(program, gameObject);
					}
				}
			}
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
}
