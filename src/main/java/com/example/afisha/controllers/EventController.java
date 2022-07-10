package com.example.afisha.controllers;

import com.example.afisha.dao.entity.Event;
import com.example.afisha.dao.entity.api.IEvent;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.service.api.IEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/v1/afisha/event")
public class EventController {
    private IEventService eventService;
    private final Predicate<SaveEventDtoFactory> eventValidator;
    private final Predicate<String> urlTypeValidator;

    public EventController(IEventService eventService, Predicate<SaveEventDtoFactory> eventValidator,
                           Predicate<String> urlTypeValidator) {
        this.eventService = eventService;
        this.eventValidator = eventValidator;
        this.urlTypeValidator = urlTypeValidator;
    }

    @GetMapping("/{type}/{uuid}")
    public ResponseEntity<IEvent> getEvent(@PathVariable String type, @PathVariable UUID uuid){
        String urlType = type.toUpperCase();
        urlTypeValidator.test(urlType);//чек правильного урла

        IEvent event = eventService.get(uuid, EventType.valueOf(urlType));

        if (!EventType.valueOf(urlType).equals(event.getType())){//сравнение типов в боди и урле
            throw new IllegalArgumentException("TYPES DOES NOT MATCH");
        }

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/{type}")
    public ResponseEntity<Page<? extends Event>> getAll(@PathVariable String type,
                                               @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                               @RequestParam(required = false, defaultValue = "5", name = "size") Integer size){
        String urlType = type.toUpperCase();
        urlTypeValidator.test(urlType);//чек правильного урла

        Page<? extends Event> eventsPage = eventService.getAll(EventType.valueOf(urlType), PageRequest.of(page, size, Sort.by("title")));

        return new ResponseEntity<>(eventsPage, HttpStatus.OK);
    }

    @PostMapping("/{type}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@PathVariable String type, @RequestBody SaveEventDtoFactory dtoFactory){
        String urlType = type.toUpperCase();
        urlTypeValidator.test(urlType);//чек правильного урла
        if (!eventValidator.test(dtoFactory)){//чек обязательных полей
            throw new IllegalArgumentException("FIELDS: TITLE, TYPE, STATUS, EVENT_DATE AND CATEGORY OR COUNTRY MUST BE FILLED");
        }

        if (!EventType.valueOf(urlType).equals(dtoFactory.getType())){//сравнение типов в боди и урле
            throw new IllegalArgumentException("TYPES DOES NOT MATCH");
        }

        eventService.save(dtoFactory.getDto());
    }

    @PutMapping("/{type}/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable String type, @PathVariable LocalDateTime dt_update,
                       @PathVariable UUID uuid, @RequestBody SaveEventDtoFactory dtoFactory){

        String urlType = type.toUpperCase();
        urlTypeValidator.test(urlType);//чек правильного урла
        if (!eventValidator.test(dtoFactory)){//чек обязательных полей
            throw new IllegalArgumentException("FIELDS: TITLE, TYPE, STATUS, EVENT_DATE AND CATEGORY OR COUNTRY MUST BE FILLED");
        }

        if (!EventType.valueOf(urlType).equals(dtoFactory.getType())){//сравнение типов в боди и урле
            throw new IllegalArgumentException("TYPES DOES NOT MATCH");
        }

        eventService.update(dtoFactory, uuid, dt_update);
    }
}
/*
   CONCERT TEST
  {"title":"ASAP ROCKY","description":"raper","event_date":1657152000011,"date_end_of_sale":null,"type":"CONCERT","status":"DRAFT","category":"b992c037-a0f1-4d99-8e76-ed5c1a931d9a"}
   FILM TEST
  {"title":"ENOT","description":"NE TROGAI MOEGO ENOTA","event_date":1657152000011,"date_end_of_sale":1657212301,"type":"FILM","status":"DRAFT","country":"b992c037-a0f1-4d99-8e76-ed5c1a931d9a","release_year":2021,"release_date":1657212765478,"duration":21}
 */
