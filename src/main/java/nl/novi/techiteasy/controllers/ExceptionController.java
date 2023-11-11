package nl.novi.techiteasy.controllers;

import nl.novi.techiteasy.exceptions.RecordNotFoundException;
import nl.novi.techiteasy.exceptions.TelevisionNameTooLongException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// @ControllerAdvice - Om een Exception controller aan te geven. Verwijst (En stuurt waarschijnlijk aan) in de code naar elk van de custom exceptions in het mapje: exceptions.
public class ExceptionController {

    // Deze exception handler vangt elke RecordNotFoundException op die naar de gebruiker gegooid wordt en returned daar voor in de plaats een ResponseEntity met de Message en de NOT_FOUND-status (404)
    // Custom exception 1
    @ExceptionHandler(value = RecordNotFoundException.class) // Verwijst naar dit bestand
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Exception handler met message. Constructor in het Exception bestand super(message)
    // IndexOutOfBoundsException ioe is al een bestaande Java Exception.
    @ExceptionHandler(value = IndexOutOfBoundsException.class)
    public ResponseEntity<Object> exception(IndexOutOfBoundsException exception) {
        return new ResponseEntity<>("Dit id staat niet in de database", HttpStatus.NOT_FOUND);
    }

    // Exception zonder message (super())
    // Custom exception 2
    // Zonder deze handler krijg je een error status 500
    @ExceptionHandler(value = TelevisionNameTooLongException.class)
    public ResponseEntity<String> exception(TelevisionNameTooLongException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST); // .getMessage: "Televisienaam is te lang"
    }



}
