package ${groupId}.reactive.transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Component
public class ObjectMapperConverter {

    private ObjectMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
    }

    public String toJson(ProcessorEvent processorEvent) {
        try {
            return mapper.writeValueAsString(processorEvent);
        } catch (IOException ex) {
            //TODO: Handle exception
            return "";
        }
    }

    public String getEventType(String message) {
        try {
            Map<String, String> map = mapper.readValue(message, Map.class);
            return map.get("eventType");
        } catch (IOException ex) {
            //TODO: Handle exception
            return "";
        }
    }

    public ObjectMapper getMapper(){
        return mapper;
    }
}
