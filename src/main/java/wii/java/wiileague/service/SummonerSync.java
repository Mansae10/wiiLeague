package wii.java.wiileague.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import wii.java.wiileague.model.Summoner;
import wii.java.wiileague.repository.SummonerRepository;

@Service
public class SummonerSync {
    private final RiotApiService riotApiService;
    private final SummonerRepository summonerRepository;
    private final ObjectMapper objectMapper;

    public SummonerSync(RiotApiService riotApiService, SummonerRepository summonerRepository, ObjectMapper objectMapper) {

        this.riotApiService = riotApiService;
        this.summonerRepository = summonerRepository;
        this.objectMapper = objectMapper;
        
    }

    public Summoner syncSummonerByRiotId(String riotId) {
        try {
            String[] parts = riotId.split("#");
            if(parts.length != 2) {
                System.err.println("ERROR: Invalid Riot ID format. Use: gameName#tagLine");
                return null;
            }
            
            String gameName = parts[0];
            String tagLine = parts[1];

            System.out.println("=== Syncing summoner: " + riotId + " ===");

            JsonNode accountResponse = riotApiService.getAccountByRiotId(gameName, tagLine).block();

            if(accountResponse == null) {
                System.err.println("ERROR: No account found for " + riotId);
                return null;
            }

            String puuid = accountResponse.get("puuid").asText();
            System.out.println("Found PUUID: " + puuid);

            JsonNode summonerResponse = riotApiService.getSummonerByPuuid(puuid).block();

            if(summonerResponse == null) {
                System.err.println("ERROR: No summoner data found for PUUID " + puuid);
                return null;
            }

            System.out.println("Summoner Response: " + summonerResponse.toString());
            

            Summoner summoner = summonerRepository.findByGameNameAndTagLine(gameName, tagLine).orElse(new Summoner());

            summoner.setGameName(gameName);
            summoner.setTagLine(tagLine);
            summoner.setPuuid(puuid);
            summoner.setProfileIconId(summonerResponse.get("profileIconId").asInt());
            summoner.setSummonerLevel(summonerResponse.get("summonerLevel").asLong());
            summoner.setLastUpdated(LocalDateTime.now());

            if(summonerResponse.has("name")){
                summoner.setName(summonerResponse.get("name").asText());
                summoner.setName(gameName + "#" + tagLine);
            }else {
                System.err.println("WARNING: 'name' field not found in summoner response");
            }

            Summoner saved = summonerRepository.save(summoner);
            System.out.println("Synced summoner: " + saved.getName() + " (Level " + saved.getSummonerLevel());

            return saved;

        } catch (Exception e) {
            System.err.println("Error syncing summoner " + riotId + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String syncMultipleSummoners(String... riotIds) {
        int synced = 0;
        int failed = 0;

        for (String riotId : riotIds) {
            Summoner result = syncSummonerByRiotId(riotId);
            if (result != null) {
                synced++;
            } else {
                failed++;
            }
        }

        return "Successfully synced " + synced + " summoners (failed: " + failed + ")";
    }

    public Summoner getSummoner(String gameName, String tagLine) {
        Optional<Summoner> existing = summonerRepository.findByGameNameAndTagLine(gameName, tagLine);

        if(existing.isPresent()) {
            Summoner summoner = existing.get();

            if(summoner.getLastUpdated() != null && 
               summoner.getLastUpdated().isBefore(LocalDateTime.now().minusHours(1))) {
                System.out.println("Data is stale, refreshing...");
                return syncSummonerByRiotId(gameName + "#" + tagLine);
            }

            return summoner;
        }

        return syncSummonerByRiotId(gameName + "#" + tagLine);
    }

    public Summoner getSummonerByPuuid(String puuid) {
        return summonerRepository.findByPuuid(puuid).orElse(null);
    }
}