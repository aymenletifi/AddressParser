package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.services.AddressParserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressParserController {
    
    @Autowired
    AddressParserService addressParserService;

    Logger logger = LoggerFactory.getLogger(AddressParserController.class);

    // This route is for the developed from scratch Parser.
    @PostMapping("/parse")
    public Map<String, String> parse(@RequestBody String address){
        return addressParserService.parse(address);
    }
}
