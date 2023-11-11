package nl.novi.techiteasy.controllers;

import nl.novi.techiteasy.exceptions.RecordNotFoundException;
import nl.novi.techiteasy.exceptions.TelevisionNameTooLongException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
// @RequestMapping("/persons")
// Alle paden (In Postman) beginnen met /persons - Change base URL to: http://localhost:8080/persons - Optioneel: Onder @RestController plaatsen
public class TelevisionsController {

    // De lijst is static, omdat er maar 1 lijst kan zijn.
    // De lijst is final, omdat de lijst op zich niet kan veranderen, enkel de waardes die er in staan.
    private static final List<String> televisionDatabase = new ArrayList<>();

    // Show all televisions
    @GetMapping("/televisions")
    public ResponseEntity<Object> getAllTelevisions() { // Of ResponseEntity<String>
        // return "Televisions"; werkt ook
        // return ResponseEntity.ok(televisionDatabase);
        return new ResponseEntity<>(televisionDatabase, HttpStatus.OK); // 200 OK
    }

    // Show 1 television
    @GetMapping("televisions/{id}")
    public ResponseEntity<Object> getTelevision(@PathVariable("id") int id) { // Verschil met: @PathVariable int id?
        // return ResponseEntity.ok("television with id: " + id);
        // Return de waarde die op index(id) staat en een 200 status
        // Wanneer de gebruiker een request doet met id=300, maar de lijst heeft maar 3 items.
        // Dan gooit java een IndexOutOfBoundsException. De bonus methode in de ExceptionController vangt dit op en stuurt de gebruiker een berichtje.
        return new ResponseEntity<>(televisionDatabase.get(id), HttpStatus.OK);
    }

    // Post/create 1 television
    @PostMapping("/televisions")
    public ResponseEntity<String> addTelevision(@RequestBody String television) { // Of ResponseEntity<Object>
        // return ResponseEntity.created(null).body("television"); - Oude manier van noteren?
        // Bonus bonus: check voor 20 letters:
        if (television.length() > 20) {
            throw new TelevisionNameTooLongException("Televisienaam is te lang");
        } else {
            // Voeg de televisie uit de parameter toe aan de lijst
            televisionDatabase.add(television);
            // Return de televisie uit de parameter met een 201 status
            // return ResponseEntity.created(null).body(television);
            return new ResponseEntity<>("Television: " + television + " added.", HttpStatus.CREATED); // 201
        }
    }

    // Update television
    @PutMapping("/televisions/{id}")
    public ResponseEntity<Object> updateTelevision(@PathVariable int id, @RequestBody String television) {
        // In de vorige methodes hebben we impliciet gebruik gemaakt van "IndexOUtOfBoundsException" als het id groter was dan de lijst.
        // In deze methode checken we daar expliciet voor en gooien we een custom exception op.
        if (televisionDatabase.isEmpty() || id > televisionDatabase.size()) {
            throw new RecordNotFoundException("Record met id: " + id + " niet gevonden in de database.");
        } else {
            // Vervang de waarde op index(id) met de television uit de parameter
            televisionDatabase.set(id, television);
            // Return een 204 status
            // return ResponseEntity.noContent().build();
            return new ResponseEntity<>(television + " with id#" + id + " updated!", HttpStatus.OK);
        }
    }

    @DeleteMapping("/televisions/{id}")
    public ResponseEntity<Object> deleteTelevision(@PathVariable int id) {
        // Vervang de waarde op index(id) met null. Als we de waarde zouden verwijderen, zouden alle indexen die na deze waarden komen in de lijst met 1 afnemen.
        televisionDatabase.set(id, null); // Ipv remove()? Geeft een entry met null in de ArrayList
        // televisionDatabase.remove(id);
        // return new ResponseEntity<>("deleted", HttpStatus.NO_CONTENT); // Met 204 No Content kun je geen bericht mee sturen
        return new ResponseEntity<>("Television: " + id + " deleted.", HttpStatus.GONE); // 410 Gone
    }

}