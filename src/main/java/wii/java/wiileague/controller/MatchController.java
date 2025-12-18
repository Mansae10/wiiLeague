package wii.java.wiileague.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import wii.java.wiileague.model.Match;
import wii.java.wiileague.model.Summoner;
import wii.java.wiileague.repository.MatchRepository;
import wii.java.wiileague.repository.SummonerRepository;
import wii.java.wiileague.service.MatchSync;
import wii.java.wiileague.service.RiotApiService;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final RiotApiService riotApiService;
    private final MatchRepository matchRepository;
    private final SummonerRepository summonerRepository;
    private final MatchSync matchSync;

    public MatchController(RiotApiService riotApiService, 
                          MatchRepository matchRepository,
                          SummonerRepository summonerRepository,
                          MatchSync matchSync) {
        this.riotApiService = riotApiService;
        this.matchRepository = matchRepository;
        this.summonerRepository = summonerRepository;
        this.matchSync = matchSync;
    }


    @PostMapping("/sync/riotid/{gameName}/{tagLine}")
    public String syncMatchesByRiotId(@PathVariable String gameName,
                                      @PathVariable String tagLine,
                                      @RequestParam(defaultValue = "20") int count) {
        return matchSync.syncMatchesByRiotId(gameName + "#" + tagLine, count);
    }


    @PostMapping("/sync/puuid/{puuid}")
    public String syncMatchesByPuuid(@PathVariable String puuid, 
                                     @RequestParam(defaultValue = "20") int count) {
        return matchSync.syncMatchesByPuuid(puuid, count);
    }

    @PostMapping("/sync/{puuid}")
    public String syncMatches(@PathVariable String puuid, 
                              @RequestParam(defaultValue = "5") int count) {
        return matchSync.syncMatchesByPuuid(puuid, count);
    }


    @GetMapping("/history/riotid/{gameName}/{tagLine}")
    public ResponseEntity<List<Match>> getMatchHistoryByRiotId(@PathVariable String gameName,
                                                                @PathVariable String tagLine) {
        Summoner summoner = summonerRepository
            .findByGameNameAndTagLine(gameName, tagLine)
            .orElse(null);
        
        if (summoner == null) {
            return ResponseEntity.notFound().build();
        }
        
        List<Match> matches = matchRepository.findAll().stream()
            .filter(match -> match.getParticipants() != null &&
                   match.getParticipants().stream()
                       .anyMatch(p -> summoner.getPuuid().equals(p.getPuuid())))
            .toList();
        
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/history/puuid/{puuid}")
    public List<Match> getMatchHistoryByPuuid(@PathVariable String puuid) {
        return matchRepository.findAll().stream()
            .filter(match -> match.getParticipants() != null &&
                   match.getParticipants().stream()
                       .anyMatch(p -> puuid.equals(p.getPuuid())))
            .toList();
    }


    @GetMapping
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Optional<Match> getMatchById(@PathVariable String id) {
        return matchRepository.findById(id);
    }

    @GetMapping("/matchid/{matchId}")
    public ResponseEntity<Match> getMatchByMatchId(@PathVariable String matchId) {
        return matchRepository.findByMatchId(matchId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/mode/{gameMode}")
    public List<Match> getMatchesByGameMode(@PathVariable String gameMode) {
        return matchRepository.findByGameMode(gameMode);
    }

    @DeleteMapping("/clear")
    public String clearAllMatches() {
        long count = matchRepository.count();
        matchRepository.deleteAll();
        return "Deleted " + count + " matches";
    }

    @DeleteMapping("/matchid/{matchId}")
    public ResponseEntity<String> deleteMatchByMatchId(@PathVariable String matchId) {
        Optional<Match> match = matchRepository.findByMatchId(matchId);
        if (match.isPresent()) {
            matchRepository.delete(match.get());
            return ResponseEntity.ok("Deleted match: " + matchId);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/riot/puuid/{puuid}")
    public Mono<JsonNode> getMatchIdsFromRiot(@PathVariable String puuid, 
                                              @RequestParam(defaultValue = "20") int count) {
        return riotApiService.getMatchIdsByPuuid(puuid, count);
    }


    @GetMapping("/riot/details/{matchId}")
    public Mono<JsonNode> getMatchDetailsFromRiot(@PathVariable String matchId) {
        return riotApiService.getMatchDetails(matchId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Match createMatch(@RequestBody Match match) {
        return matchRepository.save(match);
    }
}