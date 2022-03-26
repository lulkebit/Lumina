package lumina.modules.render;

import lumina.events.Event;
import lumina.events.listeners.EventUpdate;
import lumina.modules.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", Keyboard.KEY_C, Category.RENDER);
    }

    @Override
    public void onEnable(){

    }

    @Override
    public void onDisable(){
        mc.gameSettings.gammaSetting = 1;
    }

    @Override
    public void onEvent(Event event){
        if(event instanceof EventUpdate){
            if(event.isPre()){
                mc.gameSettings.gammaSetting = 100;
            }
        }
    }

}
