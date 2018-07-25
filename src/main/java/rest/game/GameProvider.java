package rest.game;

import model.Game;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import rest.beans.GameBean;
import rest.game.transformers.GameTransformer;
import security.InvalidOperationException;
import service.GameService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameProvider {

    @Autowired
    GameService gameService;

    public GameProvider() {
        gameService = GameService.getInstance();
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public ResponseEntity<?> createNewGame(UriComponentsBuilder uriBuilder) {
        String gameId = gameService.startNewGame(6, new User());

        UriComponents uriComponents = uriBuilder.path("games/{id}").buildAndExpand(gameId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{gameId}/pits/{pitId}")
    @ResponseBody
    public ResponseEntity<GameBean> makeAMove(@PathVariable("gameId") String gameId, @PathVariable("pitId") int pitId){
        Game game = gameService.getGame(gameId);

        if(game == null) {
            return new ResponseEntity(new InvalidOperationException("H", "Play a move", "No such game exists."), HttpStatus.NOT_FOUND);
        }

        try {
            game.play(pitId - 1, new User());
        } catch (InvalidOperationException ioe){
            return new ResponseEntity(ioe, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<GameBean>(GameTransformer.fromGame(game), HttpStatus.OK);
    }
}
