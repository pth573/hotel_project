//package com.project.hotel.controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.Mapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import commercial.course.entity.User;
//
//import java.security.Principal;
//import commercial.course.service.UserService;
//
//@Controller
//public class ChatController {
//
//    @Autowired
//    private UserService userService;
//    @GetMapping("/chat-list")
//    public String showChatBoxList(Model model, Principal principal) {
//        User currentUser = getCurrentUser(principal);
//        model.addAttribute("receivers", userService.findAllExceptCurrentUser(currentUser.getId()));
//        return "chat/chat-list";
//    }
//
//    @GetMapping("/chat/{receiverId}")
//    public String chatPageUser(@PathVariable("receiverId") Long id, Model model, Principal principal) {
//        User user = userService.findById(id);
//        User currentUser = getCurrentUser(principal);
//        model.addAttribute("receivers", userService.findAllExceptCurrentUser(currentUser.getId()));
//        model.addAttribute("receiver", user);
//        return "chat/chat-box";
//    }
//
////    @GetMapping("/chat")
////    public String chatPagePublic() {
////        return "chat/chat-box-public";
////    }
//
//    private User getCurrentUser(Principal principal) {
//        String username = principal.getName();
//        return userService.findByUserName(username);
//    }
//}
