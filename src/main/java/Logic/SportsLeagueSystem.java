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
        
        for (Player player: players) {
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
            if (scanner != null) scanner.close();
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

    public Team getTeam(int teamId) throws Exception {
        for (Team team : teams) {
            if (team.getTeamId() == teamId)
                return team;
        }
        throw new Exception("Team not found");
    }
    
    public String[] getTeamNames() {
        int teamsSize = teams.size();

        String[] teamNames = new String[teamsSize];
        for (int i = 0; i < teamsSize; i++) {
            teamNames[i] = teams.get(i).getName();
        }
        
        return teamNames;
    }
    
    public Team getTeamByName(String teamName) throws Exception {
        for (Team team : getTeams()) {
            if (team.getName().equalsIgnoreCase(teamName))
                return team;
        }
        throw new Exception("Team not found");
    }
}
