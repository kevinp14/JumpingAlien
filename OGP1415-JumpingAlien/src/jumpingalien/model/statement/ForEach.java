package jumpingalien.model.statement;

import java.util.ArrayList;
import java.util.Hashtable;

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

public class ForEach implements Statement {
	
	private String variableName;
	private Kind variableKind;
	private Expression where;
	private final Expression sortExpression;
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
		allGameObjects.addAll(world.getPlants());
		allGameObjects.addAll(world.getSharks());
		allGameObjects.addAll(world.getSlimes());
		allGameObjects.add(world.getMazub());
		allGameObjects.add(world.getBuzam());
		if (this.sortDirection == SortDirection.ASCENDING) {
			if (this.variableKind == Kind.PLANT) {
				plants.sort((Plant plant1, Plant plant2) -> {this.sortExpression.execute(plant1, null) 
					< this.sortExpression.execute((plant2, null);});
				for (Plant plant: plants) {
				}
			}
			else if (this.variableKind == Kind.SHARK) {
				for (Shark shark: sharks) {
				}
			}
			else if (this.variableKind == Kind.SLIME) {
				for (Slime slime: slimes) {
				}
			}
			else if (this.variableKind == Kind.MAZUB) {
				body.execute(program, this.where);
			}
			else if (this.variableKind == Kind.BUZAM) {
				body.execute(program, this.where);
			}
			else {
				for (GameObject gameObject: allGameObjects) {
				}
			}
			for (GameObject o: ) {
				body.execute(program, this.where); //TODO Hoe hier game object laten uitvoeren?
			}
		}
		else if (this.sortDirection == SortDirection.DESCENDING) {
			if (this.variableKind == Kind.PLANT) {
				for (Plant plant: plants) {
				}
			}
			else if (this.variableKind == Kind.SHARK) {
				for (Shark shark: sharks) {
				}
			}
			else if (this.variableKind == Kind.SLIME) {
				for (Slime slime: slimes) {
				}
			}
			else if (this.variableKind == Kind.MAZUB) {
				body.execute(program, this.where);
			}
			else if (this.variableKind == Kind.BUZAM) {
				body.execute(program, this.where);
			}
			else {
				for (GameObject gameObject: allGameObjects) {
				}
			}
			for (GameObject o:) {
				body.execute(program, this.where); //TODO Hoe hier game object laten uitvoeren?
			}
		}
		else {
			if (this.variableKind == Kind.PLANT) {
				for (Plant plant: plants) {
					body.execute(program, this.where); //TODO Hoe hier game object laten uitvoeren?
				}
			}
			else if (this.variableKind == Kind.SHARK) {
				for (Shark shark: sharks) {
					body.execute(program, this.where); //TODO Hoe hier game object laten uitvoeren?
				}
			}
			else if (this.variableKind == Kind.SLIME) {
				for (Slime slime: slimes) {
					body.execute(program, this.where); //TODO Hoe hier game object laten uitvoeren?
				}
			}
			else if (this.variableKind == Kind.MAZUB) {
				body.execute(program, this.where);
			}
			else if (this.variableKind == Kind.BUZAM) {
				body.execute(program, this.where);
			}
			else {
				for (GameObject gameObject: allGameObjects) {
					body.execute(program, this.where); //TODO Hoe hier game object laten uitvoeren?
				}
			}
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
}
