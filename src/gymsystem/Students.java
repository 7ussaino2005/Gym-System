/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

/**
 * Name: Students
 * Purpose: Represents a Bahrain Polytechnic student who is a gym member.
 * Extends Member and adds student-specific fields: studentID, course, and team.
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public class Students extends Member {

    private static final long serialVersionUID = 1L;

    private int    studentID;
    private String course;
    private String team;

    /**
     * Name: Students Constructor
     * Purpose: Creates a new Students object with all required fields.
     *
     * @param studentID the unique student ID
     * @param course    the degree / course enrolled in
     * @param team      the sports team (empty String if not in a team)
     * @param memberID  the gym member ID
     * @param isStaff   false for students
     * @param DOB       date of birth
     * @param gender    gender
     * @param firstName first name
     * @param surname   surname
     * @param phone     phone number
     * @param email     email address
     * @param address   home address
     */
    public Students(int studentID, String course, String team,
                    int memberID, boolean isStaff, String DOB, String gender,
                    String firstName, String surname, String phone,
                    String email, String address) {
        super(memberID, isStaff, DOB, gender, firstName, surname, phone, email, address);
        this.studentID = studentID;
        this.course    = course;
        this.team      = (team == null) ? "" : team;
    }

    /**
     * Name: getStudentID
     * Purpose: Returns the unique student ID.
     *
     * @return studentID
     */
    public int getStudentID() { return studentID; }

    /**
     * Name: setStudentID
     * Purpose: Sets the student ID.
     *
     * @param studentID the student ID to set
     */
    public void setStudentID(int studentID) { this.studentID = studentID; }

    /**
     * Name: getCourse
     * Purpose: Returns the course the student is enrolled in.
     *
     * @return course
     */
    public String getCourse() { return course; }

    /**
     * Name: setCourse
     * Purpose: Sets the course the student is enrolled in.
     *
     * @param course the course to set
     */
    public void setCourse(String course) { this.course = course; }

    /**
     * Name: getTeam
     * Purpose: Returns the sports team of the student.
     * Returns empty String if not in a team.
     *
     * @return team
     */
    public String getTeam() { return team; }

    /**
     * Name: setTeam
     * Purpose: Sets the sports team of the student.
     *
     * @param team the team to set (empty String if not in a team)
     */
    public void setTeam(String team) { this.team = (team == null) ? "" : team; }

    /**
     * Name: toString
     * Purpose: Returns a formatted String with all student details.
     *
     * @return formatted String
     */
    @Override
    public String toString() {
        return super.toString()
             + " | Student-ID: " + studentID
             + " | Course: " + course
             + " | Team: " + (team.isEmpty() ? "N/A" : team);
    }
}