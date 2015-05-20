package jumpingalien.model;

import java.util.Map;
import java.util.Map.Entry;

import jumpingalien.model.GameObject;
import jumpingalien.model.statement.Statement;
import jumpingalien.model.type.Type;

public class Program {
	private final Statement mainStatement;
	private Map<String, Type<?>> globalVariables;
	private GameObject gameObject;
	
	public Program(Statement mainStatement, Map<String, Type<?>> globalVariables){
		this.mainStatement = mainStatement;
		this.globalVariables = globalVariables;
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
		for(Entry<String, Type<?>> entry : this.globalVariables.entrySet()) {
		    String name = entry.getKey();
		    Object value = entry.getValue();
		    if (name == variableName){
		    	return value;
		    }
		}
		return null;
	}
	
	public void setObjectByName(String variableName, Type<?> type){
		for(Entry<String, Type<?>> entry : this.globalVariables.entrySet()) {
		    String name = entry.getKey();
		    if (name == variableName){
		    	this.globalVariables.remove(name);
		    	this.globalVariables.put(name, type);
		    }
		}
	}
	
	public void executeProgram(){
		this.mainStatement.execute(this, null);
	}
}