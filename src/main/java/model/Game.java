package model;

import security.DuplicateObjectException;
import security.InvalidOperationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Game {
    private String id;
    private String url;
    private int[] pits;
    private ArrayList<User> players;
    private int currentPlayerIndex = 0;
    private boolean isGameComplete;

    public String getId() {
        return this.id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public int[] getPits() {
        return this.pits;
    }

    public boolean isGameComplete() {
        return this.isGameComplete;
    }

    public Game(int seedsPerPit, User user) {
        this.id = UUID.randomUUID().toString();
        this.pits = new int[14];
        Arrays.fill(this.pits, seedsPerPit);

        this.pits[6] = 0;
        this.pits[13] = 0;

        this.players = new ArrayList<>(2);
        this.players.add(user);
    }

    public void addPlayer(User user) throws DuplicateObjectException, Exception {
        if (players.size() == 2) {
            throw new Exception("Can't add more players to this game");
        } else if (players.contains(user)) {
            throw new DuplicateObjectException(user.getId(), "Player already in this game.");
        } else {
            players.add(user);
        }
    }

    public boolean hasPlayer(User user) {
        return players.contains(user);
    }

    public void play(int pitId, User user) throws InvalidOperationException {
        if (players.get(currentPlayerIndex).getId() != user.getId()) {
            throw new InvalidOperationException(user.getId(), "Invalid move", "It isn't currently your turn.");
        } else if (pitId >= getCurrentPlayerPitIndex() || pitId <= getPreviousPlayerPitIndex()) {
            throw new InvalidOperationException(user.getId(), "Invalid move", "You can only make a move with seeds from your designated pits");
        } else if (pits[pitId] == 0) {
            throw new InvalidOperationException(user.getId(), "Invalid move", "No seeds in this pit.");
        }

        // Take seeds; empty current pit
        int temp = pits[pitId];
        pits[pitId] = 0;

        int currentIndex = pitId + 1;

        while(temp != 0){
            if(isCurrentIndexPit(currentIndex) && (currentIndex + 1) != getCurrentPlayerPitIndex()){
                continue;
            }

            pits[currentIndex] = pits[currentIndex] + 1;
            temp--;
            currentIndex++;

            if(currentIndex == pits.length)
                currentIndex = 0;
        }

        if(currentIndex != getCurrentPlayerPitIndex()){
            this.currentPlayerIndex++;

            if(this.currentPlayerIndex >= 2){
                this.currentPlayerIndex = 0;
            }
        }
    }

    private boolean isCurrentIndexPit(int currentIndex){
        return (currentIndex + 1) % 7 == 0;
    }

    private int getCurrentPlayerPitIndex() {
        return (7 * (this.currentPlayerIndex + 1)) - 1;
    }

    private int getPreviousPlayerPitIndex() {
        if (this.currentPlayerIndex == 0) {
            return -1;
        }

        return (7 * (this.currentPlayerIndex - 1)) - 1;
    }
}
