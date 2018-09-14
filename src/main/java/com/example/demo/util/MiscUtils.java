package com.example.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class MiscUtils {

	 
	 public  String runCommandForOutput(List<String> params) {
	        ProcessBuilder pb = new ProcessBuilder(params);
	        Process p;
	        String result = "";
	        try {
	            p = pb.start();
	            // pb.command(params);
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
	 
	 
	 public  String runCommandForOutputTry(List<String> params) {
	        //ProcessBuilder pb = new ProcessBuilder(params);
	        String command = String.join("  ", params);
	        
	        String result = "";
	        try {
	        	Process p = Runtime.getRuntime().exec(command);
	        	 p.waitFor();
	           final BufferedReader bri  = new BufferedReader(new InputStreamReader(p.getInputStream()));
	           final BufferedReader bre  = new BufferedReader(new InputStreamReader(p.getErrorStream()));
               String line;
               while((line = bri.readLine()) != null) {
            	   System.out.println(line);
               }
               bri.close();
               while((line = bre.readLine()) != null) {
            	   System.out.println(line);
               }
               bre.close();

	            p.waitFor();
	            System.out.println(line);
	            p.destroy();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        result.concat("\n").concat("*************ENDED**************");
	        return result;
	    }
	 
	 
	 
	    public  void runScript( String[] cmds ){
		        try{
		        	//String result = runCommandForOutput(Arrays.asList(cmds)); 
		        	String result = runCommandForOutputTry(Arrays.asList(cmds)); 
		            
		        	System.out.println("result is "+result);
		            
		        }catch(Exception e) {
		           System.out.println("Exception Raised" + e.toString());
		        }
		        
		 }
}
