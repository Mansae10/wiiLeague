package wii.java.wiileague.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "champions")
public class Champion {
    @Id
    private String id;

    private String championId;
    private String key;
    private String name;
    private String title;

    private List<String> tags;
    private List<Spell> spells;
    private Passive passive;
    private List<Skin> skins;
    private int difficulty;

    private Image image;

    private Stats stats;

    public Champion() {this.tags = new ArrayList<>();}

    public String getId() { return id;}
    public void setId(String id) { this.id = id;}

    public String getChampionId() {return championId;}
    public void setChampionId(String championId) {this.championId = championId; }
    
    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}

    public String getName() { return name;}
    public void setName(String name) { this.name = name;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public List<String> getTags() {return tags;}
    public void setTags(List<String> tags) { this.tags = tags;}
    
    public Stats getStats() {return stats;}
    public void setStats(Stats stats) {this.stats = stats;}

    public int getDifficulty() {return difficulty;}
    public void setDifficulty(int difficulty) { this.difficulty = difficulty;}

    public List<Spell> getSpells() {return spells;}

    public void setSpells(List<Spell> spells) {this.spells = spells;}

    public List<Skin> getSkins() {return skins;}

    public void setSkins(List<Skin> skins) {this.skins = skins;}

    public Passive getPassive() {return passive;}

    public void setPassive(Passive passive) {this.passive = passive;}

    public Image getImage() {return image;}

    public void setImage(Image image) {this.image = image;}



    public static class Stats {
        private double hp;
        private double mp;
        private double armor;
        private double spellblock; // MR
        private double attackdamage;
        private double attackspeed;
        private double movespeed;
        private double attackrange;

        public Stats() {}

        public double getHp() { return hp; }
        public void setHp(double hp) { this.hp = hp; }
        
        public double getMp() { return mp; }
        public void setMp(double mp) { this.mp = mp; }
        
        public double getArmor() { return armor; }
        public void setArmor(double armor) { this.armor = armor; }
        
        public double getSpellblock() { return spellblock; }
        public void setSpellblock(double spellblock) { this.spellblock = spellblock; }
        
        public double getAttackdamage() { return attackdamage; }
        public void setAttackdamage(double attackdamage) { this.attackdamage = attackdamage; }
        
        public double getAttackspeed() { return attackspeed; }
        public void setAttackspeed(double attackspeed) { this.attackspeed = attackspeed; }
        
        public double getMovespeed() { return movespeed; }
        public void setMovespeed(double movespeed) { this.movespeed = movespeed; }
        
        public double getAttackrange() { return attackrange; }
        public void setAttackrange(double attackrange) { this.attackrange = attackrange; }

    }

    public static class Skin {
        private String id;
        private int num;
        private String name;

        public Skin() {}

        public String getId() {return id;}
        public void setId(String id){this.id = id;}

        public String getName() {return name;}
        public void setName(String name) {this.name = name;}

        public int getNum() {return num;}
        public void setNum(int num) {this.num = num;}
    }

    public static class Passive {
        private String name;
        private String description;
        private Image image;

        public Passive() {}

        public String getName() {return name;}
        public void setName(String name) {this.name = name;}

        public String getDescription() {return description;}
        public void setDescription(String description) {this.description = description;}

        public Image getImage() {return image;}
        public void setImage(Image image) {this.image = image;}
        
        
    }

    public static class Image {
        private String full;
        private String sprite;
        private String group;
        private int x;
        private int y;
        private int w;
        private int h;

        public Image() {}

        public String getFull() {return full;}
        public void setFull(String full) {this.full = full;}

        public String getSprite() {return sprite;}
        public void setSprite(String sprite) {this.sprite = sprite;}

        public String getGroup() {return group;}
        public void setGroup(String group) {this.group = group;}

        public int getX() {return x;}
        public void setX(int x) {this.x = x;}

        public int getY() {return y;}
        public void setY(int y) {this.y = y;}

        public int getW() {return w;}
        public void setW(int w) {this.w = w;}

        public int getH() {return h;}
        public void setH(int h) {this.h = h;}

    }
    
    public static class Spell {
        private String id;
        private String name;
        private String description;
        private Image image;
        
        public Spell() {}

        public String getId() {return id;}
        public void setId(String id) {this.id = id;}

        public String getName() {return name;}
        public void setName(String name) {this.name = name;}

        public String getDescription() {return description;}
        public void setDescription(String description) {this.description = description;}

        public Image getImage() {return image;}
        public void setImage(Image image) {this.image = image;}
        
    }

    
}
