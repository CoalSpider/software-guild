/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguild.assignment1;

import java.util.Random;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.sg.softwareguild.assignment1.Weapon.*;
import static com.sg.softwareguild.assignment1.RoundResult.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author Ben Norman
 *
 */
public class Roshambo extends Application {

    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 250;

    private Stage primaryStage;

    private final ImageView bImage = new ImageView("background.png");
    /**
     * ****************************************************************************************************
     */
    /**
     * ************************************** ENTRY SCREEN
     * *************************************
     */
    /**
     * ****************************************************************************************************
     */
    private final Label title = new Label("Rock! Paper! Scissors!"); // TODO: image view
    private final Button playButton = new ImageButton("playButton.png");
    private final Button exitButton = new ImageButton("exitButton.png");
    private final StackPane entryScreen = new StackPane();
    private final Scene entryScene = new Scene(entryScreen, SCREEN_WIDTH, SCREEN_HEIGHT);

    /**
     * ****************************************************************************************************
     */
    /**
     * *********************************** ROUND CHOICE SCREEN
     * *********************************
     */
    /**
     * ****************************************************************************************************
     */
    private final Button[] roundChoices = new Button[10];
    private final IntegerProperty numRounds = new SimpleIntegerProperty();
    private final Label chooseRoundNum = new Label("How many rounds do you want to play?");
    private final StackPane roundChoiceScreen = new StackPane();
    private final Scene roundChoiceScene = new Scene(roundChoiceScreen, SCREEN_WIDTH, SCREEN_HEIGHT);

    /**
     * ****************************************************************************************************
     */
    /**
     * *************************************** GAME SCREEN
     * *************************************
     */
    /**
     * ****************************************************************************************************
     */
    private Weapon currentUserChoice;
    private Weapon currentComputerChoice;
    private RoundResult currentRoundWinner;

    private final Image rockIcon = new Image("rock.png");
    private final Image paperIcon = new Image("paper.png");
    private final Image scissorsIcon = new Image("scissors.png");
    private final Image loading = new Image("loading3.gif");

    private final Label winnerLabel = new Label("Winner");
    private final Label looserLabel = new Label("Loser");
    private final Label roundNumber = new Label(); // round + i

    private final Button rockButton = new ImageButton("rock.png"); // todo image button but pass in image view
    private final Button paperButton = new ImageButton("paper.png");
    private final Button scissorsButton = new ImageButton("scissors.png");
    private final ImageView userChoice = new ImageView(loading);
    private final ImageView userIcon = new ImageView("userIcon.png");

    private final ImageView computerIcon = new ImageView("computerIcon.png");
    private final ImageView computerChoice = new ImageView(loading);

    private final StackPane gameScreen = new StackPane();
    private final Scene gameScene = new Scene(gameScreen, SCREEN_WIDTH, SCREEN_HEIGHT);

    /**
     * ****************************************************************************************************
     */
    /**
     * ************************************* RESULTS SCREEN
     * ************************************
     */
    /**
     * ****************************************************************************************************
     */
    private final Button playAgianButton = new Button("Play Agian?");
    private final Button exitToTitleScreen = new Button("Exit");
    private final IntegerProperty computerWins = new SimpleIntegerProperty();
    private final IntegerProperty userWins = new SimpleIntegerProperty();
    private final IntegerProperty ties = new SimpleIntegerProperty();
    private final Label userWinsLabel = new Label("User Wins: ");
    private final Label computerWinsLabel = new Label("Computer Wins: ");
    private final Label tiesLabel = new Label("Ties: ");
    private final Pane resultsScreen = new Pane();
    private final Scene resultsScene = new Scene(resultsScreen, SCREEN_WIDTH, SCREEN_HEIGHT);

    /**
     * moved event handlers up so I can sort of read down the event list that
     * will be called in order
     */
    EventHandler<MouseEvent> exitButtonEvent = (MouseEvent event) -> {
        // TODO: display thanks for playing
        System.exit(0);
    };

    EventHandler<MouseEvent> playButtonHandler = (MouseEvent event) -> {
        primaryStage.setScene(roundChoiceScene);
    };

