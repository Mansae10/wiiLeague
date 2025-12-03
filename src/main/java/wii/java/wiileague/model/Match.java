package wii.java.wiileague.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="matches")
public class Match {
    @Id
    private String id;
    private String matchId;
    private String gameMode;
    private long gameDuration;
    private long gameCreation;
    private List<Participant> participants;

    public Match(){}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getMatchId() {return matchId;}
    public void setMatchId(String matchId) {this.matchId = matchId;}

    public String getGameMode() {return gameMode;}
    public void setGameMode(String gameMode) {this.gameMode = gameMode;}

    public long getGameDuration() {return gameDuration;}
    public void setGameDuration(long gameDuration) {this.gameDuration = gameDuration;}

    public long getGameCreation() {return gameCreation;}
    public void setGameCreation(long gameCreation) {this.gameCreation = gameCreation;}

    public List<Participant> getParticipants() {return participants;}
    public void setParticipants(List<Participant> participants) {this.participants = participants;}

    public static class Participant {
        private String puuid;
        private String summonerName;
        private String championName;
        private int championId;
        private int kills;
        private int deaths;
        private int assists;
        private boolean win;
        private List<Integer> items;

        public Participant() {}

        public String getPuuid() {return puuid;}
        public void setPuuid(String puuid) {this.puuid = puuid;}

        public String getSummonerName() {return summonerName;}
        public void setSummonerName(String summonerName) {this.summonerName = summonerName;}

        public String getChampionName() { return championName;}
        public void setChampionName(String championName) {this.championName = championName;}

        public int getChampionId() {return championId;}
        public void setChampionId(int championId) {this.championId = championId;}

        public int getKills() {return kills;}
        public void setKills(int kills) {this.kills = kills;}

        public int getDeaths() {return deaths;}
        public void setDeaths(int deaths) {this.deaths = deaths;}

        public int getAssists() {return assists;}
        public void setAssists(int assists) {this.assists = assists;}

        public boolean isWin() {return win;}
        public void setWin(boolean win) {this.win = win;}

        public List<Integer> getItems() {return items;}
        public void setItems(List<Integer> items) {this.items = items;}

    }
}
