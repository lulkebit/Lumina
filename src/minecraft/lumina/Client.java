package lumina;

import lumina.events.Event;
import lumina.modules.Module;
import lumina.modules.movement.Sprint;
import lumina.modules.movement.VanillaFly;
import lumina.ui.HUD;
import org.lwjgl.opengl.Display;

import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    public static String name = "Lumina", version = "1";
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public static HUD hud = new HUD();

    public static void startup(){
        System.out.println("Starting " + name + " - v" + version);
        Display.setTitle(name + " v" + version);

        modules.add(new VanillaFly());
        modules.add(new Sprint());
    }

    public static void onEvent(Event event){
        for(Module module : modules){
            if(!module.isToggled())
                continue;

            module.onEvent(event);
        }
    }

    public static void keyPress(int key){
        for(Module module : modules){
            if(module.getKey() == key){
                module.toggle();
            }
        }
    }

}
