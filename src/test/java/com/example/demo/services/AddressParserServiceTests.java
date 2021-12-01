package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressParserServiceTests {

    @InjectMocks
	AddressParserService addressParserService;

	@Test
	public void addressContainsComma(){

		Map<String, String> result = addressParserService.parse("Calle Aduana, 29");

		assertEquals(result.get("street"), "Calle Aduana");
		assertEquals(result.get("housenumber"), "29");

	}

	@Test
	public void addressContainsNo(){

		Map<String, String> result = addressParserService.parse("Calle 39 No 1540");

		assertEquals(result.get("street"), "Calle 39");
		assertEquals(result.get("housenumber"), "No 1540");

		result = addressParserService.parse("Calle 39 Nb 1540");

		assertEquals(result.get("street"), "Calle 39");
		assertEquals(result.get("housenumber"), "Nb 1540");

	}

	@Test
	public void addressContainsLoneNumber(){

		Map<String, String> result = addressParserService.parse("Am Bächle 23");

		assertEquals(result.get("street"), "Am Bächle");
		assertEquals(result.get("housenumber"), "23");

		result = addressParserService.parse("Auf der Vogelwiese 23 b");

		assertEquals(result.get("street"), "Auf der Vogelwiese");
		assertEquals(result.get("housenumber"), "23 b");

		result = addressParserService.parse("Blaufeldweg 123B");

		assertEquals(result.get("street"), "Blaufeldweg");
		assertEquals(result.get("housenumber"), "123B");

		result = addressParserService.parse("200 Broadway Av");

		assertEquals(result.get("street"), "Broadway Av");
		assertEquals(result.get("housenumber"), "200");

		

	}
    
}
