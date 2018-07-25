package rest.game;

import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import rest.beans.GameBean;
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

        ResponseEntity responseEntity = provider.makeAMove(gameId, 7);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testMakeAMoveInputCounterpartPitId(){
        GameService gs = GameService.getInstance();
        String gameId = gs.startNewGame(6, new User());

        ResponseEntity responseEntity = provider.makeAMove(gameId, 8);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testMakeAMove(){
        GameService gs = GameService.getInstance();
        String gameId = gs.startNewGame(6, new User());

        ResponseEntity responseEntity = provider.makeAMove(gameId, 6);
        GameBean gameBean = (GameBean) responseEntity.getBody();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(gameId, gameBean.getId());
        Assert.assertTrue(gameBean.getStatus().get("6") == 0);
        Assert.assertTrue(gameBean.getStatus().get("7") == 1);
        Assert.assertTrue(gameBean.getStatus().get("8") == 7);
        Assert.assertTrue(gameBean.getStatus().get("9") == 7);
        Assert.assertTrue(gameBean.getStatus().get("10") == 7);
        Assert.assertTrue(gameBean.getStatus().get("11") == 7);
        Assert.assertTrue(gameBean.getStatus().get("12") == 7);
        Assert.assertTrue(gameBean.getStatus().get("13") == 6);
    }
}
