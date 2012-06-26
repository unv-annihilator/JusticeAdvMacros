package net.justicecraft.JusticeAdvMacros;

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
	
	public void removeLine(String l){
		lines.remove(l);
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
}
