package wii.java.wiileague.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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
import wii.java.wiileague.repository.MatchRepository;
import wii.java.wiileague.service.RiotApiService;



@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final RiotApiService riotApiService;
    private final MatchRepository matchRepository;

    public MatchController(RiotApiService riotApiService, MatchRepository matchRepository) {
        this.riotApiService = riotApiService;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/riot/{puuid}")
    public Mono<JsonNode> getMatchIdsFromRiot(@PathVariable String puuid, @RequestParam(defaultValue = "5") int count) {
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

    @GetMapping
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Match> getMatchById(@PathVariable String id) {
        return matchRepository.findById(id);
    }

    @GetMapping("/mode/{gameMode}")
    public List<Match> getMatchesByGameMode(@PathVariable String gameMode) {
        return matchRepository.findByGameMode(gameMode);
    }
    
}
