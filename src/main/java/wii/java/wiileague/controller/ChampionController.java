package wii.java.wiileague.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wii.java.wiileague.model.Champion;
import wii.java.wiileague.repository.ChampionRepository;
import wii.java.wiileague.service.ChampionSync;

@RestController
@RequestMapping("/api/champions")
public class ChampionController {

    private final ChampionRepository championRepository;
    private final ChampionSync championSync;

    public ChampionController(ChampionRepository championRepository,
                             ChampionSync championSync) {
        this.championRepository = championRepository;
        this.championSync = championSync;
    }

    // Sync all champions from Riot API
    @PostMapping("/sync")
    public String syncChampions() {
        return championSync.syncAllChampions();
    }
    
    // Sync specific champion from Riot API
    @PostMapping("/sync/{championKey}")
    public Champion syncSpecificChampion(@PathVariable String championKey) {
        return championSync.fetchAndSaveChampionDetails(championKey);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Champion createChampion(@RequestBody Champion champion) {
        return championRepository.save(champion);
    }

    @GetMapping
    public List<Champion> getAllChampions() {
        return championRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Champion> getChampionById(@PathVariable String id) {
        return championRepository.findById(id);
    }
    
    @GetMapping("/name/{name}")
    public Optional<Champion> getChampionByName(@PathVariable String name) {
        return championRepository.findByName(name);
    }
    
    @GetMapping("/key/{key}")
    public Optional<Champion> getChampionByKey(@PathVariable String key) {
        return championRepository.findByKey(key);
    }
    
    @GetMapping("/tag/{tag}")
    public List<Champion> getChampionsByTag(@PathVariable String tag) {
        return championRepository.findByTagsContaining(tag);
    }
    
    @GetMapping("/difficulty/{difficulty}")
    public List<Champion> getChampionsByDifficulty(@PathVariable int difficulty) {
        return championRepository.findByInfoDifficulty(difficulty);
    }
    
    @GetMapping("/search/{name}")
    public List<Champion> searchChampionsByName(@PathVariable String name) {
        return championRepository.findByNameContainingIgnoreCase(name);
    }
    
    @PutMapping("/{id}")
    public Champion updateChampion(@PathVariable String id, @RequestBody Champion champion) {
        champion.setId(id);
        return championRepository.save(champion);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChampion(@PathVariable String id) {
        championRepository.deleteById(id);
    }
}