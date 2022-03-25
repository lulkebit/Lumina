package lumina.modules;

import lumina.events.Event;
import net.minecraft.client.Minecraft;

public class Module {

    public String name;
    public boolean toggled;
    public int key;
    public Category category;
    public Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, int key, Category category){
        this.name = name;
        this.key = key;
        this.category = category;
    }

    public boolean isToggled() {
        return toggled;
    }

    public int getKey() {
        return key;
    }

    public void onEvent(Event event){

    }

    public void toggle() {
        toggled = !toggled;
        if(toggled){
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable(){

    }

    public void onDisable(){

    }

    public enum Category {
        COMBAT,
        MOVEMENT,
        PLAYER,
        RENDER;
    }
}
