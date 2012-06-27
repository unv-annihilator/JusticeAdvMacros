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

	public static List macroNames = new ArrayList<String>();
	public static List player = new ArrayList<String>();
	public static List openMacro = new ArrayList<String>();
	
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
						if(macroNames.contains(args[1].toLowerCase())){
							p.sendMessage(ChatColor.RED + "Macro already exists with that name.");
						} else {
							File macro = new File (plugin.folderBase, args[1].toLowerCase());
							macroNames.add(args[1].toLowerCase());
							plugin.macros.add(new Macro(args[1].toLowerCase()));
							p.sendMessage(ChatColor.GREEN + "Macro created!");
							if(player.contains(p.getName())){
								openMacro.remove(player.indexOf(p.getName()));
								player.remove(p.getName());
							}
							player.add(p.getName());
							openMacro.add(args[1].toLowerCase());
						}
						return true;
					}
					// set
					if(args[0].equalsIgnoreCase("set")){
						if(args.length >= 3){
							if(player.contains(p.getName())){
								if(args[1].equalsIgnoreCase("var") || args[1].equalsIgnoreCase("vars")){
									int vars = Integer.parseInt(args[2]);
									if(vars >= 0){
										plugin.getMacro((String)openMacro.get(player.indexOf(sender.getName()))).setVars(vars);
									} else {
										p.sendMessage(ChatColor.RED + "Number of variables must be positive.");
									}
								} else if (args[1].equalsIgnoreCase("line")) {
									String line = "";
									for(int i = 2; i < args.length; i++){
										line += args[i];
									}
									line.trim();
									plugin.getMacro((String)openMacro.get(player.indexOf(sender.getName()))).addLine(line);
								}
							} else {
								p.sendMessage(ChatColor.RED + "No macro open, use /macro edit <name> to edit existing macro.");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Improper format! /macro set <line/var> <command/# of vars>");
							return true;
						}
					}
					// undo
					if(args[0].equalsIgnoreCase("undo")){
						// Add in checks for if there is a macro project open
					}
					// edit
					if(args[0].equalsIgnoreCase("edit")){
						
					}
					// list
					if(args[0].equalsIgnoreCase("list")){
						if(macroNames.contains(args[1].toLowerCase())){
							File macro = new File (plugin.folderBase, args[1].toLowerCase());
							if(macro.exists()){
								Macro temp = (Macro)plugin.macros.get(macroNames.indexOf(args[1].toLowerCase()));
								p.sendMessage(ChatColor.GREEN + "Macro: "+temp.getName());
								p.sendMessage(ChatColor.DARK_PURPLE + "Number of variables: "+temp.getVars());
								if(temp.getLines().size() > 0){
									int counter = 1;
									for(Object l : temp.getLines()){
										p.sendMessage(ChatColor.WHITE + "" + counter + "). " + (String)l);
										counter++;
									}
								}
							} else {
								if(((Macro)plugin.macros.get(macroNames.indexOf(args[1].toLowerCase()))).getName().equalsIgnoreCase(args[1].toLowerCase())){
									plugin.macros.remove(macroNames.indexOf(args[1].toLowerCase()));
								}
								macroNames.remove(args[1].toLowerCase());
								p.sendMessage(ChatColor.RED + "Macro not found!");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Macro not found!");
						}
						return true;
					}
					// delete
					if(args[0].equalsIgnoreCase("delete")){
						if(macroNames.contains(args[1].toLowerCase())){
							File macro = new File (plugin.folderBase, args[1].toLowerCase());
							Macro temp = (Macro)plugin.macros.get(macroNames.indexOf(args[1].toLowerCase()));
							if(macro.exists()){
								macro.delete();
								plugin.macros.remove(macroNames.indexOf(args[1].toLowerCase()));
								macroNames.remove(args[1].toLowerCase());
								p.sendMessage(ChatColor.GREEN + "Macro deleted!");
							} else {
								
								if(temp.getName().equalsIgnoreCase(args[1].toLowerCase())){
									plugin.macros.remove(macroNames.indexOf(args[1].toLowerCase()));
								}
								macroNames.remove(args[1].toLowerCase());
								p.sendMessage(ChatColor.RED + "Unable to delete, macro not found!");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Unable to delete, macro not found!");
						}
						return true;
					}
					// perform
					// keep macros in memory to prevent more i/o
					if(macroNames.contains(args[0].toLowerCase())){
						// Check for empty file
					}
				} else if(args.length == 1){
					// list
					if(args[0].equalsIgnoreCase("list")){
						if(macroNames.size() >= 0){
							String out = "";
							int counter = 1;
							for (Object m : macroNames){
								if(macroNames.size() < counter){
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
						return true;
					}
				}
			}
		}		
		return false;
	}
}
