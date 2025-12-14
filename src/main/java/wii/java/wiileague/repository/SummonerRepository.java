package wii.java.wiileague.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import wii.java.wiileague.model.Summoner;

public interface SummonerRepository extends MongoRepository<Summoner, String> {

    Optional<Summoner> findByGameNameAndTagLine(String gameName, String tagLine);

    Optional<Summoner> findByPuuid(String puuid);
    
}
