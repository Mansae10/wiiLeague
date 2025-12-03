package wii.java.wiileague.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import wii.java.wiileague.model.Match;

public interface MatchRepository extends MongoRepository<Match, String> {
    
    Optional<Match> findByMatchId(String matchId);

    List<Match> findByGameMode(String gameMode);
}
