package com.veggie411.veggie411;

public class Ingredient {
	private long id;
	private String name;
	private String eName;
	private String refName;
	private int refInt;
	private String statusName;
	private int statusInt;
	private String description;
	//TODO finish
	public Ingredient() {
		
	}

	public Ingredient(String name) {
		this.name = name;
	}
	
	public Ingredient(String name, int statusInt) {
		this(name);
		this.statusInt = statusInt;
	}

	public Ingredient(String name, String eName) {
		this(name);
		this.eName = eName;
	}

	protected Ingredient(String name, String eName, String references, String status, String description) {
		//TODO
		this(name, eName);
		this.refName = references;
		setStatus(status);
		this.description = description;
	}
	
	protected Ingredient(String name, String eName, String references, int status, String description) {
		this(name, eName);
		this.refName = references;
		this.statusInt = status;
		this.description = description;
	}

	protected long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected String getName() {
		return this.name;
	}
	
	protected void setEName(String ename) {
		this.eName = ename;
	}

	protected String getEName() {
		return this.eName;
	}
	
	protected void setReferencesName(String ref) {
		//TODO
		this.refName = ref;
	}
	
	protected void setReferencesInt(int ref) {
		//TODO
		this.refInt = ref;
	}
	
	protected String getReferencesName() {
		//TODO
		return this.refName;
	}
	
	protected int getReferencesInt() {
		//TODO
		return this.refInt;
	}
	
	protected void setStatus(String status) {
		statusName = status;
		if (status == Constants.VEGAN_NAME) {
			statusInt = Constants.VEGAN_INT;
		} else if (status == Constants.VEGETARIAN_NAME) {
			statusInt = Constants.VEGETARIAN_INT;
		} else if (status == Constants.LACTO_OVO_NAME) {
			statusInt = Constants.LACTO_OVO_INT;
		} else if (status == Constants.PESCATARIAN_NAME) {
			statusInt = Constants.PESCATARIAN_INT;
		}
	}
	
	protected String getStatusName() {
		return statusName;
	}
	
	protected void setStatusInt(int status) {
		statusInt = status;
	}
	
	protected int getStatusInt() {
		return statusInt;
	}
	
	protected void setDescription(String description) {
		this.description = description;
	}

	protected String getDescription() {
		return this.description;
	}

	public String toString() {
		return name;
	}

}
