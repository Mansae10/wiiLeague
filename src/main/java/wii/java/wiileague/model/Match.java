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
        private String riotIdGameName;      
        private String riotIdTagline;      
        private String championName;
        private int championId;
        private int champLevel;             
        private int kills;
        private int deaths;
        private int assists;
        private boolean win;
        private List<Integer> items;
    
        private int item0;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private int item6;                 
        
      
        private int summoner1Id;
        private int summoner2Id;
        
     
        private int totalDamageDealtToChampions;
        private int goldEarned;
        private int totalMinionsKilled;
        private int neutralMinionsKilled;

        public Participant() {}

        public String getPuuid() {return puuid;}
        public void setPuuid(String puuid) {this.puuid = puuid;}

        public String getSummonerName() {return summonerName;}
        public void setSummonerName(String summonerName) {this.summonerName = summonerName;}

        public String getRiotIdGameName() {return riotIdGameName;}
        public void setRiotIdGameName(String riotIdGameName) {this.riotIdGameName = riotIdGameName;}

        public String getRiotIdTagline() {return riotIdTagline;}
        public void setRiotIdTagline(String riotIdTagline) {this.riotIdTagline = riotIdTagline;}

        public String getChampionName() { return championName;}
        public void setChampionName(String championName) {this.championName = championName;}

        public int getChampionId() {return championId;}
        public void setChampionId(int championId) {this.championId = championId;}

        public int getChampLevel() {return champLevel;}
        public void setChampLevel(int champLevel) {this.champLevel = champLevel;}

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

        public int getItem0() {return item0;}
        public void setItem0(int item0) {this.item0 = item0;}

        public int getItem1() {return item1;}
        public void setItem1(int item1) {this.item1 = item1;}

        public int getItem2() {return item2;}
        public void setItem2(int item2) {this.item2 = item2;}

        public int getItem3() {return item3;}
        public void setItem3(int item3) {this.item3 = item3;}

        public int getItem4() {return item4;}
        public void setItem4(int item4) {this.item4 = item4;}

        public int getItem5() {return item5;}
        public void setItem5(int item5) {this.item5 = item5;}

        public int getItem6() {return item6;}
        public void setItem6(int item6) {this.item6 = item6;}

        public int getSummoner1Id() {return summoner1Id;}
        public void setSummoner1Id(int summoner1Id) {this.summoner1Id = summoner1Id;}

        public int getSummoner2Id() {return summoner2Id;}
        public void setSummoner2Id(int summoner2Id) {this.summoner2Id = summoner2Id;}

        public int getTotalDamageDealtToChampions() {return totalDamageDealtToChampions;}
        public void setTotalDamageDealtToChampions(int totalDamageDealtToChampions) {this.totalDamageDealtToChampions = totalDamageDealtToChampions;}

        public int getGoldEarned() {return goldEarned;}
        public void setGoldEarned(int goldEarned) {this.goldEarned = goldEarned;}

        public int getTotalMinionsKilled() {return totalMinionsKilled;}
        public void setTotalMinionsKilled(int totalMinionsKilled) {this.totalMinionsKilled = totalMinionsKilled;}

        public int getNeutralMinionsKilled() {return neutralMinionsKilled;}
        public void setNeutralMinionsKilled(int neutralMinionsKilled) {this.neutralMinionsKilled = neutralMinionsKilled;}
    }
}
