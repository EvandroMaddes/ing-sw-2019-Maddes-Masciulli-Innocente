package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.AsActionPowerUpRequestEvent;
import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.RespawnRequestEvent;
import it.polimi.ingsw.event.view_controller_event.SpawnChoiceEvent;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.PowerUpDeck;
import it.polimi.ingsw.model.game_components.cards.power_ups.Newton;
import it.polimi.ingsw.model.game_components.cards.power_ups.Teleporter;
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
    private FirstRoundManager roundManager;
    private PowerUp powerUp1;
    private PowerUp powerUp2;

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
        PowerUpDeck powerUpDeck = controller.getGameManager().getModel().getGameboard().getPowerUpDeck();
        powerUp1 = (PowerUp) powerUpDeck.getDeck().get(0);
        powerUp2 = (PowerUp) powerUpDeck.getDeck().get(1);
        controller.getGameManager().newRound();
        roundManager = (FirstRoundManager) controller.getGameManager().getCurrentRound();
    }

    @Test
    public void firstRoundSetupTest(){
        Assert.assertEquals(player1, roundManager.getCurrentPlayer());
        Assert.assertEquals(2, player1.getPowerUps().size());
        Assert.assertEquals(0, player2.getPowerUps().size());
        Assert.assertNotNull(hashMap.get("Federico").getToRemoteView());
        ControllerViewEvent requestEvent = (RespawnRequestEvent) hashMap.get("Federico").getToRemoteView();
        Assert.assertEquals(2, ((RespawnRequestEvent)requestEvent).getPowerUpNames().length);
        Assert.assertEquals(powerUp1.getName(), ((RespawnRequestEvent)requestEvent).getPowerUpNames()[0]);
        Assert.assertEquals(powerUp1.getColour(), ((RespawnRequestEvent) requestEvent).getPowerUpColours()[0]);
        Assert.assertEquals(powerUp2.getName(), ((RespawnRequestEvent)requestEvent).getPowerUpNames()[1]);
        Assert.assertEquals(powerUp2.getColour(), ((RespawnRequestEvent)requestEvent).getPowerUpColours()[1]);
        player1.discardPowerUp(powerUp2);
        player1.addPowerUp(new Teleporter(CubeColour.Blue));

        SpawnChoiceEvent choiceEvent = new SpawnChoiceEvent(player1.getUsername(), ((RespawnRequestEvent)requestEvent).getPowerUpNames()[0], ((RespawnRequestEvent)requestEvent).getPowerUpColours()[0]);
        choiceEvent.performAction(controller);
        Assert.assertEquals(1, player1.getPowerUps().size());
        Assert.assertEquals("Teleporter", player1.getPowerUps().get(0).getName());
        Assert.assertEquals(CubeColour.Blue, player1.getPowerUps().get(0).getColour());
        Assert.assertEquals(((RespawnRequestEvent)requestEvent).getPowerUpColours()[0].toString(), player1.getPosition().getSquareColour());

        requestEvent = (AsActionPowerUpRequestEvent) hashMap.get("Federico").getToRemoteView();
        Assert.assertEquals(1, ((AsActionPowerUpRequestEvent)requestEvent).getPowerUpColours().length);
        Assert.assertEquals("Teleporter", ((AsActionPowerUpRequestEvent)requestEvent).getPowerUpNames()[0]);
        Assert.assertEquals(CubeColour.Blue, ((AsActionPowerUpRequestEvent)requestEvent).getPowerUpColours()[0]);
    }

    @Test
    public void firstRoundNewtonTest(){
        ControllerViewEvent requestEvent = (RespawnRequestEvent) hashMap.get("Federico").getToRemoteView();
        player1.discardPowerUp(powerUp2);
        player1.addPowerUp(new Newton(CubeColour.Blue));

        SpawnChoiceEvent choiceEvent = new SpawnChoiceEvent(player1.getUsername(), ((RespawnRequestEvent)requestEvent).getPowerUpNames()[0], ((RespawnRequestEvent)requestEvent).getPowerUpColours()[0]);
        choiceEvent.performAction(controller);
        Assert.assertEquals(1, player1.getPowerUps().size());
        Assert.assertEquals("Newton", player1.getPowerUps().get(0).getName());

        Assert.assertEquals(2, controller.getGameManager().getCurrentRound().getPhase());
    }
}
