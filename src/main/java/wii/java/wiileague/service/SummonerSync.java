package wii.java.wiileague.service;

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

    public Summoner syncSummonerByName(String summonerName) {
        try {
            System.out.println("=== Syncing summoner: " + summonerName + " ===");

            JsonNode response = riotApiService.getSummonerByName(summonerName).block();

            if(response == null) {
                System.out.println("ERROR: No response from Riot API");
                return null;
            }

            Summoner summoner = objectMapper.treeToValue(response, Summoner.class);

            summonerRepository.findByPuuid(summoner.getPuuid()).ifPresent(existing -> {
                summoner.setId(existing.getId());
            });

            Summoner saved = summonerRepository.save(summoner);
            System.out.println("Synced summoner: " + saved.getName() + " (Level " + saved.getSummonerLevel() + ")");

            return saved;
        }catch(Exception e) {
            System.err.println("Error syncing summoner " + summonerName + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String syncMultipleSummoners(String... summonerNames){
        int synced = 0;
        int failed = 0;

        for(String name : summonerNames) {
            Summoner result = syncSummonerByName(name);
            if(result != null){
                synced++;
            }else{
                failed++;
            }
        }

        return "Successfully synced " + synced + " summoners (failed: " + failed + ")";
    }
}
