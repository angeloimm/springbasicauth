package it.olegna.test.basic.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.olegna.test.basic.dto.SimpleDto;

@RestController
@RequestMapping(value= {"/rest"})
public class TestBasicController {
	@RequestMapping(value= {"/simple"}, method= {RequestMethod.GET}, produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<SimpleDto>> getSimpleAnswer()
	{
		List<SimpleDto> payload = new ArrayList<>();
		for(int i= 0; i < 5; i++)
		{
			payload.add(new SimpleDto(UUID.randomUUID().toString()));
		}
		return ResponseEntity.ok().body(payload);
	}
}