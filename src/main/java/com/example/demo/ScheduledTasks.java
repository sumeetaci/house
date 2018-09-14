package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.util.MiscUtils;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String sp = File.separator;
    public void scheduleTaskWithFixedRate() {}

    public void scheduleTaskWithFixedDelay() {}

    public void scheduleTaskWithInitialDelay() {}
    
    // fire every day at 12
   @Scheduled(cron = "0 25 13 * * ?")
   public void scheduleTaskWithNBayCronExpression() {
    	callPython("http://sfbay.craigslist.org/search/nby/apa");
    }
    
    @Scheduled(cron = "0 52 15 * * ?")
    public void scheduleTaskWithSBayCronExpression() {
    	callPython("http://sfbay.craigslist.org/search/sby/apa");
    }
    
     @Scheduled(cron = "0 33 13 * * ?")
   // @Scheduled(fixedDelay = 1000, initialDelay = 20000)
    public void scheduleTaskWithCronExpression() {
    	callPython("http://sfbay.craigslist.org/search/eby/apa");
    }
    
    
    public  String runCommandForOutput(List<String> params) {
        ProcessBuilder pb = new ProcessBuilder(params);
        Process p;
        String result = "";
        try {
            p = pb.start();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            InputStream error = p.getErrorStream();
            InputStreamReader isrerror = new InputStreamReader(error);
            BufferedReader bre = new BufferedReader(isrerror);
            String linee;
            if(bre != null && bre.readLine() != null)
           while ((linee = bre.readLine()) != null) {
                    System.out.println(linee);
                } 
            /*
            StringJoiner sj = new StringJoiner(System.getProperty("line.separator"));
            reader.lines().iterator().forEachRemaining(sj::add);
            result = sj.toString(); 
              */
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.concat("\n").concat("*************ENDED**************");
        return result;
    }
 
 
    private void callPython(String url) {
    	logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        logger.info("Calling script now");
        MiscUtils util = new MiscUtils();
        String scriptFile = "/static/craigslist_Final_main.py";
       // Resource resource = new ClassPathResource(scriptFile);
    	File file;
		try {
			// file = resource.getFile();
		String[] urlsplit = url.split("/apa");
		String url_differ = urlsplit[0].substring(urlsplit[0].lastIndexOf("/")+1);//, endIndex);
		//url_differ
		String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		fileName = url_differ.concat("_").concat(fileName).concat(".txt");
		String xlfileName = url_differ.concat("_").concat(fileName).concat(".xlsx"); 
		logger.info("Sending log to "+fileName);
		
		// String[] cmds = new String[]{"cmd.exe", "/c","python "+file.getCanonicalPath()+" "+ url+" > "+fileName};//"http://sfbay.craigslist.org/search/nby/apa"};
		String[] cmds = new String[]{"cmd.exe", "/c","python "+scriptFile+" "+ url+" "+getFilePath(xlfileName)+" > "+getFilePath(fileName)};
		// util.runScript(cmds);
        String result = runCommandForOutput(Arrays.asList(cmds)); 
        logger.info("Called script just now: "+ String.join(" ", cmds));
		} catch (Exception e) {
        //} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}