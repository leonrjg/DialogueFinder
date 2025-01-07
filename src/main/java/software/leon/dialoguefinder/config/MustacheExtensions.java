package software.leon.dialoguefinder.config;

import com.samskivert.mustache.Mustache;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
class MustacheExtensions {

    @ModelAttribute("urlencode")
    public Mustache.Lambda urlencode() {
        return (frag, out) -> {
            try {
                String value = frag.execute();
                out.write(UriUtils.encodeQueryParam(value, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
