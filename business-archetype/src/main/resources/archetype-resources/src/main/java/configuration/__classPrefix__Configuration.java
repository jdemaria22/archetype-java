package ${groupId}.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ${classPrefix}Configuration {

    @Value("${app.user}")
    private String appUser;
    @Value("${app.url.integration}")
    private String urlIntegration;
}