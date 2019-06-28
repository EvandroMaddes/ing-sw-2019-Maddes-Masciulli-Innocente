package it.polimi.ingsw.event.view_server_event;

public class NewGameChoiceEvent extends ViewServerEvent {

    public NewGameChoiceEvent(String user) {
        super(user, true);
    }

    @Override
    public String performAction() {
        return getUser();
    }
}
