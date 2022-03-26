package lumina.events.listeners;

import lumina.events.Event;

public class EventKey extends Event<EventKey> {

    public int key;

    public EventKey(int key){
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
