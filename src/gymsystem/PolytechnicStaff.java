/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

/**
 * Name: PolytechnicStaff
 * Purpose: Represents a Bahrain Polytechnic staff member who is a gym member.
 * Extends Member and adds staff-specific fields: staffID, department, position.
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public class PolytechnicStaff extends Member {

    private static final long serialVersionUID = 1L;

    private int    staffID;
    private String department;
    private String position;

    /**
     * Name: PolytechnicStaff Constructor
     * Purpose: Creates a new PolytechnicStaff object with all required fields.
     *
     * @param staffID    the unique staff ID
     * @param department the department the staff member works in
     * @param position   the job position
     * @param memberID   the gym member ID
     * @param isStaff    true for polytechnic staff
     * @param DOB        date of birth
     * @param gender     gender
     * @param firstName  first name
     * @param surname    surname
     * @param phone      phone number
     * @param email      email address
     * @param address    home address
     */
    public PolytechnicStaff(int staffID, String department, String position,
                            int memberID, boolean isStaff, String DOB, String gender,
                            String firstName, String surname, String phone,
                            String email, String address) {
        super(memberID, isStaff, DOB, gender, firstName, surname, phone, email, address);
        this.staffID    = staffID;
        this.department = department;
        this.position   = position;
    }

    /**
     * Name: getStaffID
     * Purpose: Returns the unique staff ID.
     *
     * @return staffID
     */
    public int getStaffID() { return staffID; }

    /**
     * Name: setStaffID
     * Purpose: Sets the staff ID.
     *
     * @param staffID the staff ID to set
     */
    public void setStaffID(int staffID) { this.staffID = staffID; }

    /**
     * Name: getDepartment
     * Purpose: Returns the department of the staff member.
     *
     * @return department
     */
    public String getDepartment() { return department; }

    /**
     * Name: setDepartment
     * Purpose: Sets the department of the staff member.
     *
     * @param department the department to set
     */
    public void setDepartment(String department) { this.department = department; }

    /**
     * Name: getPosition
     * Purpose: Returns the job position of the staff member.
     *
     * @return position
     */
    public String getPosition() { return position; }

    /**
     * Name: setPosition
     * Purpose: Sets the job position of the staff member.
     *
     * @param position the position to set
     */
    public void setPosition(String position) { this.position = position; }

    /**
     * Name: toString
     * Purpose: Returns a formatted String with all staff details.
     *
     * @return formatted String
     */
    @Override
    public String toString() {
        return super.toString()
             + " | Staff-ID: " + staffID
             + " | Dept: " + department
             + " | Position: " + position;
    }
}