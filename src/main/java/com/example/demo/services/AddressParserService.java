package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.mapzen.jpostal.AddressExpander;


@Service
public class AddressParserService {

    Logger logger = LoggerFactory.getLogger(AddressParserService.class);

    public Map<String, String> parse(String address) {



        // Singleton, libpostal setup is done in the constructor
        AddressExpander e = AddressExpander.getInstance();
        String[] expansions = e.expandAddress("Quatre vingt douze Ave des Champs-Élysées");

        logger.info(expansions[0]);
        

        String lowerCaseAddress = address.toLowerCase();

        // Check if the address contains No or Nb and use that as an indication for the
        // house number.
        if (lowerCaseAddress.contains("no") || lowerCaseAddress.contains("nb")) {

            return parseContainsNo(address);

        }

        // Check if the address contains a comma.
        if (address.contains(",")) {

            return parseComma(address);

        }

        // If the address contains a single number then base the separation on that
        // number.
        if (numberCount(address) == 1) {

            return parseLoneNumber(address);
            
        }

        // if the address contains 0 or more than 1 number then we use it all as the
        // street name.
        HashMap<String, String> result = new HashMap<>();

        result.put("street", address);
        result.put("housenumber", "");

        return result;

    }

    // If the address contains a comma, then the part that contains a number is the
    // house number.
    // If the 2 parts contain numbers, then we assume that the longest part is the
    // street name.
    private Map<String, String> parseComma(String address) {

        HashMap<String, String> result = new HashMap<>();

        String[] strings = address.split(",");

        String firstPart = strings[0].trim();
        String secondPart = strings[1].trim();

        boolean firstCondition = firstPart.matches(".*[0-9]+.*");
        boolean secondCondition = secondPart.matches(".*[0-9]+.*");

        if (firstCondition && !secondCondition) {

            result.put("street", secondPart);
            result.put("housenumber", firstPart);

        } else if (!firstCondition && secondCondition) {

            result.put("street", firstPart);
            result.put("housenumber", secondPart);

        } else if (firstPart.split(" ").length > secondPart.split(" ").length) {

            result.put("street", firstPart);
            result.put("housenumber", secondPart);

        } else {

            result.put("street", secondPart);
            result.put("housenumber", firstPart);

        }

        return result;

    }

    private Map<String, String> parseContainsNo(String address) {

        HashMap<String, String> result = new HashMap<>();

        String lowerCaseAddress = address.toLowerCase();

        int index = Math.max(lowerCaseAddress.indexOf(" no "), lowerCaseAddress.indexOf(" nb "));

        String houseNumber = address.substring(index);
        String streetName = address.substring(0, index);

        result.put("street", streetName.trim());
        result.put("housenumber", houseNumber.trim());

        return result;
    }

    // This is a utility function that counts the number of numbers in an address.
    private int numberCount(String address) {

        String[] elements = address.split(" ");

        int result = 0;

        for (String element : elements) {
            if (element.matches("^[0-9]+.*$")) {

                result++;

            }
        }

        return result;
    }

    // If the address contains only one number, then we assume that the shortest
    // part is part of the house number.
    private Map<String, String> parseLoneNumber(String address) {

        HashMap<String, String> result = new HashMap<>();

        Pattern pattern = Pattern.compile("[0-9]+[^\\s]*");
        Matcher matcher = pattern.matcher(address);

        // contains the position of the number.
        matcher.find();

        logger.info(address);

        String houseNumber = "";
        String streetName = "";

        if (matcher.start() == 0) {

            houseNumber = address.substring(0, matcher.end());
            streetName = address.substring(matcher.end(), address.length());

        } else if (matcher.end() == address.length()) {

            houseNumber = address.substring(matcher.start());
            streetName = address.substring(0, matcher.start());

        } else {

            String firstHalf = address.substring(0, matcher.start()).trim();
            String secondHalf = address.substring(matcher.end()).trim();

            logger.info("first half is " + firstHalf);
            logger.info("second half is " + secondHalf);

            if (firstHalf.split(" ").length > secondHalf.split(" ").length) {

                houseNumber = address.substring(matcher.start());
                streetName = firstHalf;

            } else {

                streetName = address.substring(matcher.end());
                houseNumber = address.substring(0, matcher.end());

            }
        }

        result.put("street", streetName.trim());
        result.put("housenumber", houseNumber.trim());

        return result;
    }

}
