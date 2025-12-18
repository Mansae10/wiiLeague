package wii.java.wiileague.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import wii.java.wiileague.model.Summoner;
import wii.java.wiileague.repository.SummonerRepository;
import wii.java.wiileague.service.RiotApiService;
import wii.java.wiileague.service.SummonerSync;





@RestController
@RequestMapping("/api/summoners")
public class SummonerController {
    
    private final SummonerRepository summonerRepository;
    private final SummonerSync summonerSync;
    private final RiotApiService riotApiService;

    public SummonerController(SummonerRepository summonerRepository,
                              SummonerSync summonerSync,
                              RiotApiService riotApiService) {
        this.summonerRepository = summonerRepository;
        this.summonerSync = summonerSync;
        this.riotApiService = riotApiService;
    }

    @PostMapping("/sync/riotid/{gameName}/{tagLine}")
    public ResponseEntity<Summoner> syncByRiotId(@PathVariable String gameName, 
                                                  @PathVariable String tagLine) {
        Summoner summoner = summonerSync.syncSummonerByRiotId(gameName + "#" + tagLine);
        
        if (summoner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.ok(summoner);
    }

    @PostMapping("/sync/multiple")
    public String syncMultipleSummoners(@RequestBody List<String> riotIds) {
        return summonerSync.syncMultipleSummoners(riotIds.toArray(new String[0]));
    }

    @GetMapping("/riotid/{gameName}/{tagLine}")
    public ResponseEntity<Summoner> getSummonerByRiotId(@PathVariable String gameName, 
                                                         @PathVariable String tagLine) {
        Summoner summoner = summonerSync.getSummoner(gameName, tagLine);
        
        if (summoner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.ok(summoner);
    }

    @GetMapping("/puuid/{puuid}")
    public ResponseEntity<Summoner> getSummonerByPuuid(@PathVariable String puuid) {
        return summonerRepository.findByPuuid(puuid)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public List<Summoner> getAllSummoners() {
        return summonerRepository.findAll();
    }

    @GetMapping("/puuid/{puuid}/matches")
    public Mono<JsonNode> getMatchIds(@PathVariable String puuid,
                                      @RequestParam(defaultValue = "20") int count) {
        return riotApiService.getMatchIdsByPuuid(puuid, count);
    }
}