package rest.game.transformers;

import model.Game;
import rest.beans.GameBean;

import java.util.HashMap;

public class GameTransformer {
    public static GameBean fromGame(Game g){
        GameBean bean = new GameBean();
        HashMap<String, Integer> pitMap = new HashMap<>();

        bean.setId(g.getId());
        bean.setUrl(g.getUrl());

        int[] pits = g.getPits();
        int pitLength = pits.length;

        for(int index = 0; index < pitLength; index++){
            pitMap.put(String.valueOf(index + 1), pits[index]);
        }

        bean.setStatus(pitMap);

        return bean;
    }
}
