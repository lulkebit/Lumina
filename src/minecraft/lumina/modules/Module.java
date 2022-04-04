package lumina.modules;

import lumina.Client;
import lumina.events.Event;
import lumina.util.settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Module {

    public String name;
    public boolean toggled;
    public int key;
    public Category category;
    public Minecraft mc = Minecraft.getMinecraft();

    public boolean expanded;
    public int index;
    public List<Setting> settings = new ArrayList<Setting>();

    public Module(String name, int key, Category category){
        this.name = name;
        this.category = category;

        try {
            this.key = (int) Client.config.config.get(name.toLowerCase() + " key");
            this.toggled = (boolean) Client.config.config.get(name.toLowerCase() + " enabled");
        } catch (NullPointerException e) {
            this.key = key;
            this.toggled = false;
        }
    }

    public void addSettings(Setting... settings){
        this.settings.addAll(Arrays.asList(settings));
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
        COMBAT("Combat"),
        MOVEMENT("Movement"),
        PLAYER("Player"),
        RENDER("Render");

        public String name;
        public int moduleIndex;

        Category(String name){
            this.name = name;
        }
    }
}
