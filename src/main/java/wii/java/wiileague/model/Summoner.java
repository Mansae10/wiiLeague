package wii.java.wiileague.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "summoners")
public class Summoner {
    @Id
    private String id;
    private String puuid;
    private String summonerId;
    private String accountId;
    private String name;
    private int profileIconId;
    private long summonerLevel;

    public Summoner() {}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getPuuid() {return puuid;}
    public void setPuuid(String puuid) {this.puuid = puuid;}

    public String getSummonerId() {return summonerId;}
    public void setSummonerId(String summonerId) {this.summonerId = summonerId;}

    public String getAccountId() {return accountId;}
    public void setAccountId(String accountId) {this.accountId = accountId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getProfileIconId() {return profileIconId;}
    public void setProfileIconId(int profileIconId) {this.profileIconId = profileIconId;}

    public long getSummonerLevel() {return summonerLevel;}
    public void setSummonerLevel(long summonerLevel) {this.summonerLevel = summonerLevel;}

}
