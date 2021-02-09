package org.jointheleague.ventilator;

import org.jointheleague.ventilator.calculations.Units;

public class PatientProfile { // TODO test this class
	public int age;
	public double height;
	public double weight;
	public double bmi;
	public String gender;
	public String disease;

	public PatientProfile(int age, double height, double weight, String gender, String disease) {
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.bmi = weight/(height*height);
		this.gender = gender;
		this.disease = disease;
	}
	
	/**
	 * Gets age of patient.
	 * @return Age (years)
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets age of patient.
	 * @param age Age (years)
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Gets height of patient.
	 * @return Height (in)
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Sets height of patient.
	 * @param height Height (in)
	 */
	public void setHeight(double height) {
		this.height = height;
		this.bmi = weight/(height*height);
	}

	/**
	 * Gets weight of patient.
	 * @return Weight (lb)
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Sets weight of patient.
	 * @param weight Weight (lb)
	 */
	public void setWeight(double weight) {
		this.weight = weight;
		this.bmi = (weight/Units.KILOGRAM)/((height*height)/(Units.METER*Units.METER));
	}

	/**
	 * Gets BMI of patient.
	 * @return BMI
	 */
	public double getBmi() {
		return bmi;
	}

	/**
	 * Gets gender of patient.
	 * @return Gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets gender of patient.
	 * @param gender Gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets disease of patient.
	 * @return Disease
	 */
	public String getDisease() {
		return disease;
	}

	/**
	 * Sets disease of patient.
	 * @param disease Disease
	 */
	public void setDisease(String disease) {
		this.disease = disease;
	}
}
