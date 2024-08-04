package likelion.babsim.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorResult> notEnoughMoneyExHandle(NotEnoughMoneyException e){
        log.info("NotEnoughMoneyException occurs! : {}",e);
        ErrorResult errorResult = new ErrorResult("NotEnoughMoney-EX",e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResult> noSuchElementExHandle(EntityNotFoundException e){
        log.info("EntityNotFoundException occurs! : {}",e);
        ErrorResult errorResult = new ErrorResult("EntityNotFoundException-EX",e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateNftException.class)
    public ResponseEntity<ErrorResult> noSuchElementExHandle(CreateNftException e){
        log.info("CreateNftException occurs! : {}",e);
        ErrorResult errorResult = new ErrorResult("CreateNftException-EX",e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

}
