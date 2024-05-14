/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.util.*;

/**
 *
 * @author Rehan
 */
public class Player extends Member {
    private boolean isCaptain;
    private String position;
    private int teamId;

    public Player(Scanner scanner) {
        super(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), 0);

        position = scanner.nextLine();

        isCaptain = scanner.nextLine().equalsIgnoreCase("Captain");
        this.teamId = -1;
    }

    public Player(Scanner scanner, int teamId) {
        super(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextInt());
        scanner.nextLine();

        position = scanner.nextLine();
        isCaptain = scanner.nextLine().equalsIgnoreCase("Captain");
        this.teamId = teamId;
    }

    public Player(
            String name,
            String dateOfBirth,
            String nationality,
            double yearlySalary,
            String address,
            boolean isCaptain,
            String position,
            int teamId) {
        super(name, address, dateOfBirth, nationality, yearlySalary);
        this.isCaptain = isCaptain;
        this.position = position;
        this.teamId = teamId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void setCaptain(boolean isCaptain) {
        this.isCaptain = isCaptain;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }   
}
