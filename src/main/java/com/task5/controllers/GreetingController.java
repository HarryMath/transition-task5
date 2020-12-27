package com.task5.controllers;

import com.task5.repository.RoomsRepository;
import com.task5.repository.TagsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GreetingController {

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("rooms", RoomsRepository.getAll());
        model.addAttribute("tags", TagsRepository.getAll());
        return "home";
    }

    @GetMapping("/{id}")
    public String game(@PathVariable("id") String roomId, Model model) {
        if(RoomsRepository.isAvailable(roomId)) {
            model.addAttribute("room", RoomsRepository.getRoom(roomId));
            model.addAttribute("role", RoomsRepository.getRoom(roomId).getCurrentRole());
            return "game";
        }
        return main(model);
    }
}
