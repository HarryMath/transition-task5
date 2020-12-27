package com.task5.controllers;

import com.task5.models.GameRoom;
import com.task5.repository.RoomsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestsController {

    @RequestMapping("/newGame")
    public int newGame(@RequestParam(value="title", required=false, defaultValue="") String title,
                            @RequestParam(value="tags", required=false, defaultValue="") String tags,
                            @RequestParam(value="color", required=false, defaultValue="") String color,
                            @RequestParam(value="size", required=false, defaultValue="3") short field_size
    ) {
        return RoomsRepository.addRoom(title, tags, color, field_size);
    }
}
