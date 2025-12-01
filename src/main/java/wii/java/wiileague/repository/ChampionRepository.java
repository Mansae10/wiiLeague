package wii.java.wiileague.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import wii.java.wiileague.model.Champion;

public interface ChampionRepository extends MongoRepository<Champion, String>{

    List<Champion> findByName(String name);

    List<Champion> findByRolesIn(String role);
}
