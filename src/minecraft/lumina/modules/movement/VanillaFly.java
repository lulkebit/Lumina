package lumina.modules.movement;

import lumina.events.Event;
import lumina.events.listeners.EventUpdate;
import lumina.modules.Module;
import org.lwjgl.input.Keyboard;

public class VanillaFly extends Module {

    public VanillaFly() {
        super("VanillaFly", Keyboard.KEY_F, Category.MOVEMENT);
    }

    @Override
    public void onEnable(){
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.capabilities.allowFlying = true;
    }

    @Override
    public void onDisable(){
        mc.thePlayer.capabilities.isFlying = false;
        mc.thePlayer.capabilities.allowFlying = false;
    }

    @Override
    public void onEvent(Event event){
        if(event instanceof EventUpdate){
            if(event.isPre()){

            }
        }
    }

}
