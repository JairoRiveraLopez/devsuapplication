package com.application.jrl_technical_test.Exception;


import com.application.jrl_technical_test.Web.DTO.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionDTO> getGeneralException(Exception e){
        ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({InternalAppException.class})
    public ResponseEntity<ExceptionDTO> getGeneralException(InternalAppException e){
        return new ResponseEntity<>(e.getExceptionDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ExceptionDTO> getGeneralException(BadRequestException e){
        return new ResponseEntity<>(e.getExceptionDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
