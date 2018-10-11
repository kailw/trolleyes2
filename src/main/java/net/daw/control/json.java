/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.control;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import net.daw.conexiones.BoneCp;
import net.daw.conexiones.C3p0;
import net.daw.conexiones.ConnectionInterface;
import net.daw.conexiones.Hikari;

/**
 *
 * @author kevin
 */
public class json extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        HttpSession oSession = request.getSession();
        String strOp = request.getParameter("op");
        String strJson = "";
        if (strOp != null) {
            if (strOp.equalsIgnoreCase("") || !strOp.equalsIgnoreCase("login") || !strOp.equalsIgnoreCase("logout") || !strOp.equalsIgnoreCase("secret")
                    || !strOp.equalsIgnoreCase("bonecp")|| !strOp.equalsIgnoreCase("hikari") || !strOp.equalsIgnoreCase("c3p0")) {
                response.setStatus(500);
                strJson = strJson(500, "No debe estar vacía.");
            } else  {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (Exception ex) {
                    strJson = strJson(500, "Driver not found.");
                }

                Connection connection;
                ConnectionInterface connectionInterface = null;

                if (strOp.equalsIgnoreCase("bonecp")) {
                    try {
                        connectionInterface = new BoneCp();
                        connection = connectionInterface.newConnection();
                        strJson = strJson(200, "Conexion Realizada a BonceCP.");
                    } catch (Exception e) {
                        strJson = strJson(500, "Conexion Fallida a BonceCP.");
                    }
                }

                if (strOp.equalsIgnoreCase("hikari")) {
                    try {
                        connectionInterface = new Hikari();
                        connection = connectionInterface.newConnection();
                        strJson = strJson(200, "Conexion Realizada a Hikari.");
                    } catch (Exception e) {
                        strJson = strJson(500, "Conexion Fallida a Hikari.");
                    }
                }

                if (strOp.equalsIgnoreCase("c3p0")) {
                    try {
                        connectionInterface = new C3p0();
                        connection = connectionInterface.newConnection();
                        strJson = strJson(200, "Conexion Realizada a c3p0.");
                    } catch (Exception e) {
                        strJson = strJson(500, "Conexion Fallida a c3p0.");
                    }
                }

                if (strOp.equalsIgnoreCase("login")) {
                    String strUser = request.getParameter("user");
                    String strPass = request.getParameter("passw");
                    if (strUser != null && strPass != null) {
                        if (strUser.equals("kevin") && strPass.equals("kevin")) {
                            oSession.setAttribute("sesionvar", strUser);
                            response.setStatus(200);
                            strJson = strJson(200, "You are logged in.");
                        } else {
                            response.setStatus(401);
                            strJson = strJson(401, "Authentication error");
                        }
                    } else {
                        strJson = strJson(401, "User or pass not specified.");
                    }
                }
                if (strOp.equalsIgnoreCase("logout")) {
                    oSession.invalidate();
                    response.setStatus(200);
                    strJson = strJson(200, "Session is closed.");
                }
                if (strOp.equalsIgnoreCase("check")) {
                    String strUserName = (String) oSession.getAttribute("sesionvar");
                    if (strUserName == null) {
                        response.setStatus(401);
                        strJson = strJson(401, "You are NOT logged in.");
                    } else {
                        response.setStatus(200);
                        strJson = strJson(200, "You are logged in !!.");
                    }
                }

                if (strOp.equalsIgnoreCase("secret")) {
                    String strUserName = (String) oSession.getAttribute("sesionvar");
                    if (strUserName == null) {
                        response.setStatus(401);
                        strJson = strJson(401, "Error. You are not access.");
                    } else {
                        response.setStatus(200);
                        strJson = strJson(200, "El nº secreto es 788945");
                    }
                }

            }

        } else {
            response.setStatus(500);
            strJson = strJson(500, "No ha solicitado ninguna operacion.");
        }

        response.getWriter().append(strJson);

    }

    public String strJson(int status, String msg) {
        String strJsonM = "{\"status\":" + status + ",\"msg\":\"" + msg + "\"}";
        return strJsonM;
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
