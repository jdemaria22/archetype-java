package ${groupId}.integration;

import ${groupId}.response.${classPrefix}Response;
import ${groupId}.configuration.${classPrefix}Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ${classPrefix}Integration{

    private static final Logger LOGGER = LoggerFactory.getLogger("logToElkAppender");

    @Autowired
    private ${classPrefix}Configuration configurationApp;

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "integrationFallbackMethod", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000"),
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10")})
    public Optional<${classPrefix}Response> getResponseFromIntegration() throws HttpClientErrorException{
        try{
            HttpHeaders headers = new HttpHeaders();
            HttpEntity entity = new HttpEntity(headers);
            String integrationUrl = configurationApp.getUrlIntegration();
            LOGGER.debug(":::::: Integration URL  {} ::::::", integrationUrl);
            ${classPrefix}Response response = restTemplate.exchange(
                    integrationUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<${classPrefix}Response>() {
                    })
                    .getBody();
            Optional<${classPrefix}Response> optionalResponse = Optional.ofNullable(response);
            if(optionalResponse.isEmpty()){
                LOGGER.warn(":::::: The response is empty for request ::::::");
                return Optional.empty();
            }
            LOGGER.debug(":::::: Integration URL  {} ::::::", integrationUrl);
            return optionalResponse;
        } catch (HttpClientErrorException ex) {
            LOGGER.error(":::::: Error getting Response from integration ::::::",
            ex.getMessage());
            throw new HttpClientErrorException(ex.getStatusCode());
            }
        }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }

    public void integrationFallbackMethod() throws HttpStatusCodeException {
        LOGGER.error(":::::: Error service integration ::::::");
        throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}