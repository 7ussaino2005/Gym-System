/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

/**
 * Name: Member
 * Purpose: Abstract class representing a gym member.
 * Extends GymPerson and adds member-specific fields: memberID, isStaff,
 * date of birth, gender, and an optional link to a PersonalTrainer.
 * Concrete subclasses are Students and PolytechnicStaff.
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public abstract class Member extends GymPerson {

    private static final long serialVersionUID = 1L;

    private int memberID;
    private boolean isStaff;
    private PersonalTrainer personalTrainer;
    private String DOB;
    private String gender;

    /**
     * Name: Member Constructor
     * Purpose: Creates a new Member with all required fields.
     * personalTrainer is initialised to null.
     *
     * @param memberID  the unique member ID
     * @param isStaff   true if the member is polytechnic staff
     * @param DOB       date of birth
     * @param gender    gender
     * @param firstName first name
     * @param surname   surname
     * @param phone     phone number
     * @param email     email address
     * @param address   home address
     */
    public Member(int memberID, boolean isStaff, String DOB, String gender,
                  String firstName, String surname, String phone,
                  String email, String address) {
        super(firstName, surname, phone, email, address);
        this.memberID        = memberID;
        this.isStaff         = isStaff;
        this.DOB             = DOB;
        this.gender          = gender;
        this.personalTrainer = null;
    }

    /**
     * Name: getMemberID
     * Purpose: Returns the unique member ID.
     *
     * @return memberID
     */
    public int getMemberID() { return memberID; }

    /**
     * Name: setMemberID
     * Purpose: Sets the member ID.
     *
     * @param memberID the member ID to set
     */
    public void setMemberID(int memberID) { this.memberID = memberID; }

    /**
     * Name: getIsStaff
     * Purpose: Returns true if this member is polytechnic staff.
     *
     * @return isStaff
     */
    public boolean getIsStaff() { return isStaff; }

    /**
     * Name: setIsStaff
     * Purpose: Sets whether this member is polytechnic staff.
     *
     * @param isStaff true if staff
     */
    public void setIsStaff(boolean isStaff) { this.isStaff = isStaff; }

    /**
     * Name: getPersonalTrainer
     * Purpose: Returns the PersonalTrainer assigned to this member,
     * or null if not assigned.
     *
     * @return personalTrainer or null
     */
    public PersonalTrainer getPersonalTrainer() { return personalTrainer; }

    /**
     * Name: setPersonalTrainer
     * Purpose: Sets or clears the personal trainer assigned to this member.
     *
     * @param personalTrainer the trainer to assign, or null to unassign
     */
    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    /**
     * Name: getDOB
     * Purpose: Returns the date of birth of the member.
     *
     * @return DOB
     */
    public String getDOB() { return DOB; }

    /**
     * Name: setDOB
     * Purpose: Sets the date of birth of the member.
     *
     * @param DOB the date of birth to set
     */
    public void setDOB(String DOB) { this.DOB = DOB; }

    /**
     * Name: getGender
     * Purpose: Returns the gender of the member.
     *
     * @return gender
     */
    public String getGender() { return gender; }

    /**
     * Name: setGender
     * Purpose: Sets the gender of the member.
     *
     * @param gender the gender to set
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     * Name: toString
     * Purpose: Returns a formatted String with member details.
     *
     * @return formatted String
     */
    @Override
    public String toString() {
        String trainerInfo = (personalTrainer == null) ? "None"
                : personalTrainer.getFirstName() + " " + personalTrainer.getSurname();
        return super.toString()
             + " | Member ID: " + memberID
             + " | DOB: " + DOB
             + " | Gender: " + gender
             + " | Trainer: " + trainerInfo;
    }
}