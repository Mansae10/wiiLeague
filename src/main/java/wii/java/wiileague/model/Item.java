package wii.java.wiileague.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "items")
public class Item {
    @Id
    private String id;
    private int itemId;
    private String name;
    private String description;
    private int gold;
    private List<String> tags;
    private Stats stats;

    public Item() {}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public int getItemId() {return itemId;}
    public void setItemId(int itemId) {this.itemId = itemId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public int getGold() {return gold;}
    public void setGold(int gold) {this.gold = gold;}

    public List<String> getTags() {return tags;}
    public void setTags(List<String> tags) {this.tags = tags;}

    public Stats getStats() {return stats;}
    public void setStats(Stats stats) {this.stats = stats;}

    public static class Stats {
        private int attackDamage;
        private int abilityPower;
        private int armor;
        private int magicResist;
        private int health;
        private int mana;
        private int abilityHaste;
        private int moveSpeed;
        private int lethality;
        private int flatMPen;
        private int percentMPen;
        private int healthRegen;
        private int manaRegen;
        private int attackRange;
        private int critChance;
        private int critDamage;
        private int attackSpeed;
        private int lifesteal;
        private int healAndShieldPower;
        private int omnivamp;
        private int tenacity;

        public Stats () {}

        public int getAttackDamage() {return attackDamage;}
        public void setAttackDamage(int attackDamage) {this.attackDamage = attackDamage;}

        public int getAbilityPower() {return abilityPower;}
        public void setAbilityPower(int abilityPower) {this.abilityPower = abilityPower;}

        public int getArmor() {return armor;}
        public void setArmor(int armor) {this.armor = armor;}

        public int getMagicResist() {return magicResist;}
        public void setMagicResist(int magicResist) {this.magicResist = magicResist;}

        public int getHealth() {return health;}
        public void setHealth(int health) {this.health = health;}

        public int getMana() {return mana;}
        public void setMana(int mana) {this.mana = mana;}

        public int getAbilityHaste() {return abilityHaste;}
        public void setAbilityHaste(int abilityHaste) {this.abilityHaste = abilityHaste;}

        public int getMoveSpeed() {return moveSpeed;}
        public void setMoveSpeed(int moveSpeed) {this.moveSpeed = moveSpeed;}

        public int getLethality() {return lethality;}
        public void setLethality(int lethality) {this.lethality = lethality;}

        public int getFlatMPen() {return flatMPen;}
        public void setFlatMPen(int flatMPen) {this.flatMPen = flatMPen;}

        public int getPercentMPen() {return percentMPen;}
        public void setPercentMPen(int percentMPen) {this.percentMPen = percentMPen;}

        public int getHealthRegen() {return healthRegen;}
        public void setHealthRegen(int healthRegen) {this.healthRegen = healthRegen;}

        public int getManaRegen() {return manaRegen;}
        public void setManaRegen(int manaRegen) {this.manaRegen = manaRegen;}

        public int getAttackRange() {return attackRange;}
        public void setAttackRange(int attackRange) {this.attackRange = attackRange;}

        public int getCritChance() {return critChance;}
        public void setCritChance(int critChance) {this.critChance = critChance;}

        public int getCritDamage() {return critDamage;}
        public void setCritDamage(int critDamage) {this.critDamage = critDamage;}

        public int getAttackSpeed() {return attackSpeed;}
        public void setAttackSpeed(int attackSpeed) {this.attackSpeed = attackSpeed;}

        public int getLifesteal() {return lifesteal;}
        public void setLifesteal(int lifesteal) {this.lifesteal = lifesteal;}

        public int getHealAndShieldPower() {return healAndShieldPower;}
        public void setHealAndShieldPower(int healAndShieldPower) {this.healAndShieldPower = healAndShieldPower;}

        public int getOmnivamp() {return omnivamp;}
        public void setOmnivamp(int omnivamp) {this.omnivamp = omnivamp;}

        public int getTenacity() {return tenacity;}
        public void setTenacity(int tenacity) {this.tenacity = tenacity;}

    }
}
