package com.example.afisha.controllers.json;

import com.example.afisha.controllers.api.IEventController;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dto.SaveFilmDto;
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
@RequestMapping("/api/v1/afisha/event/film")
public class FilmController implements IEventController<Film, SaveFilmDto> {
    private final IEventService<Film, SaveFilmDto> service;
    private final Predicate<SaveFilmDto> filmValidate;
    private final IMapper<Film, SaveFilmDto> mapper;

    public FilmController(IEventService<Film, SaveFilmDto> service, Predicate<SaveFilmDto> filmValidate, IMapper<Film, SaveFilmDto> mapper) {
        this.service = service;
        this.filmValidate = filmValidate;
        this.mapper = mapper;
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<Film> get(@PathVariable UUID uuid){
        return new ResponseEntity<Film>(service.get(uuid), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<Film>> get(@RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
                                          @RequestParam(required = false, value = "size", defaultValue = "5") Integer size ){

        return new ResponseEntity<>(service.getAll(PageRequest.of(page, size)), HttpStatus.OK);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody SaveFilmDto filmDto){
        if(!filmValidate.test(filmDto)) throw new IllegalArgumentException("FIELDS: TITLE, TYPE, STATUS, EVENT_DATE AND COUNTRY MUST BE FILLED");

        service.save(mapper.mapToEntity(filmDto));
    }

    //POST SAMPLE
    // {"title":"ENOT","description":"NE TROGAI MOEGO ENOTA","event_date":1657152000011,"date_end_of_sale":1657212301,
    // "type":"FILM","status":"DRAFT","country":"b992c037-a0f1-4d99-8e76-ed5c1a931d9a","release_year":2021,
    // "release_date":1657212765478,"duration":21}

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody SaveFilmDto filmDto, @PathVariable UUID uuid, @PathVariable LocalDateTime dt_update ){
        if(!filmValidate.test(filmDto)) throw new IllegalArgumentException("FIELDS: TITLE, TYPE, STATUS, EVENT_DATE AND COUNTRY MUST BE FILLED");

        service.update(filmDto, uuid, dt_update );
    }
}
