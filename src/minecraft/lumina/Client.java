package lumina;

import lumina.events.Event;
import lumina.modules.Module;
import lumina.modules.movement.*;
import lumina.modules.render.*;
import lumina.ui.HUD;
import lumina.util.config.Config;
import org.lwjgl.opengl.Display;

import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    public static String name = "Lumina", version = "1";
    public static Config config = new Config();
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public static HUD hud = new HUD();


    public static void startup(){
        System.out.println("Starting " + name + " - v" + version);
        Display.setTitle(name + " v" + version);

        config.loadModuleConfig();

        // Movement
        modules.add(new VanillaFly());
        modules.add(new Sprint());

        // Render
        modules.add(new Fullbright());
    }

    public static void shutdown(){
        System.out.println("Shutting down " + name);

        config.saveModuleConfig();
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
