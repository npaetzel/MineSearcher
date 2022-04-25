package com.github.npaetzel.MineSearcher;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MineSweeperGUIHighscores extends Stage {
	Stage stageHighscores;
	
	public MineSweeperGUIHighscores() {
		
		GridPane root = new GridPane();
		root.setVgap(10);
		root.setHgap(5);
		root.setPadding(new Insets(10, 10, 10, 10));
		
		Text easy = new Text("Easy");
		easy.setFont(new Font(MineSweeperGUI.fontsize));
		root.add(easy, 0, 0);
		
		Text normal = new Text("Normal");
		normal.setFont(new Font(MineSweeperGUI.fontsize));
		root.add(normal, 0, 1);
		
		Text hard = new Text("Hard");
		hard.setFont(new Font(MineSweeperGUI.fontsize));
		root.add(hard, 0, 2);
		
		Text easyName = new Text(Highscore.getHighscore(Difficulty.EASY).getName());
		easyName.setFont(new Font(MineSweeperGUI.fontsize));
		root.add(easyName, 1, 0);
		
		Text normalName = new Text(Highscore.getHighscore(Difficulty.NORMAL).getName());
		normalName.setFont(new Font(MineSweeperGUI.fontsize));
		root.add(normalName, 1, 1);
		
		Text hardName = new Text(Highscore.getHighscore(Difficulty.HARD).getName());
		hardName.setFont(new Font(MineSweeperGUI.fontsize));
		root.add(hardName, 1, 2);
		
		Text easyTime = new Text(Integer.toString(Highscore.getHighscore(Difficulty.EASY).getTime()));
		easyTime.setFont(new Font(MineSweeperGUI.fontsize));
		root.add(easyTime, 2, 0);
		
		Text normalTime = new Text(Integer.toString(Highscore.getHighscore(Difficulty.NORMAL).getTime()));
		normalTime.setFont(new Font(MineSweeperGUI.fontsize));
		root.add(normalTime, 2, 1);
		
		Text hardTime = new Text(Integer.toString(Highscore.getHighscore(Difficulty.HARD).getTime()));
		hardTime.setFont(new Font(MineSweeperGUI.fontsize));
		root.add(hardTime, 2, 2);
		
		
		Scene scene = new Scene(root);
		
		this.setScene(scene);
		this.initStyle(StageStyle.UTILITY);
		this.setTitle("Highscores");
		this.show();	
	}
}
