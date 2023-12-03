/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import javax.swing.JOptionPane;
import model.User;
import model.UserDAO;
import view.frmAdmin;
import view.frmUser;

/**
 *
 * @author Bravo
 */
public class Login {

    User authenticatedUser = null;
    UserDAO dao = new UserDAO();
    CtrlUser userCtrl = new CtrlUser();

    public void login(String email, String password) {
        //read the User list
        List<User> userList = dao.read();
        //determines whether the user has been successfully authenticated or not.
        boolean isAuthenticated = false;
        for (User user : userList) {
            //validates if the email and password are the same as the entered data registered in the database
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                authenticatedUser = user;
                isAuthenticated = true;
                break;
            }
        }
        //Displays the frame if the user has been authenticated.
        if (isAuthenticated) {
            openFrame(authenticatedUser);
        } else {
            JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Method to displays the frame 
    private void openFrame(User authenticatedUser) {
        //Gets the authenticated user's role identifier
        int rolId = authenticatedUser.getRol_id();
        //if the role identifier is 1, the Admin frame is displayed.
        if (rolId == 1) {
            openAdminFrame(authenticatedUser);
            //if the role identifier is 2, the user frame is displayed.
        } else if (rolId == 2) {
            openUserFrame(authenticatedUser);
        }
    }
    
    private void openAdminFrame(User authenticatedUser) {
        frmAdmin login = new frmAdmin(authenticatedUser);
        login.setVisible(true);
    }
    
    private void openUserFrame(User authenticatedUser){
        int rolId = authenticatedUser.getRol_id();
        userCtrl.setRolId(rolId);
        frmUser login = new frmUser(authenticatedUser);
        login.setVisible(true);
    }
}
