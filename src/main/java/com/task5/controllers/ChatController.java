package com.task5.controllers;

import com.task5.models.Move;
import com.task5.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void putMove(Move move) throws Exception {
        boolean validMove = RoomsRepository.saveMove(move);
        if(validMove) {
            messagingTemplate.convertAndSend("/room/" + move.roomId + "/queue/messages", move);
        } if (RoomsRepository.gameEnded(move.roomId)) {
            messagingTemplate.convertAndSend("/room/" + move.roomId + "/queue/messages", RoomsRepository.getWinner(move.roomId));
            RoomsRepository.disposeRoom(move.roomId);
        }
    }
}
