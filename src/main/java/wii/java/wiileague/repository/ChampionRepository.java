package wii.java.wiileague.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import wii.java.wiileague.model.Champion;

public interface ChampionRepository extends MongoRepository<Champion, String>{

    Optional<Champion> findByName(String name);

    Optional<Champion> findByKey(String key);

    Optional<Champion> findByChampionId(String championId);

    List<Champion> findByTagsContaining(String tag);

    List<Champion> findByTagsIn(List<String> tags);

    List<Champion> findByDifficulty(int difficulty);

    List<Champion> findByNameContainingIgnoreCase(String name);

}
