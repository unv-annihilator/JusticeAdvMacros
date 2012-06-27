package net.justicecraft.JusticeAdvMacros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin{
	// Create Logging Stuff
	public final Logger log = Logger.getLogger("Minecraft");
	public static String logPrefix = "[JCAdvMacro] ";
	
	// Command handling
	private CmdExecutor myExecutor;
	
	public static List macros = new ArrayList<Macro>();
	
	// File stuff
	public static final File folderBase= new File( "plugins" + File.separator + "JusticeAdvMacros" );
	
	// On Disable
	public void onDisable(){
		log.info(logPrefix+" has been disabled.");
	}
	
	// On Enable
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName()+ " version " + pdfFile.getVersion() + " is enabled.");
		
		myExecutor = new CmdExecutor(this);
		getCommand("macro").setExecutor(myExecutor);
		
		// Load up list of macros
		if(folderBase.exists()){
			if(folderBase.isDirectory()){
				log.info(logPrefix + "Loading macros.");
				for(File file : folderBase.listFiles()){
					myExecutor.macroNames.add(file.getName());
					macros.add(new Macro(file.getName()));
					// check for vars or lines
					try {
						BufferedReader in = new BufferedReader(new FileReader(file));
						String line;
						log.info("File: "+file.getName());
						while((line = in.readLine()) != null){
							log.info(line);
							if(line.startsWith("var: ")){
								String[] temp = line.split(":");
								if(temp.length > 1){
									getMacro(file.getName()).setVars(Integer.parseInt(temp[1].trim()));
								}
							} else if(line.startsWith("/")){
								getMacro(file.getName()).addLine(line);
							}
						}
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			log.info(logPrefix + "Creating folder.");
			folderBase.mkdir();
		}
	}
	
	public Macro getMacro(String name){
		Macro result;
		if(myExecutor.macroNames.contains(name)){
			result = (Macro) macros.get(myExecutor.macroNames.indexOf(name));
			return result;
		} else {
			return null;
		}
	}
}
