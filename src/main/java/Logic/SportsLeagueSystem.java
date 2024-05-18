/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Rehan
 */
public class SportsLeagueSystem {
    ArrayList<Team> teams;
    ArrayList<Player> players;
    String serializedFile;

    public SportsLeagueSystem() {
        teams = new ArrayList<>();
        players = new ArrayList<>();
        serializedFile = "sportsLeague.ser";

        File file = new File(serializedFile);

        if (file.exists()) {
            deserialize();
        } else {
            cleanStart("startup.txt");
        }
    }

    boolean createFile(String name) {

        try {
            File file = new File(name);
            file.createNewFile();
            return true;
        } catch (Exception e) {
            System.out.println("An I/O Error Occured");
            return false;
        }
    }

    public void exit() {
        serialize();
        System.exit(0);
    }

    public ArrayList<Player> getPlayersFromTeam(int teamId) {
        ArrayList<Player> teamPlayers = new ArrayList<>();

        for (Player player : players) {
            if (player.getTeamId() == teamId) {
                teamPlayers.add(player);
            }
        }

        return teamPlayers;
    }

    public void cleanStart(String startupFilePath) {
        File file = new File(startupFilePath);
        Scanner scanner = null;

        teams.clear();
        players.clear();

        try {
            scanner = new Scanner(file);

            int numberOfTeams = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numberOfTeams; i++) {
                Team team = new Team(scanner);
                Manager manager = new Manager(scanner);

                team.setManager(manager);

                int numberOfPlayers = scanner.nextInt();
                scanner.nextLine();

                for (int j = 0; j < numberOfPlayers; j++) {
                    Player player = new Player(scanner, team.getTeamId());
                    players.add(player);
                }

                teams.add(team);
            }

            int nonAssignedPlayers = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < nonAssignedPlayers; i++) {
                players.add(new Player(scanner));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not Found");
        } finally {
            if (scanner != null)
                scanner.close();
        }
    }

    public void serialize() {
        try {
            if (!createFile(serializedFile))
                throw new Exception();

            FileOutputStream fileOutputStream = new FileOutputStream(serializedFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("teams", getTeams());
            hashMap.put("players", getPlayers());

            objectOutputStream.writeObject(hashMap);

            objectOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An Error Occured while serializing");
        }
    }

    @SuppressWarnings("unchecked")
    public void deserialize() {
        try {
            FileInputStream fileInputStream = new FileInputStream(serializedFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            HashMap<String, Object> hashMap = (HashMap<String, Object>) objectInputStream.readObject();

            ArrayList<Player> serializedPlayers = (ArrayList<Player>) hashMap.get("players");
            ArrayList<Team> serializedTeams = (ArrayList<Team>) hashMap.get("teams");

            int totalManagers = 0;

            for (Team team : serializedTeams) {
                if (team.getManager() != null) {
                    totalManagers++;
                }
            }

            setTeams(serializedTeams);
            setPlayers(serializedPlayers);

            Team.setCount(serializedTeams.size() + 1);
            Member.setCount(serializedPlayers.size() + totalManagers + 1);

            objectInputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An Error Occured while deserializing");
        }
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void alterMember(Player player, String name, String address, String nationality, String dob,
            String position, double salary, boolean isCaptain, int teamId) throws Exception {

        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(dob, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use the format: dd/MM/yyyy");
        }

        player.setName(name);
        player.setAddress(address);
        player.setNationality(nationality);
        player.setDateOfBirth(dob);
        player.setPosition(position);
        player.setYearlySalary(salary);
        player.setCaptain(isCaptain);
        player.setTeamId(teamId);
    }

    public void alterMember(Manager manager, String name, String address, String nationality, String dob, double salary,
            String coachingQualifications, double bonusPercentage, int teamId) throws Exception {

        if (bonusPercentage < 0) {
            throw new IllegalArgumentException("Bonus Percentage cannot be negative");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            LocalDate.parse(dob, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use the format: dd/M/yyyy");
        }

        manager.setName(name);
        manager.setAddress(address);
        manager.setNationality(nationality);
        manager.setDateOfBirth(dob);
        manager.setCoachingQualifications(coachingQualifications);
        manager.setBonusPercentage(bonusPercentage);

    }

    public Manager getManagerById(int managerID) {
        for (Team team : teams) {
            Manager manager = team.getManager();
            if (manager.getId() == managerID) {
                return manager;
            }
        }
        return null;
    }

    public int[] getManagerIds() {
        int[] managerIds = new int[teams.size()];
        int i = 0;
        for (Team team : teams) {
            if (team.getManager() != null) {
                int managerId = team.getManager().getId();
                managerIds[i] = managerId;
                i++;
            }
        }

        return managerIds;
    }

    public String[] getManagerIdString() {
        int[] managerId = getManagerIds();
        String[] stringManagerIds = intArrayToStringArray(managerId);
        return stringManagerIds;
    }

    public static String[] intArrayToStringArray(int[] intArray) {
        if (intArray == null || intArray.length == 0) {
            return new String[0];
        }
        String[] stringArray = new String[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            stringArray[i] = String.valueOf(intArray[i]);
        }
        return stringArray;
    }

    public Player getPlayerById(int playerId) {
        for (Player player : players) {
            if (player.getId() == playerId) {
                return player;
            }
        }
        return null;
    }

    public int getIndexOfPlayerInArr(int playerId) {

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == playerId) {
                return i;
            }
        }
        return -1;
    }

    public Team getTeam(int teamId) throws Exception {
        for (Team team : teams) {
            if (team.getTeamId() == teamId)
                return team;
        }
        throw new Exception("Team not found");
    }

    public int getTeamIDOfMember(int memberId) {
        for (Team team : teams) {
            if (team.getManager().getId() == memberId) {
                return team.getTeamId();
            }
        }
        return -1;
    }

    public Player getPlayer(int playerId) throws Exception {
        for (Player player : players) {
            if (player.getId() == playerId)
                return player;
        }
        throw new Exception("Player not found");
    }

    public String[] getTeamNames() {
        int teamsSize = teams.size();

        String[] teamNames = new String[teamsSize];
        for (int i = 0; i < teamsSize; i++) {
            teamNames[i] = teams.get(i).getName();
        }

        return teamNames;
    }

    public String[] getTeamIDs() {
        int teamsSize = teams.size();

        String[] teamIDs = new String[teamsSize];
        for (int i = 0; i < teamsSize; i++) {
            teamIDs[i] = String.valueOf(teams.get(i).getTeamId());
        }

        return teamIDs;
    }

    public String[] getPlayerIDs() {
        int numOfPlayers = players.size();

        String[] playerId = new String[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            playerId[i] = String.valueOf(players.get(i).getId());
        }

        return playerId;
    }

    public Team getTeamByName(String teamName) throws Exception {
        for (Team team : getTeams()) {
            if (team.getName().equalsIgnoreCase(teamName))
                return team;
        }
        throw new Exception("Team not found");
    }
    
    /** Name:  addPlayer
    * @author  Zainab Abdulhusain
    * Purpose/description: Adding a player to the system
    * @param   player - the player object to be added to the league
    * @return  void - never returns a value
    */
    public void addPlayer(Player player) {
        // add the player to the players ArrayList
        this.players.add(player); 
    }
    
    /** Name:  teamHasCaptain
    * @author  Zainab Abdulhusain
    * Purpose/description: to find out whether the team has a captain or not
    * @param   teamId - the team ID of the team that the player belongs to
    * @return  true - if the team has a captain, false otherwise.
    */
    public boolean teamHasCaptain(int teamId) {
        for (Player player : players) { // loop through the players ArrayList
            // return true if the players team id is equal to the provided team id, 
            // and if the player is a captain
            if(player.getTeamId()==teamId && player.isCaptain()) {
                return true;
            }
        }
        return false;
    }
    
    /** Name:  getTeamCaptain
    * @author  Zainab Abdulhusain
    * Purpose/description: finding the team captain object
    * @param   teamId - the id of the team that the captain belongs to
    * @return  Player - the captain's object, null otherwise.
    */
    public Player getTeamCaptain(int teamId) {
        for (Player player : players) { // loop through the players ArrayList
            // return the player object if the current player's team id matches 
            // the provided team id and if the player is a captain
            if(player.getTeamId()==teamId && player.isCaptain()) {
                return player;
            }
        }
        return null;
    }
    
    /** Name:  transferPlayer
    * @author  Zainab Abdulhusain
    * Purpose/description: transferring or assigning the player to a new team
    * @param   playerID - the id of the player to be transferred
    * @param   newTeamID - the id of the team that the player is getting 
    * assigned/transferred to
    * @return  void - never returns a value
    */
    public void transferPlayer(int playerID, int newTeamID) throws Exception {
        // retrieve the player object using the player id
        Player player = getPlayer(playerID);
        
        // retrieve the new team object using the new team id
        Team newTeam = getTeam(newTeamID);
        
        // get the old team id from the player object
        int oldTeamID = player.getTeamId();
        
        // retrieve the old team object using the old team id
        Team oldTeam = getTeam(oldTeamID);
        
        // if the player is currently assigned to a team, remove the player 
        // from their old team
       if(oldTeam != null) {
           removePlayer(player);
       }
       
       // add the player to the new team
       addPlayer(player);
       
       // update the player's team id to the new team id
       player.setTeamId(newTeamID);
       
    }
    
    /** Name:  removePlayer
    * @author  Zainab Abdulhusain
    * Purpose/description: removing a player from the players list
    * @param   player - the object of the player to be removed
    * @return  void - never returns a value
    */
    public void removePlayer(Player player) {
        players.remove(player); // remove the player object from the players ArrayList
    }
 
}
