package software.leon.dialoguefinder.repository.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import software.leon.dialoguefinder.dto.DialogueResult;
import software.leon.dialoguefinder.dto.SearchResult;
import software.leon.dialoguefinder.model.Dialogue;
import software.leon.dialoguefinder.repository.DialogueRepository;
import software.leon.dialoguefinder.repository.impl.jpa.DialogueElasticsearchRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DefaultDialogueRepository implements DialogueRepository {
    private final DialogueElasticsearchRepository dialogueElasticsearchRepository;

    DefaultDialogueRepository(DialogueElasticsearchRepository dialogueElasticsearchRepository) {
        this.dialogueElasticsearchRepository = dialogueElasticsearchRepository;
    }

    public List<SearchResult> findDialogues(String query, int limit) {
        Map<String, List<DialogueResult>> results = dialogueElasticsearchRepository.findDialogues(query, Pageable.ofSize(limit))
                .stream()
                .map(hit -> new DialogueResult(hit.getContent(), hit.getHighlightFields().get("text").get(0)))
                .collect(Collectors.groupingBy(s -> s.getDialogue().getEpisode()));
        return results.keySet().stream().map(episode -> new SearchResult(episode, results.get(episode))).toList();
    }

    public List<Dialogue> getDialogueContext(String episode, Integer index) {
        return dialogueElasticsearchRepository.findByEpisodeAndIndexBetweenOrderByIndexAsc(episode, index - 1, index + 1);
    }
}
