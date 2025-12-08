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
    private Gold gold;
    private List<String> tags;

    public Item() {}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public int getItemId() {return itemId;}
    public void setItemId(int itemId) {this.itemId = itemId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Gold getGold() {return gold;}
    public void setGold(Gold gold) {this.gold = gold;}

    public List<String> getTags() {return tags;}
    public void setTags(List<String> tags) {this.tags = tags;}

    public static class Gold {
        private int base;
        private int total;
        private int sell;
        private boolean purchasable;

        public Gold() {}

        public int getBase() {return base;}
        public void setBase(int base) {this.base = base;}

        public int getTotal() {return total;}
        public void setTotal(int total) {this.total = total;}

        public int getSell() {return sell;}
        public void setSell(int sell) {this.sell = sell;}

        public boolean isPurchasable() {return purchasable;}
        public void setPurchasable(boolean purchasable) {this.purchasable = purchasable;}
    }
}