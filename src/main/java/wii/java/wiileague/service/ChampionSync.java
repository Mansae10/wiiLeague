package wii.java.wiileague.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import wii.java.wiileague.model.Champion;
import wii.java.wiileague.repository.ChampionRepository;

@Service
public class ChampionSync {

    private final RiotApiService riotApiService;
    private final ChampionRepository championRepository;
    private final ObjectMapper objectMapper;

    public ChampionSync(RiotApiService riotApiService,
                        ChampionRepository championRepository,
                        ObjectMapper objectMapper) {
        this.riotApiService = riotApiService;
        this.championRepository = championRepository;
        this.objectMapper = objectMapper;
    }

    public String syncAllChampions() {
        try {
            System.out.println("=== Starting champion sync ===");
            JsonNode response = riotApiService.getChampionData().block();
            System.out.println("Got response from Riot API: " + (response != null));

            if(response == null || !response.has("data")) {
                System.out.println("ERROR: No data in response");
                return "Failed to fetch champion data from Riot API";
            }

            JsonNode championsData = response.get("data");
            System.out.println("Champions data node: " + (championsData != null));
            System.out.println("Champions data is object: " + championsData.isObject());
            System.out.println("Number of champions: " + championsData.size());
            
            int synced = 0;
            int failed = 0;
            
             List<String> championKeys = new ArrayList<>();
            var propertyNames = championsData.propertyNames();
            championKeys.addAll(propertyNames);
            
            System.out.println("Number of champions to sync: " + championKeys.size());
            
            for (String championKey : championKeys) {
                System.out.println("Processing champion: " + championKey);
                Champion champion = fetchAndSaveChampionDetails(championKey);
                if (champion != null) {
                    synced++;
                    System.out.println("✓ Synced: " + championKey);
                } else {
                    failed++;
                    System.out.println("✗ Failed: " + championKey);
                }
            }

            String result = "Successfully synced " + synced + " champions (failed: " + failed + ")";
            System.out.println("=== " + result + " ===");
            return result;
            
        } catch (Exception e) {
            System.err.println("FATAL ERROR in syncAllChampions: " + e.getMessage());
            e.printStackTrace();
            return "Error syncing champions: " + e.getMessage();
        }
    }

    public Champion fetchAndSaveChampionDetails(String championKey) {
        try {
            JsonNode response = riotApiService.getChampionDetails(championKey).block();

            if(response == null || !response.has("data")) {
                return null;
            }

            JsonNode championData = response.get("data").get(championKey);
            
            // Use ObjectMapper to convert JsonNode to Champion
            Champion champion = objectMapper.treeToValue(championData, Champion.class);

            // Preserve existing MongoDB ID if champion already exists
            championRepository.findByKey(championKey).ifPresent(existing -> champion.setId(existing.getId()));

            return championRepository.save(champion);

        } catch (Exception e) {
            System.err.println("Error fetching champion " + championKey + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
