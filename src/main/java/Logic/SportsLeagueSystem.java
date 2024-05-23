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

    /**
     * Name: createFile
     * 
     * @author Rehan Tosif 
     * Purpose: creating a file with the given name if it doesn't exists.
     * @param name - the name of the file to be created
     * @return boolean - true/false indicating whether the
     *                   file was deleted or not            
     */
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

    /** Name:  exit
    * @author  Rehan Tosif
    * Purpose: Called whenever the user's exit's from
    *          the system by clicking the exit button. It exit's the system 
    *          with a status code of 0 and also run's serialize method to save
    *          the objects 
    */
    public void exit() {
        serialize();
        System.exit(0);
    }

    /** Name:  getPlayersFromTeam
    * @author  Rehan Tosif
    * Purpose: returns an array of players that belong to a specific team.
    * @param teamId - Id of team whose players are to be retrieved.
    * @return   Player[] - An array list containing of all the players
    *           that have the same team id
    */
    public ArrayList<Player> getPlayersFromTeam(int teamId) {
        ArrayList<Player> teamPlayers = new ArrayList<>();

        for (Player player : players) {
            if (player.getTeamId() == teamId) {
                teamPlayers.add(player);
            }
        }

        return teamPlayers;
    }

    /** Name:  cleanStart
    * @author  Rehan Tosif
    * Purpose: Called whenever the user's exit's from
    *          the system by clicking the exit button. It exit's the system 
    *          with a status code of 0 and also run's serialize method to save
    *          the objects 
    * @param   startupFilePath - the path to the file from which predefined.
    *          data is going to be extracted and loaded in to the system.
    */
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

    /** Name:  serialize
    * @author  Rehan Tosif
    * Purpose: This method is suppose take all the teams and members
    *         from system and serialize them into file called sportsLeagure.ser
    */ 
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

    /** Name:  deserialize
    * @author  Rehan Tosif
    * Purpose: This method is suppose to extract the serialized content
    *         from the sportsLeague.ser file and load it into the system.
    */ 
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

    /**
     * Name: addTeam
     * 
     * @author Rehan Tosif 
     * Purpose: adding a team from the system
     * @param team - the team instance to be added
     */
    public void addTeam(Team team) {
        teams.add(team);
    }

    /**
     * Name: alterMember
     * 
     * @author Hussain Almakana
     *         Purpose/description: Alters the details of a player.
     * @param player      - the player object whose details are to be altered.
     * @param name        - the new name of the player.
     * @param address     - the new address of the player.
     * @param nationality - the new nationality of the player.
     * @param dob         - the new date of birth of the player in the format
     *                    dd/MM/yyyy.
     * @param position    - the new position of the player.
     * @param salary      - the new yearly salary of the player.
     * @param isCaptain   - boolean indicating if the player is the captain.
     * @param teamId      - the ID of the new team the player belongs to.
     * @throws Exception - if the salary is negative or the date format is invalid.
     */
    public void alterMember(Player player, String name, String address, String nationality, String dob,
            String position, double salary, boolean isCaptain, int teamId) throws Exception {

        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
        try {
            LocalDate.parse(dob, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use the format: dd/M/yyyy");
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

    /**
     * Name: alterMember
     * 
     * @author Hussain Almakana
     *         Purpose/description: Alters the details of a manager and updates team
     *         assignments.
     * @param manager                - the manager object whose details are to be
     *                               altered.
     * @param name                   - the new name of the manager.
     * @param address                - the new address of the manager.
     * @param nationality            - the new nationality of the manager.
     * @param dob                    - the new date of birth of the manager in the
     *                               format dd/M/yyyy.
     * @param salary                 - the new salary of the manager.
     * @param coachingQualifications - the new coaching qualifications of the
     *                               manager.
     * @param bonusPercentage        - the new bonus percentage of the manager.
     * @param teamId                 - the ID of the new team the player belongs to.
     * @throws Exception - if the bonus percentage is negative or the date format is
     *                   invalid.
     */
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

        // Find the current team of the manager
        Team currentTeam = null;
        for (Team team : teams) {
            if (team.getManager() != null && team.getManager().getId() == manager.getId()) {
                currentTeam = team;
                break;
            }
        }

        // Find the new team by teamId
        Team newTeam = null;
        for (Team team : teams) {
            if (team.getTeamId() == teamId) {
                newTeam = team;
                break;
            }
        }

        // Update the teams' manager references
        if (currentTeam != null && currentTeam != newTeam && newTeam != null) {
            Manager tempManager = newTeam.getManager(); // Store the new team's manager
            newTeam.setManager(manager); // Set the manager for the new team
            currentTeam.setManager(tempManager); // Set the manager for the current team
        }
    }

    /**
     * Name: alterTeam
     * 
     * @author Jenan
     *         Purpose/description: Alters the details of a team in the sports
     *         league system.
     *
     * @param teamId          The ID of the team to alter.
     * @param teamName        The new name for the team.
     * @param stadiumName     The new name for the team's stadium.
     * @param stadiumCapacity The new capacity for the team's stadium.
     * @throws Exception If an error occurs while altering the team.
     * 
     */
    public void alterTeam(int teamId, String teamName, String stadiumName, int stadiumCapacity) throws Exception {
        try {
            Team team = getTeam(teamId);
            team.setName(teamName);
            team.setStadiumName(stadiumName);
            team.setStadiumCapacity(stadiumCapacity);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /** Name:  addManager
    * @author  Rehan Tosif
    * Purpose: This method is suppose to extract the serialized content
    *          from the sportsLeague.ser file and load it into the system.
    * 
    * @param manager         The manager to be added to the system
    * @param teamId          The ID of the team to alter.
    * @throws Exception If team is not found
    * 
    */ 
    public void addManager(Manager manager, int teamId) throws Exception {
        Team team = getTeam(teamId);
        team.setManager(manager);
    }
    
    /**
     * Name: deleteTeam
     * 
     * @author Rehan Tosif 
     * Purpose: deleting a team from the system
     * @param team - the team instance to be deleted
     */
    public void deleteTeam(Team team) {
        teams.remove(team);
    }

    /**
     * Name: getManagerById
     * 
     * @author Rehan Tosif 
     * Purpose: Getting a Manager from the system
     * @param managerID - the id of the maanger to be returned
     * @return Player - manager corresponding to the Id provided
     */
    public Manager getManagerById(int managerID) {
        for (Team team : teams) {
            Manager manager = team.getManager();
            if (manager != null && manager.getId() == managerID) {
                return manager;
            }
        }
        return null;
    }


    /** Name:  getManagerIds
    * @author  Rehan Tosif
    * Purpose: returns an array of managers ids that can be used inside of 
    *          the jcombo box
    * @return int[] - An array containing the ids of all the maangers 
    *                 in the system  
    */
    public int[] getManagerIds() {
        ArrayList<Integer> managerIds = new ArrayList<>();

        for (Team team : teams) {
            Manager manager = team.getManager();
            if (manager != null) {
                int managerId = manager.getId();
                managerIds.add(managerId);
            }
        }

        int[] idsArray = new int[managerIds.size()];

        for (int i = 0; i < managerIds.size(); i++) {
            idsArray[i] = managerIds.get(i);
        }

        return idsArray;
    }

    /** Name:  getManagerIdString
    * @author  Rehan Tosif
    * Purpose: returns an array of managers ids as strings that can be used inside of 
    *          the Jcombo box
    * @return int[] - An array containing the ids of all the managers 
    *                 in the system  
    */
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

    /** Name:  getPlayerById
    * @author  Rehan Tosif
    * Purpose: returns player corresponding to the id
    * @param playerId - id of the player to be retrieved
    * @return Player - the player corresponding to the id
    *                  passed as argument
    */
    public Player getPlayerById(int playerId) {
        for (Player player : players) {
            if (player.getId() == playerId) {
                return player;
            }
        }
        return null;
    }

    /**
     * Name: getTeam
     * 
     * @author Rehan Tosif 
     * Purpose: Getting a team from the system
     * @param teamId - the id of the team to be returned
     * @return Team - team corresponding to the Id provided
     * @throws Exception - when there is no team corresponding to the id.  
     */
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

    /**
     * Name: getPlayer
     * 
     * @author Rehan Tosif 
     * Purpose: Getting a player from the system
     * @param playerId - the id of the player to be returned
     * @return Player - player corresponding to the Id provided
     * @throws Exception - when there is no player corresponding to the id.  
     */
    public Player getPlayer(int playerId) throws Exception {
        for (Player player : players) {
            if (player.getId() == playerId)
                return player;
        }
        throw new Exception("Player not found");
    }

    /** Name:  getTeamNames
    * @author  Rehan Tosif
    * Purpose: returns an array of team names that can be used inside of 
    *          the Jcombo box
    * @return String[] - An array containing the names of all the teams
    */
    public String[] getTeamNames() {
        int teamsSize = teams.size();

        String[] teamNames = new String[teamsSize];
        for (int i = 0; i < teamsSize; i++) {
            teamNames[i] = teams.get(i).getName();
        }

        return teamNames;
    }

    /** Name:  getTeamIDs
    * @author  Rehan Tosif
    * Purpose: returns an array of team ids that can be used inside of 
    *          the jcombo box
    * @return String[] - An array containing the ids of all the teams
    */
    public String[] getTeamIDs() {
        int teamsSize = teams.size();

        String[] teamIDs = new String[teamsSize];
        for (int i = 0; i < teamsSize; i++) {
            teamIDs[i] = String.valueOf(teams.get(i).getTeamId());
        }

        return teamIDs;
    }

    /** Name:  getPlayerIDs
    * @author  Rehan Tosif
    * Purpose: returns an array of player ids that can be used inside of 
    *          the Jcombo box
    * @return String[] - An array containing the ids of all the teams
    */
    public String[] getPlayerIDs() {
        int numOfPlayers = players.size();

        String[] playerId = new String[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            playerId[i] = String.valueOf(players.get(i).getId());
        }

        return playerId;
    }
    
    /**
     * Name: addPlayer
     * 
     * @author Zainab Abdulhusain
     *         Purpose/description: Adding a player to the system
     * @param player - the player object to be added to the league
     */
    public void addPlayer(Player player) {
        // add the player to the players ArrayList
        this.players.add(player);
    }

    /**
     * Name: teamHasCaptain
     * 
     * @author Zainab Abdulhusain
     *         Purpose/description: to find out whether the team has a captain or
     *         not
     * @param teamId - the team ID of the team that the player belongs to
     * @return true - if the team has a captain, false otherwise.
     */
    public boolean teamHasCaptain(int teamId) {
        for (Player player : players) { // loop through the players ArrayList
            // return true if the players team id is equal to the provided team id,
            // and if the player is a captain
            if (player.getTeamId() == teamId && player.isCaptain()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Name: getTeamCaptain
     * 
     * @author Zainab Abdulhusain
     *         Purpose/description: finding the team captain object
     * @param teamId - the id of the team that the captain belongs to
     * @return Player - the captain's object, null otherwise.
     */
    public Player getTeamCaptain(int teamId) {
        for (Player player : players) { // loop through the players ArrayList
            // return the player object if the current player's team id matches
            // the provided team id and if the player is a captain
            if (player.getTeamId() == teamId && player.isCaptain()) {
                return player;
            }
        }
        return null;
    }

    /**
     * Name: transferPlayer
     * 
     * @author Zainab Abdulhusain
     *         Purpose/description: transferring or assigning the player to a new
     *         team
     * @param playerID  - the id of the player to be transferred
     * @param newTeamID - the id of the team that the player is getting
     *                  assigned/transferred too.
     * @throws Exception - If team or player are not found.
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
        if (oldTeam != null) {
            removePlayer(player);
        }

        // add the player to the new team
        addPlayer(player);

        // update the player's team id to the new team id
        player.setTeamId(newTeamID);

    }


    /**
    * Name: removeMember
    * 
    * @author Rehan Tosif
    * Purpose: To find the member weather manager or player and delete from 
    *          within the system and delete it from the system
    * @param memberId - the ID of the member to be removed from the system
    */    
    public void removeMember(int memberId) {

        for (Team team : teams) {
            Manager manager = team.getManager();

            if (manager != null && manager.getId() == memberId) {
                team.removeManager();
                return;
            }
        }

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == memberId) {
                players.remove(i);
                return;
            }
        }
    }

    /**
     * Name: removePlayer
     * 
     * @author Zainab Abdulhusain
     *         Purpose/description: removing a player from the players list
     * @param player - the object of the player to be removed
     */
    public void removePlayer(Player player) {
        players.remove(player); // remove the player object from the players ArrayList
    }

}
