package wii.java.wiileague.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import wii.java.wiileague.model.Item;
import wii.java.wiileague.repository.ItemRepository;
import wii.java.wiileague.service.ItemSync;
import wii.java.wiileague.service.RiotApiService;




@RestController
@RequestMapping("/api/items")
public class ItemController {
    
    private final RiotApiService riotApiService;
    private final ItemRepository itemRepository;
    private final ItemSync itemSync;

    public ItemController(RiotApiService riotApiService, ItemRepository itemRepository, ItemSync itemSync) {
    this.riotApiService = riotApiService;
    this.itemRepository = itemRepository;
    this.itemSync = itemSync;
    }

    @GetMapping("/riot")
    public Mono<JsonNode> getItemsFromRiot() {
        return riotApiService.getItemData();
    }

    @PostMapping("/sync")
    public String syncItems() {
        return itemSync.syncAllItems();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
        
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Item> getItemById(@PathVariable String id) {
        return itemRepository.findById(id);
    }

    @GetMapping("/search/{name}")
    public List<Item> searchItemsByName(@PathVariable String name) {
        return itemRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/tag/{tag}")
    public List<Item> getItemsByTag(@PathVariable String tag) {
        return itemRepository.findByTagsIn(tag);
    }   

    @GetMapping("/gold/under/{maxGold}")
    public List<Item> getItemsUnderGold(@PathVariable int maxGold) {
        return itemRepository.findByGoldTotalLessThan(maxGold);
    }
    
    @GetMapping("/gold/range")
    public List<Item> getItemsByGoldRange(
            @RequestParam int min, 
            @RequestParam int max) {
        return itemRepository.findByGoldTotalBetween(min, max);
    }
    
    @GetMapping("/gold/over/{minGold}")
    public List<Item> getItemsOverGold(@PathVariable int minGold) {
        return itemRepository.findByGoldGreaterThan(minGold);
    }

}
