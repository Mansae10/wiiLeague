package wii.java.wiileague.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import wii.java.wiileague.model.Match;
import wii.java.wiileague.model.Summoner;
import wii.java.wiileague.repository.MatchRepository;
import wii.java.wiileague.repository.SummonerRepository;

@Service
public class MatchSync {

    private final RiotApiService riotApiService;
    private final MatchRepository matchRepository;
    private final SummonerRepository summonerRepository;
    private final ObjectMapper objectMapper;

    public MatchSync(RiotApiService riotapiService, MatchRepository matchRepository, SummonerRepository summonerRepository, ObjectMapper objectMapper) {
        this.riotApiService = riotapiService;
        this.matchRepository = matchRepository;
        this.summonerRepository = summonerRepository;
        this.objectMapper = objectMapper;

    }

    public String syncMatchesByRiotId(String riotId, int count) {
        String[] parts = riotId.split("#");
        if (parts.length != 2) {
            return "Invalid Riot ID format. Use: gameName#tagLine";
        }

        Summoner summoner = summonerRepository
                .findByGameNameAndTagLine(parts[0], parts[1])
                .orElse(null);

        if (summoner == null) {
            return "Summoner not found in database. Please sync summoner first: " + riotId;
        }

        return syncMatchesByPuuid(summoner.getPuuid(), count);
    }

    public String syncMatchesByPuuid(String puuid, int count) {
        try {
            System.out.println("=== Starting match sync for PUUID: " + puuid + " ===");

            JsonNode matchIdsResponse = riotApiService.getMatchIdsByPuuid(puuid, count).block();

            if (matchIdsResponse == null || !matchIdsResponse.isArray()) {
                return "Failed to fetch match IDs from Riot API";
            }

            List<String> matchIds = new ArrayList<>();
            matchIdsResponse.forEach(node -> matchIds.add(node.asText()));
            System.out.println("Found " + matchIds.size() + " matches to sync");

            int synced = 0;
            int failed = 0;
            int skipped = 0;

            for (String matchId : matchIds) {
                if (matchRepository.findByMatchId(matchId).isPresent()) {
                    skipped++;
                    System.out.println("Skipped (already exists): " + matchId);
                    continue;
                }

                System.out.println("Processing match: " + matchId);
                Match match = fetchAndSaveMatchDetails(matchId);
                if (match != null) {
                    synced++;
                    System.out.println("✓ Synced: " + matchId);
                } else {
                    failed++;
                    System.out.println("✗ Failed: " + matchId);
                }
            }

            String result = "Successfully synced " + synced + " matches (failed: " + failed + ", skipped: " + skipped + ")";
            System.out.println("=== " + result + " ===");
            return result;

        } catch (Exception e) {
            System.err.println("FATAL ERROR in syncMatchesByPuuid: " + e.getMessage());
            e.printStackTrace();
            return "Error syncing matches: " + e.getMessage();
        }
    }

