package wii.java.wiileague.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import wii.java.wiileague.model.Summoner;

public interface SummonerRepository extends MongoRepository<Summoner, String> {

    Optional<Summoner> findByPuuid(String puuid);

    Optional<Summoner> findByName(String name);

    Optional<Summoner> findBySummonerId(String summonerId);
    
}
