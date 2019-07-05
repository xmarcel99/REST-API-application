package com.crud.tasks.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TrelloClient {
    @Autowired
    RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.id}")
    private String username;


    public URI getTrelloUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists","all").build().encode().toUri();
        return url;
    }

    public List<TrelloBoardDto> getTrelloBoards(){
          TrelloBoardDto[] boardsResponse = restTemplate.getForObject(getTrelloUrl(), TrelloBoardDto[].class);
          if(boardsResponse != null) {
              return Arrays.asList(boardsResponse);
          }
          return new ArrayList<>();
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint+"/cards")
                .queryParam("key",trelloAppKey)
                .queryParam("token",trelloToken)
                .queryParam("name",trelloCardDto.getName())
                .queryParam("desc",trelloCardDto.getDescription())
                .queryParam("pos",trelloCardDto.getPos())
                .queryParam("idList",trelloCardDto.getListId()).build().encode().toUri();

        return restTemplate.postForObject(url,null,CreatedTrelloCard.class);
    }

    public CreatedTrelloCard getCard(String cardId) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint+"/cards/"+cardId)
                .queryParam("key",trelloAppKey)
                .queryParam("token",trelloToken)
                .queryParam("fields","id,name,shortUrl,badges").build().encode().toUri();

        return restTemplate.getForObject(url,CreatedTrelloCard.class);
    }
}
