package com.github.npaetzel.MineSearcher;
import javafx.application.Application;

public class MineSweeper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Highscore.loadHighscores();
		
				
		Application.launch(MineSweeperGUI.class, args);
	}


}
