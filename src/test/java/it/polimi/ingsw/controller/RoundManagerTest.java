package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controllerviewevent.ActionRequestEvent;
import it.polimi.ingsw.event.viewcontrollerevent.SkipActionChoiceEvent;
import it.polimi.ingsw.event.viewcontrollerevent.ViewControllerEvent;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.powerups.TargetingScope;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class RoundManagerTest {
    private HashMap<String, VirtualView> hashMap;
    private Controller controller;
    private Player player1;
    private Player player2;
    private Square[][] map;

    @Before
    public void setUp() {
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        controller = new Controller(hashMap, 0);
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.DOZER);
        controller.getGameManager().getModel().getPlayers().add(player1);
        controller.getGameManager().getModel().getPlayers().add(player2);
        map = controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix();
    }

    /**
     * Check that a SkipActionRequestEvent correctly move the player to the next phase of the round
     */
    @Test
    public void skipActionTest() {

        controller.getGameManager().setCurrentRound(new RoundManager(controller, player1));
        RoundManager roundManager = controller.getGameManager().getCurrentRound();
        controller.getGameManager().setPlayerTurn(0);
        player1.addPowerUp(new TargetingScope(CubeColour.Blue));
        player1.setPosition(map[0][0]);
        roundManager.manageRound();
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[1]);
        Assert.assertFalse(((ActionRequestEvent) requestMessage).getUsableActions()[2]);

        ViewControllerEvent choiceEvent = new SkipActionChoiceEvent(player1.getUsername());
        choiceEvent.performAction(controller);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent) requestMessage).getUsableActions()[1]);
        Assert.assertFalse(((ActionRequestEvent) requestMessage).getUsableActions()[2]);

        choiceEvent = new SkipActionChoiceEvent(player1.getUsername());
        choiceEvent.performAction(controller);

        roundManager = controller.getGameManager().getCurrentRound();
        Assert.assertEquals(player2, roundManager.getCurrentPlayer());
    }

    /**
     * Check that the refillMap method of the GameManager correctly refill all and only the empty squares of the map
     */
    @Test
    public void mapRefillTest() {
        controller.getGameManager().newRound();
        for (SpawnSquare s : controller.getGameManager().getModel().getGameboard().getMap().getSpawnSquares()) {
            Assert.assertEquals(3, s.getWeapons().size());
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (!controller.getGameManager().getModel().getGameboard().getMap().getSpawnSquares().contains(map[i][j]))
                    Assert.assertTrue(((BasicSquare) map[i][j]).checkAmmo());
            }
        }
    }
}

