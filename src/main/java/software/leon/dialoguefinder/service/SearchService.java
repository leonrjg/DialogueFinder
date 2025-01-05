package software.leon.dialoguefinder.service;

import org.springframework.stereotype.Service;
import software.leon.dialoguefinder.dto.SearchResult;
import software.leon.dialoguefinder.model.Dialogue;
import software.leon.dialoguefinder.repository.DialogueRepository;

import java.util.List;

@Service
public class SearchService {
    private final DialogueRepository dialogueRepository;

    public SearchService(DialogueRepository dialogueRepository) {
        this.dialogueRepository = dialogueRepository;
    }

    public List<SearchResult> searchDialogues(String query, int limit) {
        return dialogueRepository.findDialogues(query, limit);
    }

    public List<Dialogue> getDialogueContext(String episode, Integer index) {
        return dialogueRepository.getDialogueContext(episode, index);
    }

}
