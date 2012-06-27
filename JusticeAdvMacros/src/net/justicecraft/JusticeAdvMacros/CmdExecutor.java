package net.justicecraft.JusticeAdvMacros;

import java.io.File;
import java.io.IOException;
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
							try {
								macro.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
							macroNames.add(args[1].toLowerCase());
							plugin.macros.add(new Macro(args[1].toLowerCase()));
							p.sendMessage(ChatColor.GREEN + "Macro created with name '"+args[1].toLowerCase()+"'.");
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
								Macro tempMacro = plugin.getMacro((String)openMacro.get(player.indexOf(p.getName())));
								if(args[1].equalsIgnoreCase("var") || args[1].equalsIgnoreCase("vars")){
									int vars = Integer.parseInt(args[2]);
									if(vars >= 0){
										tempMacro.setVars(vars);
										p.sendMessage(ChatColor.GREEN+"Number of variables set to '"+vars+"' for macro '"+tempMacro.getName()+"'.");
										try {
											tempMacro.saveMacro();
										} catch (IOException e) {
											e.printStackTrace();
										}
									} else {
										p.sendMessage(ChatColor.RED + "Number of variables must be positive.");
									}
								} else if (args[1].equalsIgnoreCase("line")) {
									String line = "";
									for(int i = 2; i < args.length; i++){
										line += args[i] + " ";
									}
									line.trim();
									tempMacro.addLine(line);
									p.sendMessage(ChatColor.GREEN+"Line set for macro '"+tempMacro.getName()+"'.");
									try {
										tempMacro.saveMacro();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							} else {
								p.sendMessage(ChatColor.RED + "No macro open, use '/macro edit <name>' to edit existing macro.");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Improper format! '/macro set <line/var> <command/# of vars>'");
						}
						return true;
					}
					// remove
					if(args[0].equalsIgnoreCase("remove")){
						// Removes line number given
						if(args.length >= 2){
							if(player.contains(p.getName())){
								Macro tempMacro = plugin.getMacro((String)macroNames.get(player.indexOf(p.getName())));
								int lineNum = Integer.parseInt(args[1].trim());
								if(tempMacro.getLines().size() >= lineNum){
									tempMacro.removeLine(Integer.parseInt(args[1].trim()));
									try {
										tempMacro.saveMacro();
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(ChatColor.GREEN+"Line number "+lineNum+" removed from macro "+tempMacro.getName()+".");
								} else {
									p.sendMessage(ChatColor.RED + tempMacro.getName()+" contains no/not enough lines!");
								}
							} else {
								p.sendMessage(ChatColor.RED + "No macro open, use '/macro edit <name>' to edit existing macro.");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Improper format! '/macro remove <line number>'");
						}
						return true;
					}
					// edit
					if(args[0].equalsIgnoreCase("edit")){
						if(macroNames.contains(args[1].toLowerCase())){
							if(player.contains(p.getName())){
								openMacro.remove(player.indexOf(p.getName()));
								player.remove(p.getName());
							}
							player.add(p.getName());
							openMacro.add(args[1].toLowerCase());
							p.sendMessage(ChatColor.GREEN+"Macro '"+args[1].toLowerCase()+"' opened for editing!");
						} else {
							p.sendMessage(ChatColor.RED + args[1].toLowerCase()+" not found!");
						}
						return true;
					}
					// list
					if(args[0].equalsIgnoreCase("list")){
						if(macroNames.contains(args[1].toLowerCase())){
							File macro = new File (plugin.folderBase, args[1].toLowerCase());
							if(macro.exists()){
								Macro temp = plugin.getMacro(args[1].toLowerCase());
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
								p.sendMessage(ChatColor.RED + args[1].toLowerCase()+" not found!");
							}
						} else {
							p.sendMessage(ChatColor.RED + args[1].toLowerCase()+" not found!");
						}
						return true;
					}
					// delete
					if(args[0].equalsIgnoreCase("delete")){
						if(macroNames.contains(args[1].toLowerCase())){
							File macro = new File (plugin.folderBase, args[1].toLowerCase());
							Macro temp = (Macro)plugin.macros.get(macroNames.indexOf(args[1].toLowerCase()));
							// if sender has open macro, and this is macro, close
							if(player.contains(p.getName())){
								if(((String)openMacro.get(player.indexOf(p.getName()))).equalsIgnoreCase(args[1])){
									openMacro.remove(player.indexOf(p.getName()));
									player.remove(p.getName());
								}
							}
							if(macro.exists()){
								macro.delete();
								plugin.macros.remove(macroNames.indexOf(args[1].toLowerCase()));
								macroNames.remove(args[1].toLowerCase());
								p.sendMessage(ChatColor.GREEN + args[1].toLowerCase()+" has been deleted!");
							} else {
								if(temp.getName().equalsIgnoreCase(args[1].toLowerCase())){
									plugin.macros.remove(macroNames.indexOf(args[1].toLowerCase()));
								}
								macroNames.remove(args[1].toLowerCase());
								p.sendMessage(ChatColor.RED + "Unable to delete '"+args[1].toLowerCase()+"', macro not found!");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Unable to delete'"+args[1].toLowerCase()+"', macro not found!");
						}
						return true;
					}
					// perform
					if(macroNames.contains(args[0].toLowerCase())){
						Macro tempMacro = (Macro) plugin.macros.get(macroNames.indexOf(args[0].toLowerCase()));
						if(tempMacro.getLines().size() > 0){
							if((tempMacro.getVars() + 1) == args.length){
								
							} else if((tempMacro.getVars() + 1) > args.length){
								p.sendMessage(ChatColor.RED+"Not enough variables provided!");
							} else {
								p.sendMessage(ChatColor.RED+"Too many arguments passed!");
							}
						} else {
							p.sendMessage(ChatColor.RED+"Unable to execute macro '"+tempMacro.getName()+"'. No lines to execute!");
						}
						return true;
					}
					return true;
				} else if(args.length == 1){
					// list
					if(args[0].equalsIgnoreCase("list")){
						if(macroNames.size() >= 0){
							String out = "";
							int counter = 1;
							for (Object m : macroNames){
								if(macroNames.size() > counter){
									out += (String)m + ", ";
								} else {
									out += (String)m + ".";
								}
								counter++;
							}
							p.sendMessage(ChatColor.GREEN + "Current Macros:"+ ChatColor.WHITE + out);
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
