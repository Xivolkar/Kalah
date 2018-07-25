package rest.game;

import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import service.GameService;

public class GameProviderTest {
    GameProvider provider;

    @Before
    public void setup(){
        provider = new GameProvider();
    }

    @Test
    public void testCreateNewGame(){
        ResponseEntity responseEntity = provider.createNewGame(UriComponentsBuilder.fromUriString("http://localhost:8080/"));

        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertFalse(responseEntity.getHeaders().getLocation().toString().isEmpty());
    }

    @Test
    public void testMakeAMoveGameNotExisting(){
        ResponseEntity responseEntity = provider.makeAMove("123", 1);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testMakeAMoveInvalidMove(){
        GameService gs = GameService.getInstance();
        String gameId = gs.startNewGame(6, new User());

        ResponseEntity responseEntity = provider.makeAMove(gameId, -1);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testMakeAMoveInputHousePitId(){
        GameService gs = GameService.getInstance();
        String gameId = gs.startNewGame(6, new User());

        ResponseEntity responseEntity = provider.makeAMove(gameId, 6);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testMakeAMoveInputCounterpartPitId(){
        GameService gs = GameService.getInstance();
        String gameId = gs.startNewGame(6, new User());

        ResponseEntity responseEntity = provider.makeAMove(gameId, 8);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
