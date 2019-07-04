package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() throws NotFoundTrelloBoardException {

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();

        for (int i = 0; i < trelloClient.getBoardsResponse().length; i++) {
            TrelloBoardDto trelloBoardDto = trelloClient.getTrelloBoards(i).orElseThrow(NotFoundTrelloBoardException::new);
            trelloBoards.add(trelloBoardDto);
        }
        trelloBoards.stream()
                .filter(trelloBoardDto -> trelloBoardDto.getId() != null && trelloBoardDto.getName() != null && trelloBoardDto.getName().contains("Kodilla"))
                .forEach(trelloBoardDto -> System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName()));

    }
}