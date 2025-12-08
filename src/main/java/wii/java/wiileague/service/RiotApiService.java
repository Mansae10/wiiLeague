package wii.java.wiileague.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Service
public class RiotApiService {
    private final WebClient webClient;

    @Value("${riot.api.key}")
    private String apiKey;

    private static final String RIOT_API_BASE = "https://na1.api.riotgames.com";
    private static final String AMERICAS_API_BASE = "https://americas.api.riotgames.com";
    private static final String DDRAGON_BASE = "https://ddragon.leagueoflegends.com/cdn/14.23.1";
    private static final String TOKEN = "X-Riot-Token";

    public RiotApiService() {
    ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
        .build();
    
    this.webClient = WebClient.builder()
        .exchangeStrategies(strategies)
        .build();
}

    public Mono<JsonNode> getAccountByRiotId(String gameName, String tagLine) {
        String url = AMERICAS_API_BASE + "/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine;
        return webClient.get().uri(url).header(TOKEN, apiKey).retrieve().bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> getSummonerByPuuid(String puuid) {
        String url = RIOT_API_BASE + "/lol/summoner/v4/summoners/by-puuid/" + puuid;
        return webClient.get().uri(url).header(TOKEN, apiKey).retrieve().bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> getSummonerByName(String summonerName) {
        String url = RIOT_API_BASE + "/lol/summoner/v4/summoners/by-name/" + summonerName;
        return webClient.get().uri(url).header(TOKEN, apiKey).retrieve().bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> getMatchIdsByPuuid(String puuid, int count) {
        String url = AMERICAS_API_BASE + "/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=" + count;
        return webClient.get().uri(url).header(TOKEN, apiKey).retrieve().bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> getMatchDetails(String matchId) {
        String url = AMERICAS_API_BASE + "/lol/match/v5/matches/" + matchId;
        return webClient.get().uri(url).header(TOKEN, apiKey).retrieve().bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> getChampionData() {
        String url = DDRAGON_BASE + "/data/en_US/champion.json";
        return webClient.get().uri(url).retrieve().bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> getItemData() {
        String url = DDRAGON_BASE + "/data/en_US/item.json";
        return webClient.get().uri(url).retrieve().bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> getChampionDetails(String championName) {
        String url = DDRAGON_BASE + "/data/en_US/champion/" + championName + ".json";
        return webClient.get().uri(url).retrieve().bodyToMono(JsonNode.class);
    }
}


