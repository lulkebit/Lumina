package lumina;

import lumina.events.Event;
import lumina.events.listeners.EventKey;
import lumina.modules.Module;
import lumina.modules.combat.*;
import lumina.modules.movement.*;
import lumina.modules.player.*;
import lumina.modules.render.*;
import lumina.ui.HUD;
import lumina.util.config.Config;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;
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
        modules.add(new TabGUI());
        modules.add(new Fullbright());

        // Player
        modules.add(new NoFall());

        // Combat
        // modules.add(new Killaura());
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
        Client.onEvent(new EventKey(key));

        for(Module module : modules){
            if(module.getKey() == key){
                module.toggle();
            }
        }
    }

    public static List<Module> getModulesByCategory(Module.Category category){
        List<Module> modules = new ArrayList<Module>();

        for(Module module : Client.modules){
            if(module.category == category)
                modules.add(module);
        }

        return modules;
    }

}
