package wii.java.wiileague.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import wii.java.wiileague.model.Summoner;
import wii.java.wiileague.repository.SummonerRepository;
import wii.java.wiileague.service.RiotApiService;




@RestController
@RequestMapping("/api/summoners")
public class SummonerController {
    
    private final RiotApiService riotApiService;
    private final SummonerRepository summonerRepository;

    public SummonerController(RiotApiService riotApiService, SummonerRepository summonerRepository){
        this.riotApiService = riotApiService;
        this.summonerRepository = summonerRepository;
    }

    @GetMapping("/riot/{summonerName}")
    public Mono<JsonNode> getSummonerFromRiot(@PathVariable String summonerName) {
        return riotApiService.getSummonerByName(summonerName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Summoner createSummoner(@RequestBody Summoner summoner) {
        return summonerRepository.save(summoner);
    }

    @GetMapping
    public List<Summoner> getAllSummoners() {
        return summonerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Summoner> getSummonerById(@PathVariable String id) {
        return summonerRepository.findById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<Summoner> getSummonerByName(@PathVariable String name) {
        return summonerRepository.findByName(name);
    }   
    
}
