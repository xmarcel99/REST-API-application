package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloMapperTestSuite {

    private List<TrelloBoardDto> createListOfTrelloBoardsDto() {
        return new ArrayList<>(Arrays.asList(
                new TrelloBoardDto("testName", "testId", createListOfTrelloListDto())
        ));
    }

    private List<TrelloBoard> createListOfTrelloBoards() {
        return new ArrayList<>(Arrays.asList(
                new TrelloBoard("testName", "testId", trelloMapper.mapToList(createListOfTrelloListDto()))
        ));
    }

    private List<TrelloListDto> createListOfTrelloListDto() {
        return new ArrayList<>(Arrays.asList(
                new TrelloListDto("testId", "testName", true)
        ));
    }

    private List<TrelloList> createCollectionOfTrelloList() {
        return new ArrayList<>(Arrays.asList(new TrelloList("testId", "testName", true)));
    }

    private TrelloCard createTrelloCard() {
        return new TrelloCard("testName", "testDescription", "testPos", "testListId");
    }

    private TrelloCardDto createTrelloCartDto() {
        return new TrelloCardDto("testName", "testDescription", "testPos", "testListId");
    }

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void shouldMapToList() {
        //Given
        List<TrelloList> result = trelloMapper.mapToList(createListOfTrelloListDto());
        //When
        int sizeOfResultList = result.size();
        String nameOfMappedObjectInList = result.get(0).getName();
        //Then
        assertEquals(createListOfTrelloListDto().size(), sizeOfResultList);
        assertEquals(createListOfTrelloListDto().get(0).getName(), nameOfMappedObjectInList);
    }

    @Test
    public void shouldMapToBoards() {
        //Given
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(createListOfTrelloBoardsDto());
        //When
        int sizeOfMappedList = trelloBoards.size();
        String nameOfMappedObjectInList = trelloBoards.get(0).getName();
        int sizeOfMappedListAttributeInObject = trelloBoards.get(0).getLists().size();
        //Then
        assertEquals(createListOfTrelloBoardsDto().size(), sizeOfMappedList);
        assertEquals(createListOfTrelloBoardsDto().get(0).getLists().size(), sizeOfMappedListAttributeInObject);
        assertEquals(createListOfTrelloBoardsDto().get(0).getName(), nameOfMappedObjectInList);
    }

    @Test
    public void shouldMapToListDto() {
        //Given
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(createCollectionOfTrelloList());
        //When
        int sizeOfMappedList = trelloListDtos.size();
        String nameOfObjectInMappedList = trelloListDtos.get(0).getName();
        boolean isClosedObjectInList = trelloListDtos.get(0).isClosed();
        //Then
        assertEquals(createCollectionOfTrelloList().size(), sizeOfMappedList);
        assertEquals(createCollectionOfTrelloList().get(0).getName(), nameOfObjectInMappedList);
        assertEquals(createCollectionOfTrelloList().get(0).isClosed(), isClosedObjectInList);
    }

    @Test
    public void shouldMapToBoardsDto() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(createListOfTrelloBoards());
        //When
        int sizeOfMappedList = trelloBoardDtos.size();
        int sizeOfMappedListAttributeInObjectFromList = trelloBoardDtos.get(0).getLists().size();
        //Then
        assertEquals(createListOfTrelloBoards().size(), sizeOfMappedList);
        assertEquals(createListOfTrelloBoards().get(0).getLists().size(), sizeOfMappedListAttributeInObjectFromList);
    }

    @Test
    public void shouldMapToCard() {
        //Given
        TrelloCard trelloCard = trelloMapper.mapToCard(createTrelloCartDto());
        //When
        String trelloCartName = trelloCard.getName();
        String trelloCartDescription = trelloCard.getDescription();
        String trelloCardPos = trelloCard.getPos();
        String trelloCardListId = trelloCard.getListId();
        //Then
        assertEquals(createTrelloCartDto().getName(),trelloCartName);
        assertEquals(createTrelloCartDto().getDescription(),trelloCartDescription);
        assertEquals(createTrelloCartDto().getPos(),trelloCardPos);
        assertEquals(createTrelloCartDto().getListId(),trelloCardListId);
    }

    @Test
    public void shouldMapToCardDto() {
        //Given
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(createTrelloCard());
        //When
        String trelloCartDtoName = trelloCardDto .getName();
        String trelloCartDtoDescription = trelloCardDto .getDescription();
        String trelloCardDtoPos = trelloCardDto .getPos();
        String trelloCardDtoListId = trelloCardDto .getListId();
        //Then
        assertEquals(createTrelloCartDto().getName(),trelloCartDtoName);
        assertEquals(createTrelloCartDto().getDescription(),trelloCartDtoDescription);
        assertEquals(createTrelloCartDto().getPos(),trelloCardDtoPos);
        assertEquals(createTrelloCartDto().getListId(),trelloCardDtoListId);
    }
}