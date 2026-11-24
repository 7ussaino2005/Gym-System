/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

/**
 * Name: Employee
 * Purpose: Represents a regular gym employee.
 * Extends GymPerson and adds employee-specific fields: employeeID,
 * isPersonalTrainer flag, and salary.
 *
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public class Employee extends GymPerson {

    private static final long serialVersionUID = 1L;

    private int     employeeID;
    private boolean isPersonalTrainer;
    private double  salary;

    /**
     * Name: Employee Constructor
     * Purpose: Creates a new Employee object with all required fields.
     *
     * @param employeeID        the unique employee ID
     * @param isPersonalTrainer true if this employee is also a personal trainer
     * @param salary            the employee salary
     * @param firstName         first name
     * @param surname           surname
     * @param phone             phone number
     * @param email             email address
     * @param address           home address
     */
    public Employee(int employeeID, boolean isPersonalTrainer, double salary,
                    String firstName, String surname, String phone,
                    String email, String address) {
        super(firstName, surname, phone, email, address);
        this.employeeID        = employeeID;
        this.isPersonalTrainer = isPersonalTrainer;
        this.salary            = salary;
    }

    /**
     * Name: getEmployeeID
     * Purpose: Returns the unique employee ID.
     *
     * @return employeeID
     */
    public int getEmployeeID() { return employeeID; }

    /**
     * Name: setEmployeeID
     * Purpose: Sets the employee ID.
     *
     * @param employeeID the employee ID to set
     */
    public void setEmployeeID(int employeeID) { this.employeeID = employeeID; }

    /**
     * Name: isPersonalTrainer
     * Purpose: Returns true if this employee is a personal trainer.
     *
     * @return isPersonalTrainer
     */
    public boolean isPersonalTrainer() { return isPersonalTrainer; }

    /**
     * Name: setIsPersonalTrainer
     * Purpose: Sets whether this employee is a personal trainer.
     *
     * @param isPersonalTrainer true if personal trainer
     */
    public void setIsPersonalTrainer(boolean isPersonalTrainer) {
        this.isPersonalTrainer = isPersonalTrainer;
    }

    /**
     * Name: getSalary
     * Purpose: Returns the salary of the employee.
     *
     * @return salary
     */
    public double getSalary() { return salary; }

    /**
     * Name: setSalary
     * Purpose: Sets the salary of the employee.
     *
     * @param salary the salary to set
     */
    public void setSalary(double salary) { this.salary = salary; }

    /**
     * Name: toString
     * Purpose: Returns a formatted String with employee details.
     *
     * @return formatted String
     */
    @Override
    public String toString() {
        return super.toString()
             + " | Emp-ID: " + employeeID
             + " | Salary: " + salary
             + " | PT: " + isPersonalTrainer;
    }
}