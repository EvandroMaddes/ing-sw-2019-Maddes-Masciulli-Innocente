package it.polimi.ingsw.View;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.model_view_event.PositionUpdateEvent;
import it.polimi.ingsw.event.view_controller_event.CharacterChoiceEvent;

import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ViewTest {

    VirtualView virtualView;
    Controller controller;
    Event eventTest1;
    Event eventTest2;
    Event eventTest3;




    @Before
    public void setUp(){
        virtualView = new VirtualView("Evandro");
        controller = new Controller();
        eventTest1 = new CharacterChoiceEvent("Evandro",Character.BANSHEE);
        eventTest2 = new CharacterChoiceEvent("Giovanni",Character.SPROG);
        eventTest3 = new PositionUpdateEvent("Evandro",1,1);
        controller.createGameManager(1);

    }

    /**
     * Testing on view Observable quality
     */
/*    @Test
    public void viewControllerTest(){
        virtualView.addObserver(controller);

        virtualView.toController(eventTest1);
        Assert.assertEquals("Evandro",controller.getGameManager().getModel().getPlayers().get(0).getUsername());
        Assert.assertTrue(controller.getGameManager().getModel().getPlayers().get(0).isFirstPlayer());


        virtualView.toController(eventTest2);
        Assert.assertEquals("Giovanni",controller.getGameManager().getModel().getPlayers().get(1).getUsername());


    }

    /**
     * Testing on view Observer quality
     */
/*    @Test
    public void modelViewTest(){

        controller.getGameManager().getModel().addObserver(virtualView);
        controller.getGameManager().getModel().notifyObservers(eventTest3);

        Assert.assertEquals(virtualView.getToRemoteView(),eventTest3);

    }
    */
}
