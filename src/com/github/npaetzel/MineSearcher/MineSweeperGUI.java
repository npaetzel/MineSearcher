package com.github.npaetzel.MineSearcher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MineSweeperGUI extends Application {
	public static int blocksize = 25;
	public static int blockspace = 2;
	public static int fontsize = (int) (blocksize*0.8);
	public static Board board = new Board(Difficulty.EASY);
	
	@Override
	public void start(Stage mainStage) throws Exception {
		int counter = 0;

		Group bottom = new Group();
		bottom.setTranslateX(blockspace*2);
		bottom.setTranslateY(blockspace*2);
		
		Group top = new Group();
		top.setTranslateX(blockspace*2+fontsize/2);
		
		MenuBar menuBar = new MenuBar();
		Menu menuGame = new Menu("Game");
				Menu newGame = new Menu("New Game");
					MenuItem easy = new MenuItem("Easy");
					MenuItem normal = new MenuItem("Normal");
					MenuItem hard = new MenuItem("Hard");
				newGame.getItems().addAll(easy, normal, hard);
				MenuItem highscores = new MenuItem("Highscores");
				MenuItem exit = new MenuItem("Exit");
			menuGame.getItems().addAll(newGame, highscores, exit);
		menuBar.getMenus().add(menuGame);
		
		easy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				board = new Board(Difficulty.EASY);
				mainStage.close();
				Platform.runLater( () -> {
					try {
						new MineSweeperGUI().start(new Stage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		});
		normal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				board = new Board(Difficulty.NORMAL);
				mainStage.close();
				Platform.runLater( () -> {
					try {
						new MineSweeperGUI().start(new Stage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		});
		hard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				board = new Board(Difficulty.HARD);
				mainStage.close();
				Platform.runLater( () -> {
					try {
						new MineSweeperGUI().start(new Stage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		});
		highscores.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new MineSweeperGUIHighscores();
			}
		});
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
			}
		});
		
		Image standardIcon = new Image("smiley_smiling.jpg");
		Image winIcon = new Image("smiley_cool.jpg");
		Image loseIcon = new Image("smiley_blood.jpg");
		ImageView iconView = new ImageView(standardIcon);
		
		Rectangle timeBackground = new Rectangle(50, 25, Color.BLACK);
		Text timeText = new Text("0");
		timeText.setFont(new Font(fontsize));
		timeText.setFill(Color.RED);
		board.getStopWatch().setTimerText(timeText);
		board.getStopWatch().startTime();
		StackPane timeStack = new StackPane(timeBackground, timeText);
				
		Rectangle bombsRemainingBackground = new Rectangle(50, 25 , Color.BLACK);
		Text bombsRemainingText = new Text();
		if(board.getBombsRemaining() > 9) {
			bombsRemainingText.setText(
					"0" + Integer.toString(board.getBombsRemaining()));
		} else {
			bombsRemainingText.setText(
					"00" + Integer.toString(board.getBombsRemaining()));
		}
		bombsRemainingText.setFont(new Font(fontsize));
		bombsRemainingText.setFill(Color.RED);
		StackPane bombsRemainingStack = new StackPane(bombsRemainingBackground, bombsRemainingText);
		
		BorderPane statsBar = new BorderPane();	
		statsBar.setCenter(iconView);
		statsBar.setLeft(timeStack);
		statsBar.setRight(bombsRemainingStack);
				
		StackPane stack = new StackPane();
		stack.getChildren().addAll(bottom, top);
		stack.setAlignment(Pos.TOP_LEFT);
		
		VBox OverallVBox = new VBox();
		OverallVBox.getChildren().addAll(menuBar, statsBar, stack);

		InnerShadow shadow = new InnerShadow();
		shadow.setOffsetX(2.0);
		shadow.setOffsetY(2.0);
		
		for(int i=0; i<board.getLength(); i++) {
			for(int j=0; j<board.getHeight(); j++) {
				Rectangle rectangle = new Rectangle(blocksize, blocksize, Color.DARKGREEN);
				rectangle.setX(blockspace+i*(blocksize+blockspace));
				rectangle.setY(blockspace+j*(blocksize+blockspace));
				rectangle.setArcWidth(blocksize/4);
				rectangle.setArcHeight(blocksize/4);
				rectangle.setEffect(shadow);
				bottom.getChildren().add(rectangle);
				
				Text text = new Text(rectangle.getX(), rectangle.getY(), "");
				text.setFont(new Font(fontsize));
				top.getChildren().add(text);
				
				Block block = board.getBlocks()[counter++];
				block.setRectangle(rectangle);
				block.setText(text);
				block.setX(i);
				block.setY(j);
				
				text.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if(!board.getWin() && !board.getLose()) {
							if(event.getButton() == MouseButton.PRIMARY 
							&& event.getClickCount() > 1
							&& text.getText().length() > 0
							&& Integer.parseInt(text.getText()) > 0) {
								block.quickClick(Integer.parseInt(text.getText()));
								board.checkBoard(block);
								if(board.getWin()) {
									iconView.setImage(winIcon);
								} else if(board.getLose()) {
									iconView.setImage(loseIcon);
								}
							}
						}
					}
				});
												
				rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {		
						@Override
						public void handle(MouseEvent event) {
							if(!board.getWin() && !board.getLose()) {
								if(event.getButton() == MouseButton.PRIMARY 
								&& block.getMarked() == 0) {
									if(event.getClickCount() == 1) {
										if(block.getBomb()) {
											block.blockBomb();
										} else {
											block.blockClick(board);
										}
										block.setState(1);
										board.checkBoard(block);
										if(board.getWin()) {
											iconView.setImage(winIcon);
										} else if(board.getLose()) {
											iconView.setImage(loseIcon);
										}
									} else if(text.getText().length() > 0
									&& Integer.parseInt(text.getText()) > 0) {
										block.quickClick(Integer.parseInt(text.getText()));
									}
								} else if(event.getButton() == MouseButton.SECONDARY
								&& block.getState() != 1) {
										if(block.getMarked() == 0) {
											block.blockMarkBomb();
										} else if(block.getMarked() == 1) {
											block.blockMarkUnsure();
										} else {
											block.blockUnmark();
										}
								}
								board.checkBombsFound();
								if(board.getBombsRemaining() > 9) {
									bombsRemainingText.setText(
											"0" + Integer.toString(board.getBombsRemaining()));
								} else {
									bombsRemainingText.setText(
											"00" + Integer.toString(board.getBombsRemaining()));
								}
							}

						}
				});
				

			}
		}
			
				
		Scene scene = new Scene(OverallVBox, 
				board.getLength()*(blocksize+blockspace)+blockspace*3, 
				25+50+board.getHeight()*(blocksize+blockspace)+blockspace*3, 
				Color.GREY);
		
		mainStage.setTitle("MineSweeper");
		mainStage.setScene(scene);
		mainStage.show();
	}

}
