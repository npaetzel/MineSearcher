package com.github.npaetzel.MineSearcher;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

public class Board extends Dimensions {
	
	private Difficulty difficulty;
	private Block[] blocks;
	private StopWatch stopWatch;
	private int bombs;
	private int bombsFound;
	private boolean win;
	private boolean lose;
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public Block[] getBlocks() {
		return blocks;
	}
	
	public StopWatch getStopWatch() {
		return stopWatch;
	}
	
	public int getBombs() {
		return bombs;
	}
	
	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	
	public int getBombsFound() {
		return bombsFound;
	}

	public void setBombsFound(int bombs) {
		this.bombsFound = bombs;
	}
	
	public void addBombsFound(int bombs) {
		setBombsFound(getBombsFound()+bombs);
	}
	
	public void addBombFound() {
		addBombsFound(1);
	}
	
	public void resetBombsFound() {
		setBombsFound(0);
	}
	
	public void checkBombsFound() {
		this.resetBombsFound();
		for(Block block: getBlocks()) {
			if(block.getMarked() == 1) {
				addBombFound();
			}
		}
	}
	
	public int getBombsRemaining() {
		return getBombs()-getBombsFound();
	}
	
	public boolean getWin() {
		return win;
	}
	
	public boolean getLose() {
		return lose;
	}
	
	public void setWin(boolean win) {
		this.win = win;
	}

	public void setLose(boolean lose) {
		this.lose = lose;
	}
	
	public void checkBoard(Block clickedBlock) {
		if(clickedBlock.getBomb()) {
			lose();
			return;
		}
		boolean allBombsFound = true;
		for(Block block: this.getBlocks()) {
			if(block.getMarked() == 1) {
				addBombFound();
			}
			if(!block.getBomb() && block.getState() == 0) {
				allBombsFound = false;
			}
		}
		if(allBombsFound) {
			win();
		}
	}
	
	public void win() {
		setWin(true);
		if(Highscore.checkHighscore((int)getStopWatch().stopTime(), getDifficulty())) {
			TextInputDialog dialog = new TextInputDialog("Username");
			dialog.setTitle("New Record");
			dialog.setContentText("Please enter your Username:");
			dialog.initStyle(StageStyle.UTILITY);
			Optional<String> name = dialog.showAndWait();
			
			Highscore.updateHighscore((int)(getStopWatch().getStoppedTime()/1e9), getDifficulty(), name.get());
		}
		
	}
	
	public void lose() {
		getStopWatch().stopTime();
		setLose(true);
	}
	
	public Board(Difficulty difficulty) {
		setDifficulty(difficulty);
		setDimensions(difficulty.getLength(), difficulty.getHeight());
		Block[] unsortedBlocks = new Block[difficulty.getLength()*difficulty.getHeight()];
		
		setBombs(difficulty.getBombs());
		for(int i=0; i < difficulty.getLength()*difficulty.getHeight(); i++) {
			if(i < difficulty.getBombs()) {
				unsortedBlocks[i] = new Block(this, true);
			} else {
				unsortedBlocks[i] = new Block(this);
			}
		}		
		
		int randomNumber;
		blocks = new Block[unsortedBlocks.length];
		for(int i=0; i<blocks.length; i++) {
			do {
				randomNumber = (int)(Math.random()*blocks.length);
			} while(blocks[randomNumber] != null);
			blocks[randomNumber] = unsortedBlocks[i];
		}
		stopWatch = new StopWatch();
	}
}
