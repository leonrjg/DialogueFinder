package software.leon.dialoguefinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.leon.dialoguefinder.model.Dialogue;

@Data
@AllArgsConstructor
public class DialogueResult {
    private Dialogue dialogue;
    private String highlight;
}