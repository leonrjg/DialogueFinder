package software.leon.dialoguefinder.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "media")
@Data
public class Dialogue {
    private String id;
    private Integer index;
    private String episode;
    private String startTime;
    private String endTime;
    private String text;
}