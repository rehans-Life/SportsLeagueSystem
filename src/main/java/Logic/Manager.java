/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.util.Scanner;

public class Manager extends Member {
    private double bonusPercentage;
    private String coachingQualifications;

    public Manager(Scanner scanner) {
        super(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextInt());
        scanner.nextLine();
        this.bonusPercentage = scanner.nextDouble();
        scanner.nextLine();
        this.coachingQualifications = scanner.nextLine();
    }

    public Manager(
            String name,
            String dateOfBirth,
            String nationality,
            double yearlySalary,
            String address,
            double bonusPercentage,
            String coachingQualifications) {
        super(name, address, dateOfBirth, nationality, yearlySalary);
        this.bonusPercentage = bonusPercentage;
        this.coachingQualifications = coachingQualifications;
    }

    public void setBonusPercentage(double bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    public double getBonusPercentage() {
        return bonusPercentage;
    }

    public String getCoachingQualifications() {
        return coachingQualifications;
    }

    public void setCoachingQualifications(String coachingQualifications) {
        this.coachingQualifications = coachingQualifications;
    }

    @Override
    public String toString() {
        return getName();
    }
}
