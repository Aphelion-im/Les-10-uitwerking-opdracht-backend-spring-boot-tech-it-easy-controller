package nl.novi.techiteasy.exceptions;

// Custom exception
// Runtime exceptions (Unchecked exceptions): These exceptions occur during the execution of the program and the compiler does not check for their presence.
public class RecordNotFoundException extends RuntimeException {
    // Deze waarde is enkel nuttig voor serialization/deserialization. Dit hoeft niet in je code.
    private static final long serialVersionUID = 1L;

    // De exception zonder message
    // Default no args constructor
    public RecordNotFoundException() {
        super(); // Roept de constructor aan van RuntimeException
    }

    // De exception met message
    public RecordNotFoundException(String message) {
        super(message); // Als je een custom message maakt, geeft Java niet meer de default constructor. Die constructor moet je dan zelf aan maken.
    }

}