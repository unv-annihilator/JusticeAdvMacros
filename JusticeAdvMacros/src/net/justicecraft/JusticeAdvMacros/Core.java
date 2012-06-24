package net.justicecraft.JusticeAdvMacros;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin{
	// Create Logging Stuff
	public final Logger log = Logger.getLogger("Minecraft");
	public static String logPrefix = "[JCAdvMacro] ";
	
	// Command handling
	private CmdExecutor myExecutor;
	
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
				for(File file : folderBase.listFiles()){
					myExecutor.macros.add(file.getName());
				}
			}
		} else {
			folderBase.mkdir();
		}
		
		// Debug
		if(myExecutor.macros.size() > 0){
			for(Object m : myExecutor.macros){
				log.info("Macro: "+ (String)m);
			}
		}
	}
}
