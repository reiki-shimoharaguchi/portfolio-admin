package com.seattleacademy.team20;

public class SkillCategory {

  private String category;
  private int id;
  private String name;
  private int score;
  private String label;

  public SkillCategory(int id, String name, int score, String label, String category) {
    this.category = category;
    this.id = id;
    this.name = name;
    this.score = score;
    this.label = label;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
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

}
