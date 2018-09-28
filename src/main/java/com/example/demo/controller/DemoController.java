package com.example.demo.controller;


	import java.io.*;
import java.net.URLDecoder;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.ml.HelloTensorFlow;
import com.example.demo.model.Search;
import com.example.demo.util.MiscUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
	
	@Controller
	public class DemoController {
		Process mProcess;
	    Logger log = LoggerFactory.getLogger(this.getClass());
	    private static final String sp = File.separator;
	    
	    @Autowired
	    private Environment env;
	    
	    private static String OS = System.getProperty("os.name").toLowerCase();
	    private final static String PYTHON_PATH = "PYTHONPATH";
	    private static final String py_script = "C:\\Users\\sumeet_badwal\\eclipse-workspace\\demo\\src\\main\\java\\com\\example\\demo\\controller\\estimation.py"; // "/estimation.py"; 
	   
	    @Value("${PYTHONPATH:python}")
	    private String pythonEnvPath;
	    @RequestMapping(value="/", method=RequestMethod.GET)
	    public String wecomeForm(Model model) {
	       // model.addAttribute("search", new Search());
	        return "welcome";
	    }
	 
	    
	    
	    @RequestMapping(value="/form", method=RequestMethod.GET)
	    public String searchForm(Model model) {
	        model.addAttribute("search", new Search());
	        return "form";
	    }
	 
	    @RequestMapping(value="/form", method=RequestMethod.POST)
	    public String searchSubmit(@ModelAttribute Search search, Model model) {
	        log.info("Calling script now and OS is : "+OS);
	        MiscUtils util = new MiscUtils();
	        ApplicationHome home = new ApplicationHome(this.getClass());
	        File jarFile = home.getSource();
	        File jarDir = home.getDir();
	        log.info("Calling script now and jarDir is: "+jarDir.getAbsolutePath());
	        String scriptFile = jarDir.getAbsolutePath().concat("/estimation.py"); // getFilePath("estimation.py"); // "/static/estimation.py";
	        String scriptFilePath = env.getProperty("userBucket.path");
	        String pythonPath = env.getProperty("python.path");
	        //String pythonEnvPath = System.getProperty(PYTHON_PATH);
	        scriptFile = "/static/estimation.py";
	        log.info("Script file is :"+scriptFilePath);
	        log.info("pythonEnvPath is :"+pythonEnvPath);
	       // Resource resource = new ClassPathResource(scriptFile);
        	File file;
			try {
				//file = resource.getFile();
				System.getenv();
	        String[] cmds1 = new String[]{"cmd.exe", "/c","python "+scriptFile,"arg1","arg2"};
	        String[] cmds2 = new String[]{pythonPath.concat("/python ")+scriptFilePath,search.getZip().toString(),search.getDescription()};
	        List<String> cmdList = new ArrayList<String>();
	        if(OS.startsWith("windows")) {
	        	cmdList.add("cmd.exe");
	        	cmdList.add("/c");
	        	//cmdList.add("python ");
	        	cmdList.add(pythonPath);
	        }else {
	        	//cmdList.add("bash");
	        	//cmdList.add("-c");
	        	//cmdList.add("xterm");
	        	//cmdList.add("-e");
	        	// cmdList.add("/Users/sumeet_badwal/anaconda/bin/python ");
	        	//cmdList.add("python ");
	        	cmdList.add(pythonEnvPath.concat(pythonPath));
	        }
	        //cmdList.add("python ");
        	cmdList.add(scriptFilePath);
        	cmdList.add(search.getZip().toString());
        	cmdList.add(search.getDescription());
        	String[] cmds = cmdList.toArray(new String[cmdList.size()]);
	        log.info("Calling script just now: "+ String.join(" ", cmds));
	        util.runScript(cmds);
	        log.info("Called script just now");
	        runML(); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        model.addAttribute("search", search);
	        String info = search.toString();
	        log.info(info);
	        
	        return "result";
	    }
	    
	   // @RequestMapping(value="/form", method=RequestMethod.POST)
	    public String searchSubmitOrig(@ModelAttribute Search search, Model model) {
	        log.info("Calling script now");
	        MiscUtils util = new MiscUtils();
	        String scriptFile = "/static/estimation.py";
	        Resource resource = new ClassPathResource(scriptFile);
        	File file;
			try {
				file = resource.getFile();
			
	        String[] cmds = new String[]{"cmd.exe", "/c","python "+file.getCanonicalPath(),"arg1","arg2"};
	        util.runScript(cmds);
	        log.info("Called script just now");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        model.addAttribute("search", search);
	        String info = search.toString();
	        log.info(info);
	         
	        return "result";
	    }
	    
	    public static String runCommandForOutput(List<String> params) {
	        ProcessBuilder pb = new ProcessBuilder(params);
	        Process p;
	        String result = "";
	        try {
	            p = pb.start();
	            final BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

	            StringJoiner sj = new StringJoiner(System.getProperty("line.separator"));
	            reader.lines().iterator().forEachRemaining(sj::add);
	            result = sj.toString();

	            p.waitFor();
	            p.destroy();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        result.concat("\n").concat("*************ENDED**************");
	        return result;
	    }
	    
	    private void runML() throws UnsupportedEncodingException { 
	    	HelloTensorFlow htf = new HelloTensorFlow();
	    			htf.runVersionTest();
	    }
	    private String getFilePath(String filename) {
	        String[][] input = new String[7][4];
	         
	        // store data into this array
	         
	        String jarPath = "";
	        System.out.println("Writing data...");
	        try {
	            jarPath = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
	            System.out.println(jarPath); 
	                        //it's the universal approach, working not only in Eclipse as project, but also as JAR.
	                        //returning "/d:/somefolder/My.jar". Note it's a URL with "/", not with Windows File seperator "\\". 
	        } catch (UnsupportedEncodingException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	                       // construct a File within the same folder of this jar, or of this class. 
	        String completePath = jarPath.substring(0, jarPath.lastIndexOf("/")) 
	                + sp + filename;

	                       //Note: here we have an URL, so it contains only "/". To extract the parent 
	                       //folder path we use "lastIndexOf("/")".
	                       //but, for constructing a File path, we use File.seperator of each platform. 
	        File f = new File(completePath);
	        try {
	            if (!f.exists() && !f.createNewFile()) {
	                System.out.println("File doesn't exist, and creating file with path: " + completePath + " failed. ");
	                 
	            } else {
	                System.out.println("Input data exists, or file with path " + completePath + " created successfully. ");
	                System.out.println("Absolute Path: " + f.getAbsolutePath());
	                System.out.println("Path: " + f.getPath());
	               /* ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
	                out.writeObject(input);
	                out.close(); */
	                
	            }
	             return f.getPath();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return f.getPath(); 
	         
	    }
	    
	    private void runPython() {
	    	//ScriptEngine python = new ScriptEngineManager().getEngineByName("python");
	    	//python.eval(stringHere); //script engine runs code
	    	PythonInterpreter python = new PythonInterpreter();
	    	

	    }
	    
	}