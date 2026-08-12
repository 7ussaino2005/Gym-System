/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

/**
 * Name: InvalidDataException
 * Purpose: Thrown when invalid data is entered
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public class InvalidDataException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Name: InvalidDataException Constructor
     * Purpose: Creates exception with message
     *
     * @param message error message
     */
    public InvalidDataException(String message) {
        super(message);
    }
}
