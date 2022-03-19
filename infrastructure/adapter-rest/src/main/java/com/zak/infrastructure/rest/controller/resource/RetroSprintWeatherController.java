package com.zak.infrastructure.rest.controller.resource;

import com.zak.application.service.api.RetroSprintWeatherService;
import com.zak.domain.model.EventWeather;
import com.zak.domain.model.RetroSprintWeather;
import com.zak.infrastructure.rest.controller.resource.dto.RetroSprintWeatherDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/retro-sprint-weather")
public class RetroSprintWeatherController {

    private final RetroSprintWeatherService retroSprintWeatherService;
    private final MapperFacade orikaMapperFacade;

    @Autowired
    public RetroSprintWeatherController(RetroSprintWeatherService retroSprintWeatherService,
                                        MapperFacade orikaMapperFacade) {
        this.retroSprintWeatherService = retroSprintWeatherService;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    @PostMapping("/retro-sprint/{retroSprintId}/user/{userId}")
    public RetroSprintWeatherDto createRetroSprintWeatherService(
            @PathVariable String retroSprintId,
            @PathVariable String userId,
            @RequestBody RetroSprintWeatherDto retroSprintWeatherDto) {
        Optional<RetroSprintWeather> savedRetroSprintWeather =
                retroSprintWeatherService.createRetroSprintWeather(
                        userId,
                        retroSprintId,
                        orikaMapperFacade.map(retroSprintWeatherDto, RetroSprintWeather.class));
        return orikaMapperFacade.map(savedRetroSprintWeather.get(), RetroSprintWeatherDto.class);
    }

    @PostMapping("/update")
    public RetroSprintWeatherDto updateRetroSprintWeather(@RequestBody RetroSprintWeatherDto retroSprintWeatherDto) {
        Optional<RetroSprintWeather> savedRetroSprintWeather =
                retroSprintWeatherService.updateRetroSprintWeather(
                        orikaMapperFacade.map(retroSprintWeatherDto, RetroSprintWeather.class));
        return orikaMapperFacade.map(savedRetroSprintWeather.get(), RetroSprintWeatherDto.class);
    }

    @GetMapping("/retro-sprint/{retroSprintId}")
    public Set<RetroSprintWeatherDto> getRetroSprintsWeatherByRetroSprintId( @PathVariable String retroSprintId) {
        Set<RetroSprintWeather> retroSprintsWeatherSet =
                retroSprintWeatherService.getRetroSprintsWeatherByRetroSprintId(retroSprintId);
        return orikaMapperFacade.mapAsSet(retroSprintsWeatherSet, RetroSprintWeatherDto.class);
    }
}
