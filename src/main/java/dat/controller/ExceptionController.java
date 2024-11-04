package dat.controller;



import dat.entities.Message;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ExceptionController {

    private final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    public void apiExceptionsHandler(ApiException e, Context ctx){

        log.error("{} {}", e.getStatusCode(), e.getMessage());
        ctx.status(e.getStatusCode());

        // Format the current LocalDateTime
        String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Create the Message instance
        Message errorResponse = new Message(e.getStatusCode(), e.getMessage());

        // Send the error response with the formatted time as a separate field
        ctx.json(Map.of(
                "error message", errorResponse,
                "time of error", formattedTime
        ));

    }

    public void exceptionHandler(Exception e, Context ctx) {
        log.error(ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage());
        ctx.status(500);
        ctx.json(new Message(500, e.getMessage()));
    }



}

