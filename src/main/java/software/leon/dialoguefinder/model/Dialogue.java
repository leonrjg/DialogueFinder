package software.leon.dialoguefinder.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "media")
@Setting(settingPath = "/analyzer.json")
@Data
public class Dialogue {
    private String id;
    private Integer index;
    private String episode;
    private String startTime;
    private String endTime;
    @Field(type = FieldType.Text, analyzer = "subtitle_analyzer")
    private String text;
}