package jumpingalien.model;

import java.util.Map;
import java.util.Map.Entry;

import jumpingalien.model.GameObject;
import jumpingalien.model.statement.Statement;

public class Program {
	private final Statement mainStatement;
	private Map<String, Object> globalVariables;
	private GameObject gameObject;
	
	public Program(Statement mainStatement, Map<String, Object> globalVariables){
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
		for(Entry<String, Object> entry : this.globalVariables.entrySet()) {
		    String name = entry.getKey();
		    Object value = entry.getValue();
		    if (name == variableName){
		    	return value;
		    }
		}
		return null;
	}
	
	public void setObjectByName(String variableName, Object newValue){
		for(Entry<String, Object> entry : this.globalVariables.entrySet()) {
		    String name = entry.getKey();
		    if (name == variableName){
		    	this.globalVariables.remove(name);
		    	this.globalVariables.put(name, newValue);
		    }
		}
	}
	
	public void executeProgram(){
		this.mainStatement.execute(this, null);
	}
}