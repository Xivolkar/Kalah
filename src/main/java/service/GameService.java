package service;

import model.Game;
import model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class GameService {
    private static GameService gameServiceInstance;
    private HashMap<String, Game> gameCache = new HashMap<>();

    private GameService(){
    }

    public static GameService getInstance(){
        if(gameServiceInstance == null){
            gameServiceInstance = new GameService();
        }
        return gameServiceInstance;
    }

    public String startNewGame(int kalahNumber, User user) {
        Game game = new Game(kalahNumber, user);

        gameCache.put(game.getId(), game);

        return game.getId();
    }

    private synchronized void updateGameCache(Game game){
        this.gameCache.put(game.getId(), game);
    }

    /***
     * Returns the game if found in the store, else returns null
     * @param gameId
     * @return
     */
    public Game getGame(String gameId){
        return this.gameCache.getOrDefault(gameId, null);
    }
}
