/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymsystem;

/**
 * Name: GymSystemMain
 * Purpose: Entry point for the Bahrain Polytechnic Gymnasium Administration System
 * @author Hussain Alaskari
 * @author Yassen Abbas
 * @author Omar Ajaj
 * @author Haider Ali
 * @version 1.0
 */
public class GymSystemMain {

    /**
     * Name: main
     * Purpose: Application entry point. Loads GymSystem and launches DashboardFrame.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            GymSystem gym = GymSystem.loadSystem();
            new Dashboard_Frame(gym).setVisible(true);
        });
    }
}
