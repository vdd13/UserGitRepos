package pl.dom.UserGitRepos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import pl.dom.UserGitRepos.dto.NoUserDto;

@RestControllerAdvice
public class NoUserAdvice {

    @ExceptionHandler(NoUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NoUserDto noUserHandler(NoUserException e) {
    	return new NoUserDto(HttpStatus.NOT_FOUND.value(), e.getUser());
    }
}
