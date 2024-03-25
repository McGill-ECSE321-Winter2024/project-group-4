package ca.mcgill.ecse321.fitnessplusplus.exception;

import ca.mcgill.ecse321.fitnessplusplus.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FitnessPlusPlusExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
    ErrorDto errorDTO = new ErrorDto(e.getMessage());
    return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
  }
}
