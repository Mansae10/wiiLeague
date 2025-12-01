package wii.java.wiileague.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import wii.java.wiileague.model.Champion;
import wii.java.wiileague.repository.ChampionRepository;




@RestController
@RequestMapping("/api/champions")
public class ChampionController {

    private final ChampionRepository championRepository;

    public ChampionController(ChampionRepository championRepository){
        this.championRepository = championRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Champion createChampion(@RequestBody Champion champion) {
        //TODO: process POST request
        
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
    
    @GetMapping("/role/{role}")
    public List<Champion> getChampionsByRole(@PathVariable String role) {
        return championRepository.findByRolesIn(role);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChampion(@PathVariable String id) {
        championRepository.deleteById(id);
    }
    

}
