package wii.java.wiileague.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import wii.java.wiileague.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

    Optional<Item> findByItemId(int itemId);

    List<Item> findByTagsIn(String tag);
    
    List<Item> findByNameContainingIgnoreCase(String name);

    List<Item> findByGoldTotalLessThan(int maxGold);

    List<Item> findByGoldTotalBetween(int minGold, int maxGold);

    List<Item> findByGoldGreaterThan(int minGold);
    
} 