    EventHandler<MouseEvent> roundNumButtonHandler = (MouseEvent event) -> {
        // set the number of rounds the user chose to whatever button was clicked (1 to 10)
        Button source = (Button) event.getSource();
        numRounds.set(Integer.parseInt(source.getText()));

        primaryStage.setScene(gameScene);
        // TODO: anim out offscreen
        // TODO: anim in offscreen
        // TODO: show weapon anim onscren
    };

    EventHandler<ActionEvent> testHandler = (ActionEvent event) -> {
        currentRoundWinner = getRoundResult();

        switch (currentRoundWinner) {
            case COMPUTER_WIN:
                computerWins.set(computerWins.get() + 1);
                break;
            case TIE:
                ties.set(ties.get() + 1);
                break;
            case USER_WIN:
                userWins.set(userWins.get() + 1);
                break;
        }

        if (numRounds.get() > 1) {
            // TODO: next round anim

            numRounds.set(numRounds.get() - 1);
        } else {
            // TODO: show results
            // TODO: continue button // move below to continue button listener
            // TODO: show play agian buttons
            // TODO: ask to play agian
        }
    };

    EventHandler<MouseEvent> weaponButtonHandler = (MouseEvent event) -> {
        Button source = (Button) event.getSource();
        // buttons text is the same as the enums string value
        if (source == rockButton) {
            currentUserChoice = ROCK;
        } else if (source == paperButton) {
            currentUserChoice = PAPER;
        } else if (source == scissorsButton) {
            currentUserChoice = SCISSORS;
        }

        // TODO: slide out non choice then choice
        // TODO: Start countdown anim (keyframe 1)
        currentComputerChoice = calculateComputerChoice();
        switch (getRoundResult()) {
            case COMPUTER_WIN:
                computerWins.set(computerWins.get() + 1);
            case TIE:
                ties.set(ties.get() + 1);
            case USER_WIN:
                userWins.set(userWins.get() + 1);
            default: // should never happen
        }

        // TODO: show winner anim
        switch (currentUserChoice) {
            case ROCK:
                userChoice.setImage(rockIcon);
                break;
            case PAPER:
                userChoice.setImage(paperIcon);
                break;
            case SCISSORS:
                userChoice.setImage(scissorsIcon);
                break;
        }
        rockButton.setVisible(false);
        paperButton.setVisible(false);
        scissorsButton.setVisible(false);

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // swallow
                }
                return null;
            }
        };
        Task<Void> sleeper2 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // swallow
                }
                return null;
            }
        };
        sleeper.setOnSucceeded((WorkerStateEvent event1) -> {
            switch (currentComputerChoice) {
                case ROCK:
                    computerChoice.setImage(rockIcon);
                    break;
                case PAPER:
                    computerChoice.setImage(paperIcon);
                    break;
                case SCISSORS:
                    computerChoice.setImage(scissorsIcon);
                    break;
            }
            new Thread(sleeper2).start();
        });
        sleeper2.setOnSucceeded((WorkerStateEvent event1) -> {
            rockButton.setVisible(true);
            paperButton.setVisible(true);
            scissorsButton.setVisible(true);
            computerChoice.setImage(loading);
            userChoice.setImage(loading);
            if (numRounds.get() <= 1) {
                primaryStage.setScene(resultsScene);
            } else {
                numRounds.set(numRounds.get() - 1);
            }
        });
        new Thread(sleeper).start();
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setScene(entryScene);

        initStartScreen();
        initRoundChoiceScreen();
        initGameScreen();
        initResultsScreen();

        this.primaryStage.show();
    }

    private void initStartScreen() {
        entryScreen.setAlignment(Pos.CENTER);

        // for some reason bImage is being ignored.... dont know why
        HBox buttons = new HBox(playButton, exitButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPrefHeight(200);
        VBox contentsEntry = new VBox(title, buttons);
        contentsEntry.setAlignment(Pos.CENTER);
        entryScreen.getChildren().addAll(bImage, contentsEntry);

        playButton.setOnMouseClicked(playButtonHandler);

        exitButton.setOnMouseClicked(exitButtonEvent);
    }

    private void initRoundChoiceScreen() {
        roundChoiceScreen.setAlignment(Pos.CENTER);
        HBox rounds = new HBox();
        rounds.setPrefHeight(200);
        rounds.setAlignment(Pos.CENTER);
        VBox contents = new VBox(chooseRoundNum, rounds);
        contents.setAlignment(Pos.CENTER);
        roundChoiceScreen.getChildren().addAll(bImage, contents);

        for (int i = 0; i < roundChoices.length; i++) {
            // todo custom from url+"i"
            Button button = new Button((i + 1) + "");
            rounds.getChildren().add(button);
            button.setOnMouseClicked(roundNumButtonHandler);
            roundChoices[i] = button;
        }

        class MyNSC extends NumberStringConverter {

            @Override
            public String toString(Number number) {
                int round = number.intValue() - 1;
                return (round != 0) ? "Rounds Left: " + round : "Final Round";
            }

            public Number fromString(String string) {
                return numRounds.get();
            }
        }

        this.roundNumber.textProperty().bindBidirectional(numRounds, new MyNSC());
    }

    private void initGameScreen() {
        rockButton.setOnMouseClicked(weaponButtonHandler);
        paperButton.setOnMouseClicked(weaponButtonHandler);
        scissorsButton.setOnMouseClicked(weaponButtonHandler);

        GridPane grid = new GridPane();
        // grid.setGridLinesVisible(true);

        grid.setAlignment(Pos.CENTER);

        grid.addColumn(1, userIcon, userChoice);
        grid.addRow(2, rockButton, paperButton, scissorsButton);

        grid.addColumn(5, computerIcon, computerChoice);

        grid.add(roundNumber, 3, 0, 3, 1);

        for (int i = 0; i < 7; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(SCREEN_WIDTH / 7));
            //  grid.getColumnConstraints().get(i).setMinWidth(SCREEN_WIDTH/7);
        }
        for (int i = 0; i < 3; i++) {
            grid.getRowConstraints().add(new RowConstraints(SCREEN_HEIGHT / 3));
        }

        gameScreen.setAlignment(Pos.CENTER);
        gameScreen.getChildren().addAll(bImage, grid);
    }

    private void initResultsScreen() {
        VBox contents = new VBox(userWinsLabel, computerWinsLabel, tiesLabel, new HBox(playAgianButton, exitToTitleScreen));
        GridPane grid = new GridPane();
        resultsScreen.getChildren().add(contents);
    }

    // TODO: pass in AI so we can change from say a random AI to one that chooses only rock
    private static final Random RANDOM = new Random();

    private Weapon calculateComputerChoice() {
        // choose a random weapon from the enum
        int choice = RANDOM.nextInt(Weapon.values().length);
        return Weapon.values()[choice];
    }

    private RoundResult getRoundResult() {
        Weapon w1 = currentUserChoice;
        Weapon w2 = currentComputerChoice;
        switch (w1) {
            case ROCK:
                switch (w2) {
                    // case ROCK: return 0;
                    case PAPER:
                        return COMPUTER_WIN;
                    case SCISSORS:
                        return USER_WIN;
                }
                break;
            case PAPER:
                switch (w2) {
                    case ROCK:
                        return USER_WIN;
                    //  case PAPER: return 0;
                    case SCISSORS:
                        return COMPUTER_WIN;
                }
                break;
            case SCISSORS:
                switch (w2) {
                    case ROCK:
                        return COMPUTER_WIN;
                    case PAPER:
                        return USER_WIN;
                    // case SCISSORS: return 0;
                }
                break;
        }
        return TIE;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/**
 * Custom button that hides the default button and only displays the given image
 * *
 */
class ImageButton extends Button {

    ImageButton(String imageUrl) {
        ImageView view = new ImageView(imageUrl);
        ColorAdjust adjust = new ColorAdjust();
        view.setEffect(adjust);
        this.setGraphic(view);

        setStyle("-fx-background-color: transparent;");

        // on click darken the image like how a button darkens slightly on click
        setOnMousePressed((MouseEvent event) -> {
            adjust.setBrightness(-0.5f);
        });

        // reset the brightness
        setOnMouseReleased((MouseEvent event) -> {
            adjust.setBrightness(0);
        });
    }
}

enum Weapon {
    ROCK(1, "Rock"), PAPER(1, "Paper"), SCISSORS(3, "Scissors");

    int val;
    String stringVal;

    Weapon(int val, String stringVal) {
        this.val = val;
        this.stringVal = stringVal;
    }
}

enum RoundResult {
    COMPUTER_WIN, TIE, USER_WIN;
}
