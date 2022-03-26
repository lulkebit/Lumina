package lumina.modules.player;

import lumina.events.Event;
import lumina.events.listeners.EventUpdate;
import lumina.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", Keyboard.KEY_M, Category.PLAYER);
    }

    @Override
    public void onEvent(Event event){
        if(event instanceof EventUpdate){
            if(event.isPre()){
                if(mc.thePlayer.fallDistance > 2)
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
    }

}
