/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

/**
 * Name: ActiveTrainerException
 * Purpose: Thrown when deleting a trainer who still has members
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public class ActiveTrainerException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Name: ActiveTrainerException Constructor
     * Purpose: Creates exception with message
     *
     * @param message error message
     */
    public ActiveTrainerException(String message) {
        super(message);
    }
}