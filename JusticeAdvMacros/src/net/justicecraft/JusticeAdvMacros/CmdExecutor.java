package net.justicecraft.JusticeAdvMacros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdExecutor implements CommandExecutor{
	private Core plugin;

	public static List macros = new ArrayList<String>();
	
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
						if(macros.contains(args[1].toLowerCase())){
							p.sendMessage(ChatColor.RED + "Macro already exists with that name.");
						} else {
							File macro = new File (plugin.folderBase, args[1].toLowerCase());
							macros.add(args[1].toLowerCase());
							p.sendMessage(ChatColor.GREEN + "Macro created!");
						}
					}
					// set
					if(args[0].equalsIgnoreCase("set")){
						if(args.length >= 3){
							
						} else {
							
						}
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
						if(macros.contains(args[1].toLowerCase())){
							File macro = new File (plugin.folderBase, args[1].toLowerCase());
							if(macro.exists()){
								// create buffered reader
								// output as reading
								// check for empty file?
							} else {
								macros.remove(args[1].toLowerCase());
								p.sendMessage(ChatColor.RED + "Macro not found!");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Macro not found!");
						}
					}
					// delete
					if(args[0].equalsIgnoreCase("delete")){
						if(macros.contains(args[1].toLowerCase())){
							File macro = new File (plugin.folderBase, args[1].toLowerCase());
							if(macro.exists()){
								macro.delete();
								macros.remove(args[1].toLowerCase());
								p.sendMessage(ChatColor.GREEN + "Macro deleted!");
							} else {
								macros.remove(args[1].toLowerCase());
								p.sendMessage(ChatColor.RED + "Unable to delete, macro not found!");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Unable to delete, macro not found!");
						}
					}
					// perform
					// keep macros in memory to prevent more i/o
				} else if(args.length == 1){
					// list
					if(args[0].equalsIgnoreCase("list")){
						if(macros.size() >= 0){
							String out = "";
							int counter = 1;
							for (Object m : macros){
								if(macros.size() < counter){
									out += (String)m + ", ";
								} else {
									out += (String)m + ".";
								}
								counter++;
							}
							p.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "Current Macros:");
							p.sendMessage(ChatColor.WHITE + out);
						} else {
							p.sendMessage(ChatColor.RED + "No macros found!");
						}
					}
				}
			}
		}		
		return false;
	}
}
