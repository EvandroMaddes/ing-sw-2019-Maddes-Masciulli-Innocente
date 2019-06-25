package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.*;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.weapons.LockRifle;
import it.polimi.ingsw.model.game_components.cards.weapons.Railgun;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class ActionManagerTest {
    private HashMap<String, VirtualView> hashMap;
    private Controller controller;
    private Player player1;
    private Player player2;
    private Player player3;
    private Square[][] map;
    private RoundManager roundManager;

    @Before
    public void setUp(){
        hashMap = new HashMap<>();
        hashMap.put("Federico", new VirtualView("Federico"));
        hashMap.put("Francesco", new VirtualView("Francesco"));
        hashMap.put("Evandro", new VirtualView("Evandro"));
        controller = new Controller(hashMap, 0);
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.DOZER);
        player3 = new Player("Evandro", Character.D_STRUCT_OR);
        controller.getGameManager().getModel().getPlayers().add(player1);
        controller.getGameManager().getModel().getPlayers().add(player2);
        controller.getGameManager().getModel().getPlayers().add(player3);
        map = controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix();
        controller.getGameManager().setCurrentRound(new RoundManager(controller, player1));
        roundManager = controller.getGameManager().getCurrentRound();
        controller.getGameManager().setPlayerTurn(0);
        player1.setPosition(map[0][0]);
        SetUpObserverObservable.connect(controller.getGameManager().getModel().getPlayers(), controller.getUsersVirtualView(), controller.getGameManager().getModel());
    }

    @Test
    public void moveTest(){
        roundManager.manageRound();
        Event requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[0]);
        Assert.assertTrue(((ActionRequestEvent)requestMessage).getUsableActions()[1]);
        Assert.assertFalse(((ActionRequestEvent)requestMessage).getUsableActions()[2]);

        ViewControllerEvent choiceMessage = new ActionChoiceEvent(player1.getUsername(), 1);
        choiceMessage.performAction(controller);
        requestMessage = hashMap.get(player1.getUsername()).getToRemoteView();
        Assert.assertEquals(9, ((PositionMoveRequestEvent)requestMessage).getPossibleSquareX().length);
        int[] expectedX = new int[]{0,0,0,0,1,1,1,2,2};
        int[] expectedY = new int[]{0,1,2,3,0,1,2,0,1};
        for (int i = 0; i < 9; i++) {
            boolean check = false;
            for (int j = 0; j < 9; j++) {
                if (expectedX[i] == ((PositionMoveRequestEvent)requestMessage).getPossibleSquareX()[j] && expectedY[i] == ((PositionMoveRequestEvent)requestMessage).getPossibleSquareY()[j])
                    check = true;
            }
            Assert.assertTrue(check);
        }

        choiceMessage = new MoveChoiceEvent(player1.getUsername(), 2, 0);
        choiceMessage.performAction(controller);
        Assert.assertEquals(map[2][0], player1.getPosition());
        Assert.assertEquals(4, roundManager.getPhase());
    }


}
