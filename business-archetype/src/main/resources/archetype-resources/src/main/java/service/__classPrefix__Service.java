package ${groupId}.service;

import ${groupId}.exception.${classPrefix}Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ${classPrefix}Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(${classPrefix}Service.class);

    public Optional getOperation() throws ${classPrefix}Exception{
            return Optional.empty();
        }
}