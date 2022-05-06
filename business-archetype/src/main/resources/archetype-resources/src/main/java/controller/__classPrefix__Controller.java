package ${groupId}.controller;

import ${groupId}.response.${classPrefix}Response;
import ${groupId}.exception.${classPrefix}Exception;
import ${groupId}.service.${classPrefix}Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ${classPrefix}Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(${classPrefix}Controller.class);

    @Autowired
    private ${classPrefix}Service service;

    @GetMapping(value = "/base")
    public ResponseEntity<${classPrefix}Response> validarLinea() {
        try {
            LOGGER.info(":::::: Check service::::::");
            service.getOperation();
            return ResponseEntity
                    .ok()
                    .body(${classPrefix}Response.builder().build());
            } catch (${classPrefix}Exception ex) {
                LOGGER.error(":::::: Error {} ::::::", ex.getMessage());
                    return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();
            }
        }

}