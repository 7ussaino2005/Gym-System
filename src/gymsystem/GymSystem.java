/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Name: GymSystem
 * Purpose: Central business-logic class (architecture domain) for the
 * Bahrain Polytechnic Gymnasium Administration System.
 * Maintains two ArrayLists: employees (including PersonalTrainer objects)
 * and members (including Students and PolytechnicStaff objects).
 * Handles all CRUD operations, trainer-member assignment, object serialization,
 * startup file loading, and marketing report generation.
 * GUI logic is intentionally kept out of this class (Req-16).
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public class GymSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Binary file used for object serialization between runs (Req-12). */
    private static final String DATA_FILE      = "gymdata.ser";

    /** Plain-text startup file used only on the very first run (Req-13). */
    private static final String STARTUP_FILE   = "startup.txt";

    /** Plain-text marketing report output file (Req-11). */
    private static final String MARKETING_FILE = "marketingReport.txt";

    /**
     * All employees registered in the system.
     * PersonalTrainer objects are also stored here (they extend Employee).
     */
    private ArrayList<Employee> employees;

    /**
     * All gym members registered in the system.
     * Both Students and PolytechnicStaff objects are stored here.
     */
    private ArrayList<Member> members;

    /** Next memberID to assign. Never re-used (Req-3). */
    private int nextMemberID;

    /** Next employeeID to assign. Never re-used. */
    private int nextEmployeeID;

    /** Next personalTrainerID to assign. Never re-used. */
    private int nextTrainerID;

    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    /**
     * Name: GymSystem Constructor
     * Purpose: Creates an empty GymSystem with empty ArrayLists and
     * ID counters set to starting values.
     */
    public GymSystem() {
        employees      = new ArrayList<Employee>();
        members        = new ArrayList<Member>();
        nextMemberID   = 1000;
        nextEmployeeID = 1;
        nextTrainerID  = 1;
    }

    // -----------------------------------------------------------------------
    // Req-12 and Req-13 : Persistence
    // -----------------------------------------------------------------------

    /**
     * Name: loadSystem
     * Purpose: Static factory method. De-serializes gymdata.ser if it exists
     * (Req-12), otherwise loads startup.txt on first run (Req-13).
     * Returns an empty GymSystem if neither file exists.
     *
     * @return a ready-to-use GymSystem instance
     */
    public static GymSystem loadSystem() {
        System.out.println("Looking in: " + new java.io.File(".").getAbsolutePath());
        File dataFile = new File(DATA_FILE);
        if (dataFile.exists()) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(dataFile));
                GymSystem sys = (GymSystem) ois.readObject();
                return sys;
            } catch (FileNotFoundException e) {
                System.err.println("Data file not found: " + e.getMessage());
            } catch (StreamCorruptedException e) {
                System.err.println("Data file corrupted: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println("Class not found: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("IO error: " + e.getMessage());
            } finally {
                if (ois != null) {
                    try { ois.close(); } catch (IOException e) { }
                }
            }
        }
        GymSystem sys = new GymSystem();
        File startupFile = new File(STARTUP_FILE);
        if (startupFile.exists()) {
            try {
                sys.loadFromStartupFile(STARTUP_FILE);
            } catch (IOException e) {
                System.err.println("Could not read startup file: " + e.getMessage());
            }
        }
        return sys;
    }

    /**
     * Name: saveSystem
     * Purpose: Serializes the entire GymSystem to gymdata.ser so all data
     * persists between runs (Req-12, Req-14).
     * Must be called before the program exits.
     *
     * @throws IOException if the file cannot be written
     */
    public void saveSystem() throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE));
            oos.writeObject(this);
        } finally {
            if (oos != null) {
                try { oos.close(); } catch (IOException e) { }
            }
        }
    }

    /**
     * Name: loadFromStartupFile
     * Purpose: Parses the plain-text startup.txt and populates the system
     * with pre-determined data on first run (Req-13).
     * Uses Scanner on a File object (Unit 7 text files).
     *
     * @param filePath path to the startup text file
     * @throws IOException if the file cannot be read
     */
    public void loadFromStartupFile(String filePath) throws IOException {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filePath));
            int numEmployees = Integer.parseInt(sc.nextLine().trim());
            for (int i = 0; i < numEmployees; i++) {
                String type      = sc.nextLine().trim();
                String firstName = sc.nextLine().trim();
                String surname   = sc.nextLine().trim();
                String address   = sc.nextLine().trim();
                String phone     = sc.nextLine().trim();
                double salary    = Double.parseDouble(sc.nextLine().trim());
                if (type.equalsIgnoreCase("E")) {
                    int empID = nextEmployeeID++;
                    employees.add(new Employee(empID, false, salary,
                            firstName, surname, phone, "", address));
                } else if (type.equalsIgnoreCase("PT")) {
                    int empID     = nextEmployeeID++;
                    int trainerID = nextTrainerID++;
                    PersonalTrainer pt = new PersonalTrainer(trainerID, empID, true,
                            salary, firstName, surname, phone, "", address);
                    int numMembers = Integer.parseInt(sc.nextLine().trim());
                    for (int j = 0; j < numMembers; j++) {
                        Member m = parseMemberRecord(sc);
                        members.add(m);
                        pt.addMember(m);
                        m.setPersonalTrainer(pt);
                    }
                    employees.add(pt);
                }
            }
        } finally {
            if (sc != null) { sc.close(); }
        }
    }

    /**
     * Name: parseMemberRecord
     * Purpose: Reads one member record from Scanner during startup loading.
     * Handles both "staff" and "student" member types.
     *
     * @param sc Scanner positioned at the member-type line
     * @return the constructed Member (PolytechnicStaff or Students)
     * @throws IOException if reading fails
     */
    private Member parseMemberRecord(Scanner sc) throws IOException {
        String memberType = sc.nextLine().trim();
        String firstName  = sc.nextLine().trim();
        String surname    = sc.nextLine().trim();
        String address    = sc.nextLine().trim();
        String dob        = sc.nextLine().trim();
        String phone      = sc.nextLine().trim();
        String gender     = sc.nextLine().trim();
        int    memberID   = nextMemberID++;
        if (memberType.equalsIgnoreCase("staff")) {
            String position   = sc.nextLine().trim();
            String department = sc.nextLine().trim();
            return new PolytechnicStaff(memberID, department, position,
                    memberID, true, dob, gender,
                    firstName, surname, phone, "", address);
        } else {
            String course = sc.nextLine().trim();
            String team   = sc.nextLine().trim();
            return new Students(memberID, course, team,
                    memberID, false, dob, gender,
                    firstName, surname, phone, "", address);
        }
    }

    // -----------------------------------------------------------------------
    // Req-2 : Add employee
    // -----------------------------------------------------------------------

    /**
     * Name: addEmployee
     * Purpose: Creates and registers a new Employee or PersonalTrainer (Req-2).
     * Assigns employeeID and personalTrainerID automatically. IDs never re-used.
     *
     * @param isPersonalTrainer true if the employee is also a personal trainer
     * @param salary            salary – must be >= 0
     * @param firstName         first name – must not be null or blank
     * @param surname           surname – must not be null or blank
     * @param phone             phone number – must not be null or blank
     * @param email             email address
     * @param address           home address – must not be null or blank
     * @return the newly created Employee or PersonalTrainer object
     * @throws NullPointerException  if any String parameter is null (Req-15)
     * @throws InvalidDataException  if any required field is blank or salary negative
     */
    public Employee addEmployee(boolean isPersonalTrainer, double salary,
                                String firstName, String surname,
                                String phone, String email, String address)
            throws InvalidDataException {
        checkNotNull(firstName, "First name");
        checkNotNull(surname,   "Surname");
        checkNotNull(phone,     "Phone");
        checkNotNull(address,   "Address");
        checkNotBlank(firstName, "First name");
        checkNotBlank(surname,   "Surname");
        checkNotBlank(phone,     "Phone");
        checkNotBlank(address,   "Address");
        if (salary < 0) {
            throw new InvalidDataException("Salary cannot be negative.");
        }
        int empID = nextEmployeeID++;
        if (isPersonalTrainer) {
            int trainerID = nextTrainerID++;
            PersonalTrainer pt = new PersonalTrainer(trainerID, empID, true,
                    salary, firstName, surname, phone, email, address);
            employees.add(pt);
            return pt;
        } else {
            Employee emp = new Employee(empID, false, salary,
                    firstName, surname, phone, email, address);
            employees.add(emp);
            return emp;
        }
    }

    // -----------------------------------------------------------------------
    // Req-3 : Add member
    // -----------------------------------------------------------------------

    /**
     * Name: addStudent
     * Purpose: Creates and registers a new Students member (Req-3).
     * Assigns memberID automatically. IDs never re-used.
     *
     * @param course    degree / course enrolled in – must not be null or blank
     * @param team      sports team – empty String if not in a team
     * @param dob       date of birth – must not be null or blank
     * @param gender    gender – must not be null or blank
     * @param firstName first name – must not be null or blank
     * @param surname   surname – must not be null or blank
     * @param phone     phone number – must not be null or blank
     * @param email     email address
     * @param address   home address – must not be null or blank
     * @return the newly created Students object
     * @throws NullPointerException  if any String parameter is null (Req-15)
     * @throws InvalidDataException  if any required field is blank
     * @throws DuplicateIDException  if the generated memberID already exists
     */
    public Students addStudent(String course, String team,
                               String dob, String gender,
                               String firstName, String surname,
                               String phone, String email, String address)
            throws InvalidDataException, DuplicateIDException {
        checkNotNull(firstName, "First name");
        checkNotNull(surname,   "Surname");
        checkNotNull(phone,     "Phone");
        checkNotNull(address,   "Address");
        checkNotNull(dob,       "Date of birth");
        checkNotNull(gender,    "Gender");
        checkNotNull(course,    "Course");
        checkNotBlank(firstName, "First name");
        checkNotBlank(surname,   "Surname");
        checkNotBlank(phone,     "Phone");
        checkNotBlank(address,   "Address");
        checkNotBlank(dob,       "Date of birth");
        checkNotBlank(gender,    "Gender");
        checkNotBlank(course,    "Course");
        int memberID = nextMemberID++;
        if (findMemberByID(memberID) != null) {
            throw new DuplicateIDException("Member ID " + memberID + " already exists.");
        }
        Students s = new Students(memberID, course, (team == null ? "" : team),
                memberID, false, dob, gender,
                firstName, surname, phone, email, address);
        members.add(s);
        return s;
    }

    /**
     * Name: addPolytechnicStaff
     * Purpose: Creates and registers a new PolytechnicStaff member (Req-3).
     * Assigns memberID automatically. IDs never re-used.
     *
     * @param position   job position – must not be null or blank
     * @param department department name – must not be null or blank
     * @param dob        date of birth – must not be null or blank
     * @param gender     gender – must not be null or blank
     * @param firstName  first name – must not be null or blank
     * @param surname    surname – must not be null or blank
     * @param phone      phone number – must not be null or blank
     * @param email      email address
     * @param address    home address – must not be null or blank
     * @return the newly created PolytechnicStaff object
     * @throws NullPointerException  if any String parameter is null (Req-15)
     * @throws InvalidDataException  if any required field is blank
     * @throws DuplicateIDException  if the generated memberID already exists
     */
    public PolytechnicStaff addPolytechnicStaff(String position, String department,
                                                String dob, String gender,
                                                String firstName, String surname,
                                                String phone, String email, String address)
            throws InvalidDataException, DuplicateIDException {
        checkNotNull(firstName,  "First name");
        checkNotNull(surname,    "Surname");
        checkNotNull(phone,      "Phone");
        checkNotNull(address,    "Address");
        checkNotNull(dob,        "Date of birth");
        checkNotNull(gender,     "Gender");
        checkNotNull(position,   "Position");
        checkNotNull(department, "Department");
        checkNotBlank(firstName,  "First name");
        checkNotBlank(surname,    "Surname");
        checkNotBlank(phone,      "Phone");
        checkNotBlank(address,    "Address");
        checkNotBlank(dob,        "Date of birth");
        checkNotBlank(gender,     "Gender");
        checkNotBlank(position,   "Position");
        checkNotBlank(department, "Department");
        int memberID = nextMemberID++;
        if (findMemberByID(memberID) != null) {
            throw new DuplicateIDException("Member ID " + memberID + " already exists.");
        }
        PolytechnicStaff ps = new PolytechnicStaff(memberID, department, position,
                memberID, true, dob, gender,
                firstName, surname, phone, email, address);
        members.add(ps);
        return ps;
    }

    // -----------------------------------------------------------------------
    // Req-4 : Update member
    // -----------------------------------------------------------------------

    /**
     * Name: updateMember
     * Purpose: Updates the common fields of an existing member (Req-4).
     * The memberID is never changed.
     *
     * @param memberID  ID of the member to update
     * @param firstName new first name – must not be null or blank
     * @param surname   new surname – must not be null or blank
     * @param phone     new phone – must not be null or blank
     * @param email     new email
     * @param address   new address – must not be null or blank
     * @param dob       new date of birth – must not be null or blank
     * @param gender    new gender – must not be null or blank
     * @throws NullPointerException     if any String parameter is null (Req-15)
     * @throws InvalidDataException     if any required field is blank
     * @throws IllegalArgumentException if member not found
     */
    public void updateMember(int memberID, String firstName, String surname,
                             String phone, String email, String address,
                             String dob, String gender)
            throws InvalidDataException {
        Member m = findMemberByID(memberID);
        if (m == null) {
            throw new IllegalArgumentException("Member ID " + memberID + " not found.");
        }
        checkNotNull(firstName, "First name");
        checkNotNull(surname,   "Surname");
        checkNotNull(phone,     "Phone");
        checkNotNull(address,   "Address");
        checkNotNull(dob,       "Date of birth");
        checkNotNull(gender,    "Gender");
        checkNotBlank(firstName, "First name");
        checkNotBlank(surname,   "Surname");
        checkNotBlank(phone,     "Phone");
        checkNotBlank(address,   "Address");
        checkNotBlank(dob,       "Date of birth");
        checkNotBlank(gender,    "Gender");
        m.setFirstName(firstName);
        m.setSurname(surname);
        m.setPhone(phone);
        m.setEmail(email);
        m.setAddress(address);
        m.setDOB(dob);
        m.setGender(gender);
    }

    /**
     * Name: updateStudentExtra
     * Purpose: Updates the student-specific fields (course, team).
     *
     * @param memberID ID of the Students member
     * @param course   new course – must not be null or blank
     * @param team     new team – empty String if not in a team
     * @throws NullPointerException     if course is null (Req-15)
     * @throws InvalidDataException     if course is blank
     * @throws IllegalArgumentException if member not found or not a student
     */
    public void updateStudentExtra(int memberID, String course, String team)
            throws InvalidDataException {
        Member m = findMemberByID(memberID);
        if (m == null) {
            throw new IllegalArgumentException("Member ID " + memberID + " not found.");
        }
        if (!(m instanceof Students)) {
            throw new IllegalArgumentException("Member " + memberID + " is not a student.");
        }
        checkNotNull(course, "Course");
        checkNotBlank(course, "Course");
        Students s = (Students) m;
        s.setCourse(course);
        s.setTeam(team == null ? "" : team);
    }

    /**
     * Name: updateStaffExtra
     * Purpose: Updates the staff-specific fields (position, department).
     *
     * @param memberID   ID of the PolytechnicStaff member
     * @param position   new position – must not be null or blank
     * @param department new department – must not be null or blank
     * @throws NullPointerException     if any parameter is null (Req-15)
     * @throws InvalidDataException     if any field is blank
     * @throws IllegalArgumentException if member not found or not staff
     */
    public void updateStaffExtra(int memberID, String position, String department)
            throws InvalidDataException {
        Member m = findMemberByID(memberID);
        if (m == null) {
            throw new IllegalArgumentException("Member ID " + memberID + " not found.");
        }
        if (!(m instanceof PolytechnicStaff)) {
            throw new IllegalArgumentException("Member " + memberID + " is not polytechnic staff.");
        }
        checkNotNull(position,   "Position");
        checkNotNull(department, "Department");
        checkNotBlank(position,   "Position");
        checkNotBlank(department, "Department");
        PolytechnicStaff ps = (PolytechnicStaff) m;
        ps.setPosition(position);
        ps.setDepartment(department);
    }

    // -----------------------------------------------------------------------
    // Req-5 : Update employee
    // -----------------------------------------------------------------------

    /**
     * Name: updateEmployee
     * Purpose: Updates editable fields of an existing employee (Req-5).
     * The employeeID is never changed.
     *
     * @param employeeID ID of employee to update
     * @param firstName  new first name – must not be null or blank
     * @param surname    new surname – must not be null or blank
     * @param phone      new phone – must not be null or blank
     * @param email      new email
     * @param address    new address – must not be null or blank
     * @param salary     new salary – must be >= 0
     * @throws NullPointerException     if any String parameter is null (Req-15)
     * @throws InvalidDataException     if any field is blank or salary negative
     * @throws IllegalArgumentException if employee not found
     */
    public void updateEmployee(int employeeID, String firstName, String surname,
                               String phone, String email, String address, double salary)
            throws InvalidDataException {
        Employee emp = findEmployeeByID(employeeID);
        if (emp == null) {
            throw new IllegalArgumentException("Employee ID " + employeeID + " not found.");
        }
        checkNotNull(firstName, "First name");
        checkNotNull(surname,   "Surname");
        checkNotNull(phone,     "Phone");
        checkNotNull(address,   "Address");
        checkNotBlank(firstName, "First name");
        checkNotBlank(surname,   "Surname");
        checkNotBlank(phone,     "Phone");
        checkNotBlank(address,   "Address");
        if (salary < 0) {
            throw new InvalidDataException("Salary cannot be negative.");
        }
        emp.setFirstName(firstName);
        emp.setSurname(surname);
        emp.setPhone(phone);
        emp.setEmail(email);
        emp.setAddress(address);
        emp.setSalary(salary);
    }

    // -----------------------------------------------------------------------
    // Req-6 : Assign member to trainer
    // -----------------------------------------------------------------------

    /**
     * Name: assignMemberToTrainer
     * Purpose: Assigns a gym member to a personal trainer (Req-6).
     * If the member is already assigned to a different trainer, they are
     * removed from the old trainer's list automatically.
     *
     * @param memberID  ID of the member to assign
     * @param trainerID personalTrainerID of the target trainer
     * @throws IllegalArgumentException if member or trainer not found
     */
    public void assignMemberToTrainer(int memberID, int trainerID) {
        Member m = findMemberByID(memberID);
        if (m == null) {
            throw new IllegalArgumentException("Member ID " + memberID + " not found.");
        }
        PersonalTrainer pt = findTrainerByTrainerID(trainerID);
        if (pt == null) {
            throw new IllegalArgumentException("Trainer ID " + trainerID + " not found.");
        }
        PersonalTrainer current = m.getPersonalTrainer();
        if (current != null && current != pt) {
            current.removeMember(m);
        }
        m.setPersonalTrainer(pt);
        if (!pt.getPersonalTrainerMembers().contains(m)) {
            pt.addMember(m);
        }
    }

    // -----------------------------------------------------------------------
    // Req-7 : Remove member from trainer
    // -----------------------------------------------------------------------

    /**
     * Name: removeMemberFromTrainer
     * Purpose: Removes a member from a trainer's list (Req-7).
     * The member stays in the system, just unassigned.
     *
     * @param memberID  ID of the member to unassign
     * @param trainerID personalTrainerID of the trainer
     * @throws IllegalArgumentException if member/trainer not found or not assigned
     */
    public void removeMemberFromTrainer(int memberID, int trainerID) {
        Member m = findMemberByID(memberID);
        if (m == null) {
            throw new IllegalArgumentException("Member ID " + memberID + " not found.");
        }
        PersonalTrainer pt = findTrainerByTrainerID(trainerID);
        if (pt == null) {
            throw new IllegalArgumentException("Trainer ID " + trainerID + " not found.");
        }
        if (m.getPersonalTrainer() == null || m.getPersonalTrainer() != pt) {
            throw new IllegalArgumentException(
                    "Member " + memberID + " is not assigned to trainer " + trainerID + ".");
        }
        pt.removeMember(m);
        m.setPersonalTrainer(null);
    }

    // -----------------------------------------------------------------------
    // Req-8 : Delete employee
    // -----------------------------------------------------------------------

    /**
     * Name: deleteEmployee
     * Purpose: Removes an employee from the system (Req-8).
     * A PersonalTrainer can only be deleted if their member list is empty.
     *
     * @param employeeID ID of the employee to delete
     * @throws IllegalArgumentException if employee not found
     * @throws ActiveTrainerException   if trainer still has assigned members
     */
    public void deleteEmployee(int employeeID) throws ActiveTrainerException {
        Employee emp = findEmployeeByID(employeeID);
        if (emp == null) {
            throw new IllegalArgumentException("Employee ID " + employeeID + " not found.");
        }
        if (emp instanceof PersonalTrainer) {
            PersonalTrainer pt = (PersonalTrainer) emp;
            if (!pt.getPersonalTrainerMembers().isEmpty()) {
                throw new ActiveTrainerException(
                        "Cannot delete trainer " + pt.getFirstName() + " " + pt.getSurname()
                        + " - they still have " + pt.getMemberCount()
                        + " member(s). Remove them first.");
            }
        }
        employees.remove(emp);
    }

    // -----------------------------------------------------------------------
    // Req-9 : Delete member
    // -----------------------------------------------------------------------

    /**
     * Name: deleteMember
     * Purpose: Removes a member from the system (Req-9).
     * Also removes from trainer list automatically if assigned.
     *
     * @param memberID ID of the member to delete
     * @throws IllegalArgumentException if member not found
     */
    public void deleteMember(int memberID) {
        Member m = findMemberByID(memberID);
        if (m == null) {
            throw new IllegalArgumentException("Member ID " + memberID + " not found.");
        }
        if (m.getPersonalTrainer() != null) {
            m.getPersonalTrainer().removeMember(m);
            m.setPersonalTrainer(null);
        }
        members.remove(m);
    }

    // -----------------------------------------------------------------------
    // Req-10 : View members of a trainer
    // -----------------------------------------------------------------------

    /**
     * Name: getMembersOfTrainer
     * Purpose: Returns the ArrayList of members assigned to a trainer (Req-10).
     * Used by the GUI to display in a JTextArea with JScrollPane.
     *
     * @param trainerID personalTrainerID of the trainer
     * @return ArrayList of Member objects assigned to that trainer
     * @throws IllegalArgumentException if trainer not found
     */
    public ArrayList<Member> getMembersOfTrainer(int trainerID) {
        PersonalTrainer pt = findTrainerByTrainerID(trainerID);
        if (pt == null) {
            throw new IllegalArgumentException("Trainer ID " + trainerID + " not found.");
        }
        return pt.getPersonalTrainerMembers();
    }

    // -----------------------------------------------------------------------
    // Req-11 : Marketing report
    // -----------------------------------------------------------------------

    /**
     * Name: generateMarketingReport
     * Purpose: Writes a plain-text marketing report to marketingReport.txt (Req-11).
     * Groups members into Polytechnic Staff and Students sections.
     * Each section ends with the total count.
     *
     * @throws IOException if the report file cannot be written
     */
    public void generateMarketingReport() throws IOException {
        PrintStream outFile = null;
        try {
            outFile = new PrintStream(new File(MARKETING_FILE));
            outFile.println("=================================================");
            outFile.println("  BAHRAIN POLYTECHNIC GYM  -  MARKETING REPORT  ");
            outFile.println("=================================================");
            outFile.println();
            outFile.println("-------------------------------------------------");
            outFile.println("POLYTECHNIC STAFF MEMBERS");
            outFile.println("-------------------------------------------------");
            int staffCount = 0;
            for (int i = 0; i < members.size(); i++) {
                Member m = members.get(i);
                if (m instanceof PolytechnicStaff) {
                    PolytechnicStaff ps = (PolytechnicStaff) m;
                    outFile.println("Name       : " + ps.getFirstName() + " " + ps.getSurname());
                    outFile.println("Address    : " + ps.getAddress());
                    outFile.println("Phone      : " + ps.getPhone());
                    outFile.println("Position   : " + ps.getPosition());
                    outFile.println("Department : " + ps.getDepartment());
                    outFile.println();
                    staffCount++;
                }
            }
            outFile.println("Total Polytechnic Staff Members: " + staffCount);
            outFile.println();
            outFile.println("-------------------------------------------------");
            outFile.println("POLYTECHNIC STUDENT MEMBERS");
            outFile.println("-------------------------------------------------");
            int studentCount = 0;
            for (int i = 0; i < members.size(); i++) {
                Member m = members.get(i);
                if (m instanceof Students) {
                    Students s = (Students) m;
                    outFile.println("Name    : " + s.getFirstName() + " " + s.getSurname());
                    outFile.println("Address : " + s.getAddress());
                    outFile.println("Phone   : " + s.getPhone());
                    outFile.println("Course  : " + s.getCourse());
                    outFile.println("Team    : " + (s.getTeam().isEmpty() ? "N/A" : s.getTeam()));
                    outFile.println();
                    studentCount++;
                }
            }
            outFile.println("Total Polytechnic Student Members: " + studentCount);
            outFile.println();
            outFile.println("=================================================");
            outFile.println("End of Report");
        } finally {
            if (outFile != null) { outFile.close(); }
        }
    }

    // -----------------------------------------------------------------------
    // Search helpers
    // -----------------------------------------------------------------------

    /**
     * Name: findMemberByID
     * Purpose: Searches the members ArrayList for a member with the given ID.
     *
     * @param memberID the memberID to search for
     * @return the Member if found, null otherwise
     */
    public Member findMemberByID(int memberID) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getMemberID() == memberID) {
                return members.get(i);
            }
        }
        return null;
    }

    /**
     * Name: findEmployeeByID
     * Purpose: Searches the employees ArrayList for an employee with the given ID.
     *
     * @param employeeID the employeeID to search for
     * @return the Employee if found, null otherwise
     */
    public Employee findEmployeeByID(int employeeID) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeID() == employeeID) {
                return employees.get(i);
            }
        }
        return null;
    }

    /**
     * Name: findTrainerByTrainerID
     * Purpose: Searches the employees ArrayList for a PersonalTrainer
     * with the given personalTrainerID.
     *
     * @param trainerID the personalTrainerID to search for
     * @return the PersonalTrainer if found, null otherwise
     */
    public PersonalTrainer findTrainerByTrainerID(int trainerID) {
        for (int i = 0; i < employees.size(); i++) {
            Employee e = employees.get(i);
            if (e instanceof PersonalTrainer) {
                PersonalTrainer pt = (PersonalTrainer) e;
                if (pt.getPersonalTrainerID() == trainerID) {
                    return pt;
                }
            }
        }
        return null;
    }

    /**
     * Name: getAllPersonalTrainers
     * Purpose: Returns all PersonalTrainer objects in the system.
     * Used by GUI to populate JComboBox lists (Req-6, Req-10).
     *
     * @return ArrayList of all PersonalTrainer objects
     */
    public ArrayList<PersonalTrainer> getAllPersonalTrainers() {
        ArrayList<PersonalTrainer> result = new ArrayList<PersonalTrainer>();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i) instanceof PersonalTrainer) {
                result.add((PersonalTrainer) employees.get(i));
            }
        }
        return result;
    }

    // -----------------------------------------------------------------------
    // Getters
    // -----------------------------------------------------------------------

    /**
     * Name: getEmployees
     * Purpose: Returns the full employees ArrayList.
     * Used by GUI to populate tables and JComboBoxes (Req-16).
     *
     * @return ArrayList of all Employee objects
     */
    public ArrayList<Employee> getEmployees() { return employees; }

    /**
     * Name: getMembers
     * Purpose: Returns the full members ArrayList.
     * Used by GUI to populate tables and JComboBoxes (Req-16).
     *
     * @return ArrayList of all Member objects
     */
    public ArrayList<Member> getMembers() { return members; }

    // -----------------------------------------------------------------------
    // Req-15 : Exception handling helpers
    // -----------------------------------------------------------------------

    /**
     * Name: checkNotNull
     * Purpose: Throws NullPointerException if value is null.
     * Consistent with Java class library behaviour (Req-15).
     *
     * @param value     the String to check
     * @param fieldName field name used in the error message
     * @throws NullPointerException if value is null
     */
    private void checkNotNull(String value, String fieldName) {
        if (value == null) {
            throw new NullPointerException(fieldName + " cannot be null.");
        }
    }

    /**
     * Name: checkNotBlank
     * Purpose: Throws InvalidDataException if value is empty after trimming.
     *
     * @param value     the String to check
     * @param fieldName field name used in the error message
     * @throws InvalidDataException if value is blank
     */
    private void checkNotBlank(String value, String fieldName) throws InvalidDataException {
        if (value.trim().isEmpty()) {
            throw new InvalidDataException(fieldName + " cannot be blank.");
        }
    }
}