    public Match fetchAndSaveMatchDetails(String matchId) {
        try {
            JsonNode response = riotApiService.getMatchDetails(matchId).block();

            if (response == null) {
                System.err.println("No response for match: " + matchId);
                return null;
            }

            Match match = matchRepository.findByMatchId(matchId).orElse(new Match());

            JsonNode metadata = response.get("metadata");
            if (metadata != null && metadata.has("matchId")) {
                match.setMatchId(metadata.get("matchId").asText());
                System.out.println("Set matchId: " + metadata.get("matchId").asText());
            } else {
                match.setMatchId(matchId);
            }

            JsonNode info = response.get("info");
            if (info == null) {
                System.err.println("ERROR: No 'info' field in match response for " + matchId);
                return null;
            }


            if (info.has("gameMode")) {
                match.setGameMode(info.get("gameMode").asText());
                System.out.println("Set gameMode: " + info.get("gameMode").asText());
            }

            if (info.has("gameDuration")) {
                match.setGameDuration(info.get("gameDuration").asLong());
                System.out.println("Set gameDuration: " + info.get("gameDuration").asLong());
            }

            if (info.has("gameCreation")) {
                match.setGameCreation(info.get("gameCreation").asLong());
                System.out.println("Set gameCreation: " + info.get("gameCreation").asLong());
            }

            if (info.has("participants")) {
                List<Match.Participant> participants = new ArrayList<>();
                JsonNode participantsNode = info.get("participants");

                System.out.println("Parsing " + participantsNode.size() + " participants...");

                for (JsonNode pNode : participantsNode) {
                    Match.Participant participant = new Match.Participant();

                    if (pNode.has("puuid")) {
                        participant.setPuuid(pNode.get("puuid").asText());
                    }
                    if (pNode.has("summonerName")) {
                        participant.setSummonerName(pNode.get("summonerName").asText());
                    }
                    if (pNode.has("riotIdGameName")) {
                        participant.setRiotIdGameName(pNode.get("riotIdGameName").asText());
                    }
                    if (pNode.has("riotIdTagline")) {
                        participant.setRiotIdTagline(pNode.get("riotIdTagline").asText());
                    }


                    if (pNode.has("championName")) {
                        participant.setChampionName(pNode.get("championName").asText());
                    }
                    if (pNode.has("championId")) {
                        participant.setChampionId(pNode.get("championId").asInt());
                    }
                    if (pNode.has("champLevel")) {
                        participant.setChampLevel(pNode.get("champLevel").asInt());
                    }

                    if (pNode.has("kills")) {
                        participant.setKills(pNode.get("kills").asInt());
                    }
                    if (pNode.has("deaths")) {
                        participant.setDeaths(pNode.get("deaths").asInt());
                    }
                    if (pNode.has("assists")) {
                        participant.setAssists(pNode.get("assists").asInt());
                    }
                    if (pNode.has("win")) {
                        participant.setWin(pNode.get("win").asBoolean());
                    }

                    List<Integer> items = new ArrayList<>();
                    for (int i = 0; i < 7; i++) {
                        String itemKey = "item" + i;
                        if (pNode.has(itemKey)) {
                            int itemId = pNode.get(itemKey).asInt();
                            items.add(itemId);

                            switch (i) {
                                case 0:
                                    participant.setItem0(itemId);
                                    break;
                                case 1:
                                    participant.setItem1(itemId);
                                    break;
                                case 2:
                                    participant.setItem2(itemId);
                                    break;
                                case 3:
                                    participant.setItem3(itemId);
                                    break;
                                case 4:
                                    participant.setItem4(itemId);
                                    break;
                                case 5:
                                    participant.setItem5(itemId);
                                    break;
                                case 6:
                                    participant.setItem6(itemId);
                                    break;
                            }
                        }
                    }
                    participant.setItems(items);

                    if (pNode.has("summoner1Id")) {
                        participant.setSummoner1Id(pNode.get("summoner1Id").asInt());
                    }
                    if (pNode.has("summoner2Id")) {
                        participant.setSummoner2Id(pNode.get("summoner2Id").asInt());
                    }

                    if (pNode.has("totalDamageDealtToChampions")) {
                        participant.setTotalDamageDealtToChampions(pNode.get("totalDamageDealtToChampions").asInt());
                    }
                    if (pNode.has("goldEarned")) {
                        participant.setGoldEarned(pNode.get("goldEarned").asInt());
                    }
                    if (pNode.has("totalMinionsKilled")) {
                        participant.setTotalMinionsKilled(pNode.get("totalMinionsKilled").asInt());
                    }
                    if (pNode.has("neutralMinionsKilled")) {
                        participant.setNeutralMinionsKilled(pNode.get("neutralMinionsKilled").asInt());
                    }

                    participants.add(participant);
                }

                match.setParticipants(participants);
                System.out.println("✓ Parsed " + participants.size() + " participants successfully");
            } else {
                System.err.println("ERROR: No 'participants' field in match info");
            }

            Match saved = matchRepository.save(match);
            System.out.println("✓✓✓ Successfully saved match: " + matchId + " with "
                    + (saved.getParticipants() != null ? saved.getParticipants().size() : 0) + " participants");

            return saved;

        } catch (Exception e) {
            System.err.println("Error fetching match " + matchId + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
