/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

import java.io.Serializable;

/**
 * Name: GymPerson
 * Purpose: Abstract base class for all persons in the gym system.
 * Stores common personal details shared by both employees and members.
 * Implements Serializable so subclass objects can be persisted to file (Req-12, Unit 7).
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public abstract class GymPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String surname;
    private String phone;
    private String email;
    private String address;

    /**
     * Name: GymPerson Constructor
     * Purpose: Creates a new GymPerson with the supplied personal details.
     *
     * @param firstName the first name
     * @param surname   the surname
     * @param phone     the phone number
     * @param email     the email address
     * @param address   the home address
     */
    public GymPerson(String firstName, String surname, String phone,
                     String email, String address) {
        this.firstName = firstName;
        this.surname   = surname;
        this.phone     = phone;
        this.email     = email;
        this.address   = address;
    }

    /**
     * Name: getFirstName
     * Purpose: Returns the first name of the person.
     *
     * @return firstName
     */
    public String getFirstName() { return firstName; }

    /**
     * Name: setFirstName
     * Purpose: Sets the first name of the person.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * Name: getSurname
     * Purpose: Returns the surname of the person.
     *
     * @return surname
     */
    public String getSurname() { return surname; }

    /**
     * Name: setSurname
     * Purpose: Sets the surname of the person.
     *
     * @param surname the surname to set
     */
    public void setSurname(String surname) { this.surname = surname; }

    /**
     * Name: getPhone
     * Purpose: Returns the phone number of the person.
     *
     * @return phone
     */
    public String getPhone() { return phone; }

    /**
     * Name: setPhone
     * Purpose: Sets the phone number of the person.
     *
     * @param phone the phone to set
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * Name: getEmail
     * Purpose: Returns the email address of the person.
     *
     * @return email
     */
    public String getEmail() { return email; }

    /**
     * Name: setEmail
     * Purpose: Sets the email address of the person.
     *
     * @param email the email to set
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Name: getAddress
     * Purpose: Returns the home address of the person.
     *
     * @return address
     */
    public String getAddress() { return address; }

    /**
     * Name: setAddress
     * Purpose: Sets the home address of the person.
     *
     * @param address the address to set
     */
    public void setAddress(String address) { this.address = address; }

    /**
     * Name: toString
     * Purpose: Returns a formatted String of common personal details.
     *
     * @return formatted String
     */
    @Override
    public String toString() {
        return "name: " + firstName + " " + surname
             + " | Phone: " + phone
             + " | Email: " + email
             + " | Address: " + address;
    }
}