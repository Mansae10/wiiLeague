package wii.java.wiileague.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "summoners")
public class Summoner {
    @Id
    private String id;

    private String puuid;  
    private String gameName;
    private String tagLine;
    
    private String name;  
    private int profileIconId;
    private long summonerLevel;

    private LocalDateTime lastUpdated;

    public Summoner() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPuuid() { return puuid; }
    public void setPuuid(String puuid) { this.puuid = puuid; }

    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }

    public String getTagLine() { return tagLine; }
    public void setTagLine(String tagLine) { this.tagLine = tagLine; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getProfileIconId() { return profileIconId; }
    public void setProfileIconId(int profileIconId) { this.profileIconId = profileIconId; }

    public long getSummonerLevel() { return summonerLevel; }
    public void setSummonerLevel(long summonerLevel) { this.summonerLevel = summonerLevel; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    
    public String getRiotId() {
        return gameName + "#" + tagLine;
    }
    
}
