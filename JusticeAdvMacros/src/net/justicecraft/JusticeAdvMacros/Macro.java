package net.justicecraft.JusticeAdvMacros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Macro {
	private String name = "";
	private int vars = 0;
	private List lines = new ArrayList<String>();
	
	public Macro(String n){
		name = n;
	}
	
	public Macro(String n, int v, List<String> l){
		name = n;
		vars = v;
		lines = l;
	}
	
	public void setVars(int v){
		vars = v;
	}
	
	public void addLine(String l){
		lines.add(l);
	}
	
	public void removeLine(int i){
		if(lines.size() >= i){
			lines.remove(i);
		}
	}
	
	public int getVars(){
		return vars;
	}
	
	public String getName(){
		return name;
	}
	
	public List<String> getLines(){
		return lines;
	}
	
	public void saveMacro() throws IOException{
		File macro = new File(Core.folderBase, this.name);
		if(macro.exists()){
			macro.delete();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter (macro));
		writer.write("var: "+vars);
		for(int i = 0; i < lines.size(); i++){
			writer.newLine();
			writer.write((String)lines.get(i));
		}
		writer.flush();
		writer.close();
	}
}
