package com.Ahims.Controller.HipController;

import com.Ahims.Exception.InvalidRequestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for handling requests related to "hello" endpoint.
 * Logs the incoming request data and processes it to determine a status.
 * Responds with either "success" or "fail" based on the status extracted from the request data.
 */
@RestController
public class HipController {

    private static final Logger logger = LoggerFactory.getLogger(HipController.class);
    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";

    /**
     * Processes incoming POST requests to the "/hello" endpoint.
     * Extracts the status from the request data and responds with either "success" or "fail".
     * In case of errors, returns a BAD_REQUEST status with an appropriate error message.
     *
     * @param data the request body containing JSON data with a status field.
     * @return a ResponseEntity containing the status ("success" or "fail") or an error message for invalid requests.
     * @throws HttpMessageNotReadableException if the request body is malformed.
     * @throws InvalidRequestException if the request data is invalid.
     */
    @PostMapping("/hello")
    @ResponseBody
    public ResponseEntity<String> test(@RequestBody String data) {
        logger.info("Controller Method: /hello, Request Data: {}", data);

        try {
            String status = extractStatus(data);
            if (SUCCESS_STATUS.equals(status)) {
                logger.info("/hello SUCCESS");
                return ResponseEntity.ok(SUCCESS_STATUS);
            } else {
                return ResponseEntity.ok(FAIL_STATUS);
            }
        } catch (HttpMessageNotReadableException | InvalidRequestException e) {
            logger.error("Error processing request: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data: " + e.getMessage());
        }
    }

    /**
     * Extracts the value of the "status" field from the provided JSON string.
     *
     * @param data the JSON string containing a "status" field.
     * @return the value of the "status" field extracted from the provided JSON string.
     */
    private String extractStatus(String data) {
        JSONObject jsonObject = new JSONObject(data);
        return jsonObject.getString("status");
    }
}
