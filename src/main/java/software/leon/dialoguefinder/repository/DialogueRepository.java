package software.leon.dialoguefinder.repository;

import software.leon.dialoguefinder.dto.SearchResult;
import software.leon.dialoguefinder.model.Dialogue;

import java.util.List;

public interface DialogueRepository {
    List<SearchResult> findDialogues(String keyword, int limit);

    List<Dialogue> getDialogueContext(String episode, Integer index);
}
