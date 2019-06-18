package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.RespawnRequestEvent;
import it.polimi.ingsw.event.view_controller_event.SpawnChoiceEvent;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.PowerUpDeck;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class FirstRoundManagerTest {
    private HashMap<String, VirtualView> hashMap;
    private Controller controller;
    private Player player1;
    private Player player2;

    @Before
    public void setUp(){
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        controller = new Controller(hashMap, 3);
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.DOZER);
        controller.getGameManager().getModel().getPlayers().add(player1);
        controller.getGameManager().getModel().getPlayers().add(player2);
    }

    @Test
    public void firstRoundSetupTest(){
        PowerUpDeck powerUpDeck = controller.getGameManager().getModel().getGameboard().getPowerUpDeck();
        PowerUp powerUp1 = (PowerUp) powerUpDeck.getDeck().get(0);
        PowerUp powerUp2 = (PowerUp) powerUpDeck.getDeck().get(1);
        controller.getGameManager().newRound();
        FirstRoundManager roundManager = (FirstRoundManager) controller.getGameManager().getCurrentRound();
        Assert.assertEquals(player1, roundManager.getCurrentPlayer());
        Assert.assertEquals(2, player1.getPowerUps().size());
        Assert.assertEquals(0, player2.getPowerUps().size());
        Assert.assertNotNull(hashMap.get("Federico").getToRemoteView());
        RespawnRequestEvent requestEvent = (RespawnRequestEvent) hashMap.get("Federico").getToRemoteView();
        Assert.assertEquals(powerUp1.getName(), requestEvent.getPowerUpNames()[0]);
        Assert.assertEquals(powerUp1.getColour(), requestEvent.getPowerUpColours()[0]);
        Assert.assertEquals(powerUp2.getName(), requestEvent.getPowerUpNames()[1]);
        Assert.assertEquals(powerUp2.getColour(), requestEvent.getPowerUpColours()[1]);

        SpawnChoiceEvent choiceEvent = new SpawnChoiceEvent(player1.getUsername(), requestEvent.getPowerUpNames()[0], requestEvent.getPowerUpColours()[0]);
        choiceEvent.performAction(controller);
        Assert.assertEquals(1, player1.getPowerUps().size());
        Assert.assertEquals(requestEvent.getPowerUpNames()[1], player1.getPowerUps().get(0).getName());
        Assert.assertEquals(requestEvent.getPowerUpColours()[1], player1.getPowerUps().get(0).getColour());
        Assert.assertEquals(requestEvent.getPowerUpColours()[0].toString(), player1.getPosition().getSquareColour());

    }
}
