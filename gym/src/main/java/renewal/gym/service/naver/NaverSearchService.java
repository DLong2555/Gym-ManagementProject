package renewal.gym.service.naver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import renewal.gym.dto.NaverSearchResultForm;
import renewal.gym.service.GymService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NaverSearchService {

    @Value("${naver.url.search.local}")
    private String baseUrl;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientPwd;

    private final GymService gymService;
    static private final ObjectMapper objectMapper = new ObjectMapper();

    public List<NaverSearchResultForm> getSearchResult(String query) {

        log.debug("query parameter: {}", query);

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        JsonNode json = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParam("query", query)
                                .queryParam("display", 5)
                                .build())

                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientPwd)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        List<NaverSearchResultForm> result = new ArrayList<>();
        if(json != null) {
            for (JsonNode jsonNode : json.findValue("items")) {
                log.debug("json node: {}", jsonNode);
                String title = jsonNode.get("title").asText().replaceAll("<b>", " ").replaceAll("</b>", "").trim();
                String address = jsonNode.get("address").asText();
                double mapx = jsonNode.get("mapx").asDouble() / 10000000;
                double mapy = jsonNode.get("mapy").asDouble() / 10000000;

                Long findGymId = gymService.findSelectedGym(title, address);
                if(findGymId != null) {
                    result.add(new NaverSearchResultForm(findGymId, title, address, mapx, mapy));
                }
            }

            for (NaverSearchResultForm item : result) {
                log.debug("item: {}", item);
            }
        }
        
        return result;
    }


}
