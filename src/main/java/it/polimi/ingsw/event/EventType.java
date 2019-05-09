package it.polimi.ingsw.event;

public enum EventType {
    //
    ErrorEvent,
    //View_Controller_EventType
    ActionChoiceEvent,
    CardChoiceEvent,
    GameChoiceEvent,
    PlayerChoiceEvent,
    PositionChoiceEvent,
    StartGameEvent,

    //Model_View_EventType
    AmmoTileUpdateEvent,
    GameTrackUpdateEvent,
    PlayerBoardUpdateEvent,
    PositionUpdateEvent,
    WeaponUpdateEvent,

    //View_Select_EventType
    ActionRequestEvent,
    CardRequestEvent,
    PlayerRequestEvent,
    PositionRequestEvent;
}
