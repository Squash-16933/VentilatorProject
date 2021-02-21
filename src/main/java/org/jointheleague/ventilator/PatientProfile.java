package org.jointheleague.ventilator;

import org.jointheleague.ventilator.calculations.Units;

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

	/**
	 * Creates a PatientProfile object: the configuration for a patient.
	 * @param age Age (months)
	 * @param height Height (in)
	 * @param weight Weight (lb)
	 * @param gender Biological gender (<code>male</code> or <code>female</code>)
	 */
	public PatientProfile(int age, double height, double weight, String gender) {
		this(age, height, weight, gender, (String) null);
	}

	/**
	 * Creates a PatientProfile object: the configuration for a patient.
	 * @param age Age (months)
	 * @param height Height (in)
	 * @param weight Weight (lb)
	 * @param gender Biological gender (<code>male</code> or <code>female</code>)
	 * @param disease Disease
	 */
	public PatientProfile(int age, double height, double weight, String gender, String disease) {
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.bmi = (weight/Units.KILOGRAM)/((height*height)/(Units.METER*Units.METER));
		this.gender = gender;
		this.disease = disease;
	}
	
	/**
	 * Gets age of patient.
	 * @return Age (months)
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets age of patient.
	 * @param age Age (months)
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
		this.bmi = (weight/Units.KILOGRAM)/((height*height)/(Units.METER*Units.METER));
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

	@Override
	public String toString() {
		if (disease == null) {
			return (age/12)+"-year-old "+height+"-inch-tall "+weight+"lb "+gender;
		} else {
			return (age/12)+"-year-old "+height+"-inch-tall "+weight+"lb "+gender+" with "+disease;
		}
	}
}
