/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.com.utlis;

import poly.com.entity.Staff;

/**
 *
 * @author Duy Cuong
 */
public class Auth {
    public static Staff user = null;
    public static void clear(){
        Auth.user = null;
    }
    
    public static boolean isLogin(){
        return Auth.user != null;
    }
    
    
    public static boolean isManager(){
        return Auth.isLogin() && user.isRole();
    }
}
