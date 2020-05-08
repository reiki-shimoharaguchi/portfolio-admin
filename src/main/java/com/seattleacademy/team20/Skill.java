package com.seattleacademy.team20;

public class skill {

	private int id;
	private String name;
	private int score;
	private String label;
	private String category;

	public skill(int id, String name, int score, String label, String category) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.id = id;
		this.name = name;
		this.score = score;
		this.label = label;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}




	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}



	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}