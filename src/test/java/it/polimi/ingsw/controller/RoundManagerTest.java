package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.ActionRequestEvent;
import it.polimi.ingsw.event.view_controller_event.SkipActionChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.power_ups.TargetingScope;
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
    public void setUp(){
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        controller = new Controller(hashMap, 3);
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.DOZER);
        controller.getGameManager().getModel().getPlayers().add(player1);
        controller.getGameManager().getModel().getPlayers().add(player2);
        map = controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix();
    }

    @Test
    public void skipActionTest(){
        controller.getGameManager().setCurrentRound(new RoundManager(controller, player1));
        RoundManager roundManager = controller.getGameManager().getCurrentRound();
        controller.getGameManager().setPlayerTurn(0);
        player1.addPowerUp(new TargetingScope(CubeColour.Blue));
        player1.setPosition(map[0][0]);
        roundManager.manageRound();
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[1]);
        Assert.assertFalse(((ActionRequestEvent)requestMessage).getUsableActions()[2]);

        ViewControllerEvent choiceEvent = new SkipActionChoiceEvent(player1.getUsername());
        choiceEvent.performAction(controller);
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[1]);
        Assert.assertFalse(((ActionRequestEvent)requestMessage).getUsableActions()[2]);

        choiceEvent = new SkipActionChoiceEvent(player1.getUsername());
        choiceEvent.performAction(controller);

        roundManager = controller.getGameManager().getCurrentRound();
        Assert.assertEquals(player2, roundManager.getCurrentPlayer());
    }
}

