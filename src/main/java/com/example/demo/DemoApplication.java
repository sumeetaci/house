package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
// Uncomment below if running python file from scheduler
//@EnableScheduling
public class DemoApplication {
	Logger log = LoggerFactory.getLogger(this.getClass());
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	 @PostConstruct
	    private void init(){
	        log.info("creating an executable jar/war with spring boot with parent pom");
	        // Uncomment below if running python file from here
	        // getPythonReady();
	        copyPythonEstimateFile();
	        System.out.println("creating an executable jar/war with spring boot with parent pom"); 
	        
	    }

	 private void copyPythonEstimateFile() {
		    ApplicationHome home = new ApplicationHome(this.getClass());
	        File jarFile = home.getSource();
	        File jarDir = home.getDir();
	        String destname = jarDir.getAbsolutePath().concat("/estimation.py");
	        File dest =  new File(destname);
	        log.info("jar Dir is:"+jarDir.getAbsolutePath());
	        log.info("jar Dir parent dir is:"+jarDir.getParent());
	        String scriptFile = "/static/estimation.py";
	        URL url = this.getClass().getClassLoader().getResource("application.properties");
	        log.info(url.getPath());

	        File file = new File(url.getFile());
	        log.info("filename is "+file.getAbsolutePath());
	        Map<String, String> env = System.getenv();
	        for (String envName : env.keySet()) {
	            System.out.format("%s=%s%n", envName, env.get(envName));
	        }
	        /* Resource resource1 = new ClassPathResource(scriptFile);
	        InputStream resource = getClass().getResourceAsStream(scriptFile);
	        try { 
	            FileUtils.copyFileToDirectory(new File(resource.getFilename()), jarDir);// dest);
	            log.info("File "+resource.getFilename()+" is copied now here:"+jarDir.getAbsolutePath());
	        } catch (IOException e) {
	            e.printStackTrace();
	        } */
	 }
	 
	 private void getPythonReadyRun() {
		 Resource resource = new ClassPathResource("/static/requirement.txt");
     	File file;
		try {
			file = resource.getFile();
		
			System.out.println("ClassPath Resource found: "+file.getAbsolutePath());
     	    // Install all the needed python packages
     	    String[] cmds = new String[]{"cmd.exe", "/c","pip  install -r "+file.getCanonicalPath()};
	        String result = runCommandForOutput(Arrays.asList(cmds)); 
            System.out.println("result is "+result);
		 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 private void getPythonReady() {
		 Resource resource = new ClassPathResource("/static/requirement.txt");
     	File file;
		try {
			// file = resource.getFile();
		   String filename = "/static/requirement.txt";
			System.out.println("ClassPath Resource found:  /static/requirement.txt");
     	    // Install all the needed python packages
     	    String[] cmds = new String[]{"cmd.exe", "/c","pip  install -r "+"/static/requirement.txt"};
	        String result = runCommandForOutput(Arrays.asList(cmds)); 
            System.out.println("result is "+result);
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	    
}
