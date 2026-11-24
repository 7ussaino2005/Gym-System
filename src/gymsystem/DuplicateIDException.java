/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

/**
 * Name: DuplicateIDException
 * Purpose: Thrown when a duplicate ID is detected
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public class DuplicateIDException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Name: DuplicateIDException Constructor
     * Purpose: Creates exception with message
     *
     * @param message error message
     */
    public DuplicateIDException(String message) {
        super(message);
    }
}
