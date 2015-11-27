package ua.antontereshin.areacalculation.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ua.antontereshin.areacalculation.utils.Utils.getGeneralCauseMessage;

/**
 * Created by Anton on 04.11.2015.
 */
@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception e){
        return getGeneralCauseMessage(e);
    }

}
