package org.com.alasvaladas.java;

import java.io.IOException;
import javax.websocket.DeploymentException;

/**
 *
 **/
public class App 
{
    public static void main( String[] args )
    {
        java.util.Map<String, Object> user_properties = new java.util.HashMap();
        user_properties.put("Author", "AlexandreGenesi_ArnaudLasvaladas");
        
        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 1963, "/alasvaladas", user_properties /* or 'null' */, org.com.alasvaladas.websocket.WebSockets.My_ServerEndpoint.class);
        System.out.println("TEST : " + server.toString());
        try {
            server.start();
            // The Web page is launched from Java:
            java.awt.Desktop.getDesktop().browse(java.nio.file.FileSystems.getDefault().getPath("src" + java.io.File.separatorChar + "browser" + java.io.File.separatorChar + "index.html").toUri());

            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.println("Please press a key to stop the server...");
            reader.readLine();
        } catch (IOException e) {
        } catch (DeploymentException e) {
        } finally {
            server.stop();
        }
    }
}
