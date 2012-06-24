package net.justicecraft.JusticeAdvMacros;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdExecutor implements CommandExecutor{
	private Core plugin;
	
	private boolean firstLoad = true;
	
	public CmdExecutor(Core instance){
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// If sender isn't player
		if (sender instanceof Player) {
			Player p = (Player)sender;
			if(p.isOp()){
				if(args.length > 1){
					// create
					if(args[0].equalsIgnoreCase("create")){
						
					}
					// set
					if(args[0].equalsIgnoreCase("set")){
						
					}
					// undo
					if(args[0].equalsIgnoreCase("undo")){
						// Add in checks for if there is a macro project open
					}
					// save
					if(args[0].equalsIgnoreCase("save")){
						
					}
					// list
					if(args[0].equalsIgnoreCase("list")){
						
					}
					// delete
					if(args[0].equalsIgnoreCase("delete")){
						
					}
					// perform
					// keep macros in memory to prevent more i/o
				} else if(args.length == 1){
					// list
					if(args[0].equalsIgnoreCase("list")){
						
					}
				}
			}
		}		
		return false;
	}
}
