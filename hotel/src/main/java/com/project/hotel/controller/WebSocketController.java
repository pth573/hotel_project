package com.project.hotel.controller;
import com.project.hotel.model.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public Message sendMessage(Message message) {
        return message;
    }
}