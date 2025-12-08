package wii.java.wiileague.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import wii.java.wiileague.model.Item;
import wii.java.wiileague.repository.ItemRepository;


@Service
public class ItemSync {

    private final RiotApiService riotApiService;
    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    public ItemSync(RiotApiService riotApiService, 
                    ItemRepository itemRepository, 
                    ObjectMapper objectMapper) {
        this.riotApiService = riotApiService;
        this.itemRepository = itemRepository;
        this.objectMapper = objectMapper;
    }

    public String syncAllItems() {
        try {
            System.out.println("=== Starting item sync ===");
            JsonNode response = riotApiService.getItemData().block();
            System.out.println("Got response from Riot API: " + (response != null));

            if(response == null || !response.has("data")) {
                System.out.println("ERROR: No data in response");
                return "Failed to fetch item data from Riot API";
            }
            JsonNode itemsData = response.get("data");
            System.out.println("Items data node: " + (itemsData != null));

            int synced = 0;
            int failed = 0;

            List<String> itemKeys = new ArrayList<>();
            var propertyNames = itemsData.propertyNames();
            itemKeys.addAll(propertyNames);

            System.out.println("Number of items to sync: " + itemKeys.size());
            

            for(String itemKey : itemKeys) {
                System.out.println("Processing item: " + itemKey);
                try {
                    JsonNode itemData = itemsData.get(itemKey);
                    Item item = objectMapper.treeToValue(itemData, Item.class);

                    item.setItemId(Integer.parseInt(itemKey));

                    itemRepository.findByItemId(item.getItemId()).ifPresent(existing -> item.setId(existing.getId()));

                    itemRepository.save(item);
                    synced++;
                    System.out.println("Synced: " + itemKey);
                } catch(Exception e) {
                    failed++;
                    System.out.println("Failed: " + itemKey + " - " + e.getMessage());
                }
            }

            String result = "Successfully synced " + synced + " items (failed: " + failed + ")";
            System.out.println("=== " + result + " ===");
            return result;

        } catch(Exception e) {
            System.err.println("FATAL ERROR in syncAllItems: " + e.getMessage());
            e.printStackTrace();
            return "Error syncing items: " + e.getMessage();
        } 
    }
}