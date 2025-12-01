package wii.java.wiileague.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Champion {
    @Id
    private String id;

    private String name;
    private String title;

    private List<String> roles;

    private int difficulty;

    private Stats baseStats;

    public Champion() {this.roles = new ArrayList<>();}

    public String getName() { return name;}
    public void setName(String name) { this.name = name;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public String getId() { return id;}
    public void setId(String id) { this.id = id;}

    public List<String> getRoles() {return roles;}
    public void setRoles(List<String> roles) { this.roles = roles;}
    
    public Stats getBaseStats() {return baseStats;}
    public void setBaseStats(Stats baseStats) {this.baseStats = baseStats;}

    public int getDifficulty() {return difficulty;}
    public void setDifficulty(int difficulty) { this.difficulty = difficulty;}



    public static class Stats {
        private int health;
        private int ad;
        private int armor;
        private int mr;
        private int atkspeed;
        private int mspeed;
        private int atkrange;

        public Stats() {}

        public int getHealth() { return health;}
        public void setHealth(int health) {this.health = health;}

        public int getAD() {return ad;}
        public void setAD(int ad) {this.ad = ad;}

        public int getArmor() {return armor;}
        public void setArmor(int armor) { this.armor = armor;}

        public int getMr() { return mr;}
        public void setMr(int mr) { this.mr = mr;}

        public int getAtkspeed() { return atkspeed;}
        public void setAtkspeed(int atkspeed) { this.atkspeed = atkspeed;}

        public int getMspeed() { return mspeed;}
        public void setMspeed(int mspeed) { this.mspeed = mspeed;}

        public int getAtkrange() { return atkrange;}
        public void setAtkrange(int atkrange) { this.atkrange = atkrange;}

    }
    

    
}
