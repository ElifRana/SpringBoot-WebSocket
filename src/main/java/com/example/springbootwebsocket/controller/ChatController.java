package com.example.springbootwebsocket.controller;

import com.example.springbootwebsocket.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    //mesajı kendimiz özelleştirip gönderebiliyoruz.
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")   //herkes için
    // @SendToUser()         //tek bir kullanıcıya göndermek için
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, ChatMessage chatMessage) {

        System.out.println("handling send message: " + chatMessage + " to: " + to);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, chatMessage);

    }

}
