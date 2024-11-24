package com.Ahims.Controller.HipController;

import com.Ahims.Exception.InvalidRequestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HipController {

    private static final Logger logger = LoggerFactory.getLogger(HipController.class);

    @PostMapping("/hello")
    @ResponseBody
    public String test(@RequestBody String data)
    {
        logger.info("Controller Method: /hello, Request Data: {}", data);
        try{
            JSONObject jsonObject = new JSONObject(data);

            if(jsonObject.get("status").equals("success")) {
                logger.info("/hello SUCCESS ");
                return "success";
            }
            else {
                return "fail";
            }
        }
        catch (HttpMessageNotReadableException e) {
            logger.error("Error processing request: {}", e.getMessage(), e);
            throw new HttpMessageNotReadableException("Invalid request data: " + e.getMessage()); // Custom exception
        }
        catch (InvalidRequestException e) {
            logger.error("Error processing request: {}", e.getMessage(), e);
            throw new InvalidRequestException("Invalid request data: " + e.getMessage()); // Custom exception
        }

    }
}

