package likelion.babsim.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<ErrorResult> EntityNotFoundExHandle(EntityNotFoundException e){
        log.info("EntityNotFoundException occurs! : {}",e);
        ErrorResult errorResult = new ErrorResult("EntityNotFound-EX",e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateNftException.class)
    public ResponseEntity<ErrorResult> CreateNftExHandle(CreateNftException e){
        log.info("CreateNftException occurs! : {}",e);
        ErrorResult errorResult = new ErrorResult("CreateNftEx-EX",e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateTokenIdException.class)
    public ResponseEntity<ErrorResult> DuplicateTokenIdExHandle(DuplicateTokenIdException e){
        log.info("DuplicateTokenIdException occurs! : {}",e);
        ErrorResult errorResult = new ErrorResult("DuplicateTokenId-EX",e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApproveTokenException.class)
    public ResponseEntity<ErrorResult> ApproveTokenExHandle(ApproveTokenException e){
        log.info("ApproveTokenException occurs! : {}",e);
        ErrorResult errorResult = new ErrorResult("ApproveToken-EX",e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

}
