package com.github.npaetzel.MineSearcher;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Block extends Position {
	//Any Block has a number of different states.
	//It is either unclicked(0) or clicked(1).
	//In addition a block can be not marked at all(0), marked as a bomb(1) or marked as unsure(2).
	private int state; 
	private int marked;
	private int bombCounter;
	private boolean bomb;
	private Board board;
	private Rectangle rectangle = new Rectangle();
	private Text text = new Text();
	
	public int getState() {
		return state;
	}
	
	public int getMarked() {
		return marked;
	}
	
	public int getBombCounter() {
		return bombCounter;
	}
	
	public int addBombCounter(int x) {
		this.bombCounter += x;
		return this.bombCounter;
	}
	
	public boolean getBomb() {
		return bomb;
	} 
	
	public Board getBoard() {
		return board;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public Text getText() {
		return text;
	}
	
	public boolean setState(int state) {
		if(state == 1 || state == 0) {
			this.state = state;
			return true;
		} else {
			return false;
		}
	}
	
	public void setBombCounter(int bombCounter) {
		this.bombCounter = bombCounter;
	}
	
	public boolean setMarked(int marked) {
		if(marked >= 0 && marked <= 2) {
			this.marked = marked;
			return true;
		} else {
			return false;
		}
	}
	
	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	public void setText(Text text) {
		this.text = text;
	}
	
	public void changeText(String text) {
		this.text.setText(text);
	}
	
	public void blockBomb() {
		this.getRectangle().setFill(Color.RED);
		this.setState(1);
	}
	
	public void blockClick(Board board) {
		if(this.getState() == 0) {
			for(int i=-1; i<2; i++) {
				for(int j=-1; j<2; j++) {
					if(this.bombsArround(i, j, board)) {
						this.addBombCounter(1);
					}
				}
			}
		}

		if(this.getBombCounter() > 0) {
			this.changeText(Integer.toString(this.getBombCounter()));
		}
		this.getRectangle().setFill(Color.GREY);
		this.setState(1);
				
		if(this.getBombCounter() == 0) {
			for(int i=-1; i<2; i++) {
				for(int j=-1; j<2; j++) {
					this.blockArround(i, j, board);
				}
			}			
		}
	}
	
	
	public boolean bombsArround(int x, int y, Board board) {
		Block block = Block.getBlock(board.getBlocks(), this.getX()+x, this.getY()+y);
		if(block != null && block.getBomb()) {
			return true;
		}
		return false;
	}
	
	public void blockArround(int x, int y, Board board) {
		Block block = Block.getBlock(board.getBlocks(), this.getX()+x, this.getY()+y);
		if(block != null && !block.getBomb() && block.getMarked() == 0 && block.getState() == 0) { 
			block.blockClick(board);
		}
	}
	
	public void quickClick(int bombsArround) {
		Block[] blocksArround = new Block[9];
		int blocksArroundMarked = 0;
		
		Block block;
		int counter = 0;
		for(int i=-1; i<2; i++) {
			for(int j=-1; j<2; j++) {
				block = Block.getBlock(board.getBlocks(), this.getX()+i,  this.getY()+j);
				if(block != null) {
					blocksArround[counter++] = block;
					if(block.getMarked() == 1) {
						blocksArroundMarked++;
					}
				}
			}
		}
		if(blocksArroundMarked == bombsArround) {
			for(Block blockArround: blocksArround) {
				if(blockArround != null && blockArround.getMarked() == 0) {
					blockArround.blockClick(board);
				}
			}
		}
	}
	
	public void blockMarkBomb() {
		this.getRectangle().setFill(Color.YELLOW);
		this.setMarked(1);
	}

	public void blockMarkUnsure() {
		this.getRectangle().setFill(Color.BLUE);
		this.setMarked(2);
	}
	
	public void blockUnmark() {
		this.getRectangle().setFill(Color.DARKGREEN);
		this.setMarked(0);
	}
	
	public Block(Board board, int x, int y, boolean bomb) {
		this.setBoard(board);
		this.setPosition(x, y);
		this.setState(-1);
		this.setMarked(0);
		this.setBomb(bomb);
	}
	
	public Block(Board board) {
		this(board, 0, 0, false);
	}
	
	public Block(Board board, int x, int y) {
		this(board, x, y, false);
	}
	
	public Block(Board board, boolean bomb) {
		this(board, 0, 0, bomb);
	}
	
	public static Block getBlock(Block[] blocks, int x, int y) {
		for(Block block: blocks) {
			if(block.getX() == x && block.getY() == y)
				return block;
		}
		return null;
	}
}
