/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.io.Serializable;

/**
 *
 * @author Rehan
 */
public class Member implements Serializable {
    int id;
    String name;
    String dateOfBirth;
    String nationality;
    double yearlySalary;
    String address;

    static int count = 0;

    public Member() {
        name = "";
        dateOfBirth = "";
        nationality = "";
        yearlySalary = 0;
        address = "";

        id = ++count;

    }

    public Member(String name,
            String address,
            String dateOfBirth,
            String nationality,
            double yearlySalary) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.yearlySalary = yearlySalary;
        this.address = address;

        id = ++count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setYearlySalary(double yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public double getYearlySalary() {
        return yearlySalary;
    }

    @Override
    public String toString() {
        return String.format("\nID: %s\nName: %s\nYear Salary: %s\n", getId(), getName(), getYearlySalary());
    }   
}
