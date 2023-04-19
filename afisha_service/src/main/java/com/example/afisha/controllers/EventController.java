package com.example.afisha.controllers;

import com.example.afisha.controllers.pagination.MyPage;
import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.service.api.IDecoratorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("${app.events.url}")
@RequiredArgsConstructor
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final IDecoratorService service;
    private final Predicate<String> urlTypeValidator;
    private final ModelMapper mapper;

    @GetMapping("/{type}/{uuid}")
    public ResponseEntity<BaseEvent> getEvent(@PathVariable String type, @PathVariable UUID uuid){
        String urlType = type.toUpperCase();//todo type

        urlTypeValidator.test(urlType);//check isValid url

        BaseEvent event = service.get(uuid, urlType);
        log.info("GET EVENT");

        if (!EventType.valueOf(urlType).equals(EventType.valueOf(event.getType()))){//сравнение типов в боди на отдачу и урле
            throw new IllegalArgumentException("INCORRECT EVENT TYPE PROVIDED IN URL");
        }

        return new ResponseEntity<>(event, OK);
    }

    @GetMapping("/{type}")
    public ResponseEntity<MyPage<? extends BaseEvent>> getAll(@PathVariable String type,
                                                              @PageableDefault Pageable pageable){
        String urlType = type.toUpperCase();
        urlTypeValidator.test(urlType);

        log.info("GET PAGE");
        Page<? extends BaseEvent> eventsPage = service.getAll(urlType, pageable);

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

