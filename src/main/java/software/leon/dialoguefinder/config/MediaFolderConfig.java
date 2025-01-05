package software.leon.dialoguefinder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.nio.file.Path;

@Configuration
public class MediaFolderConfig implements WebMvcConfigurer {

    public static final String DEFAULT_PATH = "/mnt/media";
    private final Path mediaPath;

        MediaFolderConfig() {
            String envMediaPath = System.getenv("MEDIA_PATH");
            mediaPath = Path.of(envMediaPath == null ? DEFAULT_PATH : envMediaPath);
        }

        @Override
        public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/files/**")
                    .addResourceLocations(String.format("file:%s", mediaPath))
                    .setCachePeriod(3600)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver());
        }
}
