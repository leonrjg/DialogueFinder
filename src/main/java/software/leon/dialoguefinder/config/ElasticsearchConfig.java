package software.leon.dialoguefinder.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Configuration
public class ElasticsearchConfig {
    public static final String HOSTNAME = System.getenv("ELASTIC_HOSTNAME") == null ? "localhost" : System.getenv("ELASTIC_HOSTNAME");
    public static final int PORT = System.getenv("ELASTIC_PORT") == null ? 9200 : Integer.parseInt(System.getenv("ELASTIC_PORT"));
    public static final String ENV_PASSWORD = "ELASTIC_PASSWORD";
    public static final String USER_NAME = "elastic";
    public static final String SCHEME = System.getenv("ELASTIC_SCHEME") == null ? "http" : System.getenv("ELASTIC_SCHEME");

    @Bean
    public ElasticsearchClient elasticsearchClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USER_NAME, System.getenv(ENV_PASSWORD)));

        SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(null, (x509Certificates, s) -> true);
        final SSLContext sslContext = sslBuilder.build();

        HttpHost host = new HttpHost(HOSTNAME, PORT, SCHEME);

        Logger.getLogger(ElasticsearchConfig.class.getName()).info(String.format("\n ★★★ \n Connecting to: %s \n ★★★", host));

        RestClient restClient = RestClient
                .builder(host)
                .setHttpClientConfigCallback(
                        httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                        .setSSLContext(sslContext)
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                ).build();

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}