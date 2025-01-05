package software.leon.dialoguefinder.repository.impl.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import software.leon.dialoguefinder.model.Dialogue;

import java.util.List;

public interface DialogueElasticsearchRepository extends ElasticsearchRepository<Dialogue, String> {
    @Query("""
            {
                "bool": {
                    "should": [
                        {
                            "match": {
                                "text": {
                                    "query": "?0",
                                    "fuzziness": 1,
                                    "operator": "and",
                                    "prefix_length": 1
                                }
                            }
                        },
                        {
                            "match_phrase": {
                                "text": {
                                    "query": "?0",
                                    "slop": 1
                                }
                            }
                        },
                        {
                            "wildcard": {
                                "text": "*?0*"
                            }
                        }
                    ],
                    "minimum_should_match": 1
                }
            }
            """)
    @Highlight(fields = {@HighlightField(name = "text")},
            parameters = @HighlightParameters(preTags = "<mark>", postTags = "</mark>", numberOfFragments = 3)
    )
    List<SearchHit<Dialogue>> findDialogues(String keyword, Pageable pageable);

    List<Dialogue> findByEpisodeAndIndexBetweenOrderByIndexAsc(String episode, Integer index, Integer end);
}
