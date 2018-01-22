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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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

    private final ImageView bImage1 = new ImageView("background.png");
    private final ImageView bImage2 = new ImageView("background.png");
    private final ImageView bImage3 = new ImageView("background.png");
    private final ImageView bImage4 = new ImageView("background.png");

    /**
     * ************************************** ENTRY SCREEN
     * *************************************
     */
    private final Label title = new Label("Rock! Paper! Scissors!"); // TODO: image view
    private final Button playButton = new ImageButton("playButton.png");
    private final Button exitButton = new ImageButton("exitButton.png");
    private final StackPane entryScreen = new StackPane();
    private final Scene entryScene = new Scene(entryScreen, SCREEN_WIDTH, SCREEN_HEIGHT);

    /**
     * *********************************** ROUND CHOICE SCREEN
     * *********************************
     */
    private final Button[] roundChoices = new Button[10];
    private final IntegerProperty numRounds = new SimpleIntegerProperty();
    private final Label chooseRoundNum = new Label("How many rounds do you want to play?");
    private final StackPane roundChoiceScreen = new StackPane();
    private final Scene roundChoiceScene = new Scene(roundChoiceScreen, SCREEN_WIDTH, SCREEN_HEIGHT);

    /**
     * *************************************** GAME SCREEN
     * *************************************
     */
    private Weapon currentUserChoice;
    private Weapon currentComputerChoice;

    private final Image rockIcon = new Image("rock.png");
    private final Image paperIcon = new Image("paper.png");
    private final Image scissorsIcon = new Image("scissors.png");
    private final Image loading = new Image("loading3.gif");

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
     * ************************************* RESULTS SCREEN
     * ************************************
     */
    private final Button playAgianButton = new Button("Play Agian?");
    private final Button exitToTitleScreen = new Button("Exit");
    private final IntegerProperty computerWins = new SimpleIntegerProperty();
    private final IntegerProperty userWins = new SimpleIntegerProperty();
    private final IntegerProperty ties = new SimpleIntegerProperty();
    private final Pane resultsScreen = new Pane();
    private final Scene resultsScene = new Scene(resultsScreen, SCREEN_WIDTH, SCREEN_HEIGHT);

    /**
     * moved event handlers up so I can sort of read down the event list that
     * will be called in order
     */
    private final EventHandler<MouseEvent> exitButtonEvent = (MouseEvent event) -> {
        // no need to display thanks for playing
        System.exit(0);
    };

    private final EventHandler<MouseEvent> playButtonHandler = (MouseEvent event) -> {
        primaryStage.setScene(roundChoiceScene);
    };

    private final EventHandler<MouseEvent> roundNumButtonHandler = (MouseEvent event) -> {
        // set the number of rounds the user chose to whatever button was clicked (1 to 10)
        Button source = (Button) event.getSource();
        numRounds.set(Integer.parseInt(source.getText()));
        // user cant choose a invalid value so no need to handle errors

        primaryStage.setScene(gameScene);
    };

    private final EventHandler<MouseEvent> weaponButtonHandler = (MouseEvent event) -> {
        Button source = (Button) event.getSource();

        if (source == rockButton) {
            currentUserChoice = ROCK;
        } else if (source == paperButton) {
            currentUserChoice = PAPER;
        } else if (source == scissorsButton) {
            currentUserChoice = SCISSORS;
        }

        currentComputerChoice = calculateComputerChoice();
        switch (getRoundResult()) {
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

        // hide the buttons after use makes there choice
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
        
        // after the first delay show the computer choice
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
            // TODO: darken loser brighten winner
            // need to add effect to player/computer icon
            // start the second delay
            new Thread(sleeper2).start();
        });

        // after the second delay move to next round
        sleeper2.setOnSucceeded((WorkerStateEvent event2) -> {
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
        // start the first delay
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

        HBox buttons = new HBox(playButton, exitButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPrefHeight(200);

        VBox contentsEntry = new VBox(title, buttons);
        contentsEntry.setAlignment(Pos.CENTER);

        entryScreen.getChildren().addAll(bImage1, contentsEntry);

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
        roundChoiceScreen.getChildren().addAll(bImage2, contents);

        for (int i = 0; i < roundChoices.length; i++) {
            // TODO: images
            Button button = new Button((i + 1) + "");
            rounds.getChildren().add(button);
            button.setOnMouseClicked(roundNumButtonHandler);
            roundChoices[i] = button;
        }

        class RoundNumberStringConverter extends NumberStringConverter {

            @Override
            public String toString(Number number) {
                int round = number.intValue() - 1;
                return (round != 0) ? "Rounds Left: " + round : "Final Round";
            }

            @Override
            public Number fromString(String string) {
                return numRounds.get();
            }
        }

        this.roundNumber.textProperty().bindBidirectional(numRounds, new RoundNumberStringConverter());

    }

    private void initGameScreen() {
        rockButton.setOnMouseClicked(weaponButtonHandler);
        paperButton.setOnMouseClicked(weaponButtonHandler);
        scissorsButton.setOnMouseClicked(weaponButtonHandler);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        // add user side
        grid.addColumn(1, userIcon, userChoice);
        grid.addRow(2, rockButton, paperButton, scissorsButton);

        // add computer side
        grid.addColumn(5, computerIcon, computerChoice);
        grid.add(roundNumber, 3, 0, 3, 1);

        // constrain columns and rows
        for (int i = 0; i < 7; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(SCREEN_WIDTH / 7));
        }
        for (int i = 0; i < 3; i++) {
            grid.getRowConstraints().add(new RowConstraints(SCREEN_HEIGHT / 3));
        }

        gameScreen.setAlignment(Pos.CENTER);
        gameScreen.getChildren().addAll(bImage3, grid);
    }

    private void initResultsScreen() {
        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(30);
        Label userWinsLabel = new Label("User Wins: ");
        Label computerWinsLabel = new Label("Computer Wins: ");
        Label tiesLabel = new Label("Ties: ");
        grid.addColumn(0, userWinsLabel, computerWinsLabel, tiesLabel, playAgianButton);
        Label label = new Label();
        Label label1 = new Label();
        Label label2 = new Label();
        label.textProperty().bind(userWins.asString());
        label1.textProperty().bind(computerWins.asString());
        label2.textProperty().bind(ties.asString());
        grid.addColumn(1, label, label1, label2, exitToTitleScreen);

        // flip the overall winner displayed at the end to computer, user, or empty
        ImageView winnerImage = new ImageView(userIcon.getImage());
        userWins.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (userWins.get() > computerWins.get()) {
                    winnerImage.setVisible(true);
                    winnerImage.setImage(userIcon.getImage());
                } else if (userWins.get() == computerWins.get()) {
                    winnerImage.setVisible(false);
                }
            }
        });
        computerWins.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (userWins.get() < computerWins.get()) {
                    winnerImage.setVisible(true);
                    winnerImage.setImage(computerIcon.getImage());
                } else if (userWins.get() == computerWins.get()) {
                    winnerImage.setVisible(false);
                }
            }
        });
        // initially hidden, this is to deeal with case where a game concludes with only ties
        winnerImage.setVisible(false);
        grid.addColumn(2, new Label("---Winner---"), winnerImage);

        resultsScreen.getChildren().addAll(bImage4, grid);

        // clear previous games results and goto round select screen
        playAgianButton.setOnMouseClicked(event -> {
            userWins.set(0);
            computerWins.set(0);
            ties.set(0);
            primaryStage.setScene(roundChoiceScene);
        });

        // go to title screen
        exitToTitleScreen.setOnMouseClicked(event -> {
            primaryStage.setScene(entryScene);
        });
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

        // on click: darken the image like how a button darkens slightly on click
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
