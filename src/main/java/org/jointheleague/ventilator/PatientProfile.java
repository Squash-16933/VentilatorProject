package org.jointheleague.ventilator;

public class PatientProfile {
	public PatientProfile(int age, double height, double weight, double bmi, String gender, String disease) {
		super();
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.bmi = bmi;
		this.gender = gender;
		this.disease = disease;
	}
	public int age;
	public double height;
	public double weight;
	public double bmi;
	public String gender;
	public String disease;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getBmi() {
		return bmi;
	}
	public void setBmi(double bmi) {
		this.bmi = bmi;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
}
