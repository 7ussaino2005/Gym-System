/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

import java.util.ArrayList;

/**
 * Name: PersonalTrainer
 * Purpose: Represents a gym employee who is also a personal trainer.
 * Extends Employee and maintains an ArrayList of Members assigned to this trainer.
 * A personal trainer can have zero or more members (Req-6, Req-7, Req-10).
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public class PersonalTrainer extends Employee {

    private static final long serialVersionUID = 1L;

    private int personalTrainerID;
    private ArrayList<Member> personalTrainerMembers;

    /**
     * Name: PersonalTrainer Constructor
     * Purpose: Creates a new PersonalTrainer object and initialises
     * the members list as an empty ArrayList.
     *
     * @param personalTrainerID the unique personal trainer ID
     * @param employeeID        the employee ID
     * @param isPersonalTrainer true (always true for PersonalTrainer)
     * @param salary            the trainer salary
     * @param firstName         first name
     * @param surname           surname
     * @param phone             phone number
     * @param email             email address
     * @param address           home address
     */
    public PersonalTrainer(int personalTrainerID, int employeeID,
                           boolean isPersonalTrainer, double salary,
                           String firstName, String surname, String phone,
                           String email, String address) {
        super(employeeID, isPersonalTrainer, salary,
              firstName, surname, phone, email, address);
        this.personalTrainerID      = personalTrainerID;
        this.personalTrainerMembers = new ArrayList<Member>();
    }

    /**
     * Name: getPersonalTrainerID
     * Purpose: Returns the unique personal trainer ID.
     *
     * @return personalTrainerID
     */
    public int getPersonalTrainerID() { return personalTrainerID; }

    /**
     * Name: setPersonalTrainerID
     * Purpose: Sets the personal trainer ID.
     *
     * @param personalTrainerID the personal trainer ID to set
     */
    public void setPersonalTrainerID(int personalTrainerID) {
        this.personalTrainerID = personalTrainerID;
    }

    /**
     * Name: getPersonalTrainerMembers
     * Purpose: Returns the ArrayList of members assigned to this trainer.
     *
     * @return personalTrainerMembers
     */
    public ArrayList<Member> getPersonalTrainerMembers() {
        return personalTrainerMembers;
    }

    /**
     * Name: setPersonalTrainerMembers
     * Purpose: Replaces the entire members list.
     *
     * @param personalTrainerMembers the new ArrayList of Member objects
     */
    public void setPersonalTrainerMembers(ArrayList<Member> personalTrainerMembers) {
        this.personalTrainerMembers = personalTrainerMembers;
    }

    /**
     * Name: addMember
     * Purpose: Adds a member to this trainer's list (Req-6).
     *
     * @param m the Member to add
     */
    public void addMember(Member m) { personalTrainerMembers.add(m); }

    /**
     * Name: removeMember
     * Purpose: Removes a member from this trainer's list (Req-7, Req-9).
     *
     * @param m the Member to remove
     */
    public void removeMember(Member m) { personalTrainerMembers.remove(m); }

    /**
     * Name: getMemberCount
     * Purpose: Returns the number of members assigned to this trainer.
     *
     * @return number of assigned members
     */
    public int getMemberCount() { return personalTrainerMembers.size(); }

    /**
     * Name: toString
     * Purpose: Returns a formatted String with personal trainer details.
     *
     * @return formatted String
     */
    @Override
    public String toString() {
        return super.toString()
             + " | PT-ID: " + personalTrainerID
             + " | Members: " + personalTrainerMembers.size();
    }
}