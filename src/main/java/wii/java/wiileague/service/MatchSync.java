package wii.java.wiileague.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import wii.java.wiileague.model.Match;
import wii.java.wiileague.repository.MatchRepository;

@Service
public class MatchSync {
    private final RiotApiService riotApiService;
    private final MatchRepository matchRepository;
    private final ObjectMapper objectMapper;

    public MatchSync(RiotApiService riotapiService, MatchRepository matchRepository, ObjectMapper objectMapper) {
        this.riotApiService = riotapiService;
        this.matchRepository = matchRepository;
        this.objectMapper = objectMapper;
        
    }

    public String syncMatchesByPuuid(String puuid, int  count) {
        try {
            System.out.println("=== Starting match sync for PUUID: " + puuid + " ===");

            JsonNode matchIdsResponse = riotApiService.getMatchIdsByPuuid(puuid, count).block();

            if(matchIdsResponse == null || !matchIdsResponse.isArray()) {
                return "Failed to fetch match IDs from Riot API";
            }

            List<String> matchIds = new ArrayList<>();
            matchIdsResponse.forEach(node -> matchIds.add(node.asText()));
            System.out.println("Found " + matchIds.size() + " matches to sync");

            int synced = 0;
            int failed = 0;
            int skipped = 0;

            for(String matchId : matchIds) {
                if(matchRepository.findByMatchId(matchId).isPresent()) {
                    skipped++;
                    System.out.println("Skipped (already exists): " + matchId);
                    continue;
                }

                System.out.println("Processing match: " + matchId);
                Match match = fetchAndSaveMatchDetails(matchId);
                if(match != null){
                    synced++;
                    System.out.println("Synced: " + matchId);
                }else {
                    failed++;
                    System.out.println("Failed: " + matchId);
                }
            }
            String result = "Successfully synced " + synced + " matches (failed: " + failed  + ", skipped: " + skipped + ")";
            System.out.println("=== " + result + " ===");
            return result;
        }catch(Exception e) {
            System.err.println("FATAL ERROR in syncMatchesByPuuid: " + e.getMessage());
            e.printStackTrace();
            return "Error syncing matches: " + e.getMessage();
        }
    }

    public Match fetchAndSaveMatchDetails(String matchId) {
        try {
            JsonNode response = riotApiService.getMatchDetails(matchId).block();

            if(response == null) {
                return null;
            }

            Match match = objectMapper.treeToValue(response, Match.class);

            matchRepository.findByMatchId(matchId).ifPresent(existing -> {
                match.setId(existing.getId());
            });

            return matchRepository.save(match);

        }catch (Exception e) {
            System.err.println("Error fetching match " + matchId + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
