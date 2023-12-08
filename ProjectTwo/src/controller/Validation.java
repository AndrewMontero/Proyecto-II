/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author abarc
 */
public class Validation {

    public boolean validateIdNumber(String IDNumber) {
        return IDNumber.matches("[1-9][0-9]{8}");
    }

    public boolean validateLettersSpaces(String valor) {
        Pattern pat = Pattern.compile("^[a-zA-Z\\s]+$?");
        Matcher mat = pat.matcher(valor);
        return mat.matches();
    }

    public boolean validateNumber(String valor) {
        Pattern pat = Pattern.compile("[0-9]*");
        Matcher mat = pat.matcher(valor);
        return mat.matches();
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        // El patrón acepta solo dígitos y el formato "xxxx-xxxx"
        Pattern pat = Pattern.compile("^\\d{4}-\\d{4}$");
        Matcher mat = pat.matcher(phoneNumber);
        return mat.matches();
    }

    public boolean validateEmail(String correo) {
        // Patrón que permite letras minúsculas, dígitos numéricos, guiones bajos, @ y al menos un punto.
        Pattern pat = Pattern.compile("^[a-z0-9_.]+@[a-z0-9_.]+$");
        Matcher mat = pat.matcher(correo);
        return mat.matches();
    }

    public boolean validateNumeric(String valor) {
    Pattern pat = Pattern.compile("^[0-9.-]*$");
    Matcher mat = pat.matcher(valor);
    return mat.matches();
}
    
    public boolean validateAlphanumeric(String valor) {
        Pattern pat = Pattern.compile("^[a-zA-Z0-9]*$");
        Matcher mat = pat.matcher(valor);
        return mat.matches();
    }
}
