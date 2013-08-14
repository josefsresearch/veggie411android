package com.veggie411.veggie411;

public class Ingredient {
	private long id;
	private String name;
	private String eName;
	private String description;
	
	public Ingredient() {
	}

	public Ingredient(String name) {
		this.name = name;
	}

	public Ingredient(String name, String eName) {
		this(name);
		this.eName = eName;
	}

	protected Ingredient(String name, String eName, String description) {
		this(name, eName);
		this.description = description;
	}

	protected long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	protected String getName() {
		return this.name;
	}

	protected String geteName() {
		return this.eName;
	}

	protected String getDescription() {
		return this.description;
	}

	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
