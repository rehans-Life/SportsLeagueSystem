/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.util.*;
import java.io.*;

/**
 *
 * @author Rehan
 */
public class Team implements Serializable {
    private int teamId;
    private String name;
    private String stadiumName;
    private int stadiumCapacity;
    private Manager manager;

    static int count = 0;

    public Team(Scanner scanner) {
        this.name = scanner.nextLine();
        this.stadiumName = scanner.nextLine();
        this.stadiumCapacity = scanner.nextInt();
        teamId = ++count;
        scanner.nextLine();
    }

    public Team(String name, String stadiumName, int stadiumCapacity) {

        this.name = name;
        this.stadiumName = stadiumName;
        this.stadiumCapacity = stadiumCapacity;

        teamId = ++count;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumCapacity(int stadiumCapacity) {
        this.stadiumCapacity = stadiumCapacity;
    }

    public int getStadiumCapacity() {
        return stadiumCapacity;
    }

    @Override
    public String toString() {
        return getName();
    }   
}
