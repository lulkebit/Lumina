package lumina.modules.movement;

import lumina.events.Event;
import lumina.events.listeners.EventUpdate;
import lumina.modules.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Keyboard.KEY_N, Category.MOVEMENT);
    }

    @Override
    public void onEnable(){

    }

    @Override
    public void onDisable(){
        mc.thePlayer.setSprinting(false);
    }

    @Override
    public void onEvent(Event event){
        if(event instanceof EventUpdate){
            if(event.isPre()){
                mc.thePlayer.setSprinting(true);
            }
        }
    }

}
