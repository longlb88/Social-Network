/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

import javax.servlet.ServletContextEvent;
import org.apache.log4j.Logger;

/**
 * Web application lifecycle listener.
 *
 * @author Long Le
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {

    private Logger log = Logger.getLogger(ServletContextListener.class.getName());
    private final String AUTH_FUNCTIONS_FILE = "/WEB-INF/prohibitGuest.txt";
    private final String NEW_USER_FILE = "/WEB-INF/prohibitNewUser.txt";

    private List<String> loadFunctionsFromFile(String filePath) {
	FileReader fr = null;
	BufferedReader br = null;
	List<String> functions = null;

	try {
	    fr = new FileReader(filePath);
	    br = new BufferedReader(fr);

	    while (br.ready()) {
		String function = br.readLine();

		if (functions == null) {
		    functions = new ArrayList<>();
		}
		functions.add(function);
	    }
	} catch (FileNotFoundException ex) {
	    log.error("FileNotFoundException: " + ex.getMessage());
	} catch (IOException ex) {
	    log.error("IOException: " + ex.getMessage());
	} finally {
	    try {
		if (br != null) {
		    br.close();
		}
		if (fr != null) {
		    fr.close();
		}
	    } catch (IOException ex) {
		log.error("IOException: " + ex.getMessage());
	    }
	}
	return functions;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
	ServletContext context = sce.getServletContext();
	String realPath = context.getRealPath("/");
	
	List<String> authFunctions = loadFunctionsFromFile(realPath + AUTH_FUNCTIONS_FILE);
	if (authFunctions != null){
	    context.setAttribute("PROHIBIT_GUEST", authFunctions);
	}

	List<String> newUserFunctions = loadFunctionsFromFile(realPath + NEW_USER_FILE);
	if (authFunctions != null){
	    context.setAttribute("PROHIBIT_NEW_USER", newUserFunctions);
	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
	log.info("Contex destroyed!");
    }
}
