package com.crud.tasks.service;

import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrelloService {

    private static String SUBJECT = "Task: New Trello Card";

    @Autowired
    SimpleEmailService emailService;

    @Autowired
    TrelloClient trelloClient;

    @Autowired
    AdminConfig adminConfig;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return  trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCard createTrelloCard(TrelloCardDto trelloCardDto) {
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
        Optional.ofNullable(newCard).ifPresent(createdTrelloCard -> emailService.send(
                new Mail(adminConfig.getAdminMail(),
                        SUBJECT,
                        "Card Name"+ createdTrelloCard.getName() + "has been created")));
        return newCard;
    }
}
