package com.example.afisha.controllers.json;

import com.example.afisha.controllers.api.IEventController;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.SaveConcertDto;
import com.example.afisha.service.api.IEventService;
import com.example.afisha.service.api.IMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/v1/afisha/event/concert")
public class ConcertController implements IEventController<Concert, SaveConcertDto> {
    private final IEventService<Concert, SaveConcertDto> service;
    private final Predicate<SaveConcertDto> concertValidate;//
    private final IMapper<Concert, SaveConcertDto> mapper;

    public ConcertController(IEventService<Concert, SaveConcertDto> service,
                             Predicate<SaveConcertDto> concertValidate,
                             IMapper<Concert, SaveConcertDto> mapper) {
        this.service = service;
        this.concertValidate = concertValidate;
        this.mapper = mapper;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Concert> get(@PathVariable UUID uuid){
        return new ResponseEntity<>(service.get(uuid), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<Concert>> get(@RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(required = false, value = "size", defaultValue = "5") Integer size ){

        return new ResponseEntity<>(service.getAll(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody SaveConcertDto concertDto){
        if(!concertValidate.test(concertDto)) throw new IllegalArgumentException("FIELDS: TITLE, TYPE, STATUS, EVENT_DATE AND CATEGORY MUST BE FILLED");

        service.save(mapper.mapToEntity(concertDto));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody SaveConcertDto concertDto, @PathVariable UUID uuid, @PathVariable LocalDateTime dt_update ){
        if(!concertValidate.test(concertDto)) throw new IllegalArgumentException("FIELDS: TITLE, TYPE, STATUS, EVENT_DATE AND CATEGORY MUST BE FILLED");

        service.update(concertDto, uuid, dt_update );
    }
    /*
      PUT SAMPLE
      {"title":"ASAP ROCKY","description":null,"event_date":1657152000011,"date_end_of_sale":null,"type":"FILM","status":"DRAFT","category":"b992c037-a0f1-4d99-8e76-ed5c1a931d9a"}
     */


}
