package com.example.afisha.controllers;

import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.controllers.pagination.MyPage;
import com.example.afisha.service.api.IDecoratorService;
import com.example.afisha.validation.UrlTypeValidationPredicate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

import static org.springframework.http.HttpStatus.*;

//TODO MULTIPLE ERROR RESPONSE
//VALIDATOR AS EXTERNAL CLASS
//CONVERTER SERVICE
@RestController
@RequestMapping("${app.events.url}")
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final IDecoratorService service;
    private final Predicate<String> urlTypeValidator;
    private final ModelMapper mapper;

    public  EventController(IDecoratorService service, UrlTypeValidationPredicate urlTypeValidator, ModelMapper mapper) {
        this.service = service;
        this.urlTypeValidator = urlTypeValidator;
        this.mapper = mapper;
    }

    @GetMapping("/{type}/{uuid}")
    public ResponseEntity<BaseEvent> getEvent(@PathVariable String type, @PathVariable UUID uuid){
        String urlType = type.toUpperCase();//todo type

        urlTypeValidator.test(urlType);//check isValid url

        BaseEvent event = service.get(uuid, urlType);
        log.info("GET EVENT");

        if (!EventType.valueOf(urlType).equals(event.getType())){//сравнение типов в боди на отдачу и урле
            throw new IllegalArgumentException("INCORRECT EVENT TYPE PROVIDED IN URL");
        }

        return new ResponseEntity<>(event, OK);
    }

    @GetMapping("/{type}")
    public ResponseEntity<MyPage<? extends BaseEvent>> getAll(@PathVariable String type,
                                                              @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                                              @RequestParam(required = false, defaultValue = "5", name = "size") Integer size){
        String urlType = type.toUpperCase();
        urlTypeValidator.test(urlType);

        log.info("GET PAGE");
        Page<? extends BaseEvent> eventsPage = service.getAll(urlType, PageRequest.of(page, size, Sort.by("createDate").descending()));

        return new ResponseEntity<MyPage<? extends BaseEvent>>(mapper.map(eventsPage, MyPage.class), OK);
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    public void createEvent(@RequestBody SaveEventDtoFactory dtoFactory){
        log.info( "SAVE DTO " + dtoFactory );
        service.save(dtoFactory.getEntity());
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(ACCEPTED)
    public void update(@PathVariable LocalDateTime dt_update,
                       @PathVariable UUID uuid, @RequestBody SaveEventDtoFactory dtoFactory){

        log.info("CHECK TYPE IN DTO FOR PUT @NOT NULL");
        if (dtoFactory.getType() == null){
            throw new IllegalArgumentException("PLEASE, PROVIDE SUPPORTED EVENT TYPE: " + Arrays.toString(EventType.values()));
        }

        log.info("UPDATE DTO " + dtoFactory );
        service.update(dtoFactory, uuid, dt_update);
    }
}
/*
   CONCERT TEST
  {"title":"ASAP ROCKY","description":"raper","event_date":1657152000011,"date_end_of_sale":null,"type":"CONCERT","status":"DRAFT","category":"b992c037-a0f1-4d99-8e76-ed5c1a931d9a"}
   FILM TEST
  {"title":"ENOT","description":"NE TROGAI MOEGO ENOTA","event_date":1657152000011,"date_end_of_sale":1657212301,"type":"FILM","status":"DRAFT","country":"b992c037-a0f1-4d99-8e76-ed5c1a931d9a","release_year":2021,"release_date":1657212765478,"duration":21}
 */
