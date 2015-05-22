package jumpingalien.model;

import java.util.Map;
import java.util.Map.Entry;

import jumpingalien.model.GameObject;
import jumpingalien.model.statement.Statement;
import jumpingalien.model.type.Type;

public class Program {
	private final Statement mainStatement;
	private Map<String, Type<?>> globalVariables;
	private Map<String, Object> variables;
	private GameObject gameObject;
	
	public Program(Statement mainStatement, Map<String, Type<?>> globalVariables){
		this.mainStatement = mainStatement;
		this.globalVariables = globalVariables;
		for (Entry<String, Type<?>> entry : this.globalVariables.entrySet()){
			String variableName = entry.getKey();
			this.variables.put(variableName, null);
		}
	}
	
	public GameObject getGameObject() {
		return this.gameObject;
	}
	
	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	public boolean isWellFormed(){
		return true;
	}
	
	public Object getObjectByName(String variableName){
		for(Entry<String, Object> entry : this.variables.entrySet()) {
		    String name = entry.getKey();
		    Object value = entry.getValue();
		    if (name == variableName){
		    	return value;
		    }
		}
		return null;
	}
	
	public void setObjectByName(String variableName, Object value){
		for(Entry<String, Object> entry : this.variables.entrySet()) {
		    String name = entry.getKey();
		    if ((name == variableName) && (this.globalVariables.get(name) == type)){
		    	this.variables.put(name, value);
		    }
		}
	}
	
	public void executeProgram(){
		this.mainStatement.execute(this, null);
	}
}
