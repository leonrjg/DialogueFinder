package software.leon.dialoguefinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResult {
    private String episode;
    private List<DialogueResult> dialogues;
}
