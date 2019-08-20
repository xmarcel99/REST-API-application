package com.crud.tasks.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    TrelloClient trelloClient;

    @Mock
    RestTemplate restTemplate;

    @Mock
    TrelloConfig trelloConfig;

    @Before
    public void init() {
        ReflectionTestUtils.setField(trelloConfig, "trelloApiEndpoint", "http://test.com");
        ReflectionTestUtils.setField(trelloConfig, "trelloAppKey", "test");
        ReflectionTestUtils.setField(trelloConfig, "trelloToken", "test");
        ReflectionTestUtils.setField(trelloConfig, "username", "marcelsztur");
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn((String) ReflectionTestUtils.getField(trelloConfig, "trelloApiEndpoint"));
        when(trelloConfig.getTrelloAppKey()).thenReturn((String) ReflectionTestUtils.getField(trelloConfig, "trelloAppKey"));
        when(trelloConfig.getTrelloToken()).thenReturn((String) ReflectionTestUtils.getField(trelloConfig, "trelloToken"));
        when(trelloConfig.getUsername()).thenReturn((String) ReflectionTestUtils.getField(trelloConfig, "username"));
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_board", "test_id", new ArrayList<>());

        URI url = new URI("http://test.com/members/marcelsztur/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com"
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        //Then
        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException{
        //Given
        URI url = new URI("http://test.com/members/marcelsztur/boards?key=test&token=test&fields=name,id&lists=all");
        TrelloBoardDto[] expectedTab = new TrelloBoardDto[0];
        when(restTemplate.getForObject(url,TrelloBoardDto[].class)).thenReturn(null);
        //When
        List<TrelloBoardDto> resultList = trelloClient.getTrelloBoards();
        //Then
        assertEquals(0,resultList.size());
    }
}