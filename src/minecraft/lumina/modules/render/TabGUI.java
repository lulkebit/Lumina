package lumina.modules.render;

import lumina.Client;
import lumina.events.Event;
import lumina.events.listeners.EventKey;
import lumina.events.listeners.EventRenderGUI;
import lumina.events.listeners.EventUpdate;
import lumina.modules.Module;
import lumina.util.settings.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class TabGUI extends Module {

    public int currentTab;
    public boolean expanded;

    public TabGUI() {
        super("TabGUI", Keyboard.KEY_NONE, Category.RENDER);
        toggled = true;
    }

    @Override
    public void onEvent(Event event){
        if(event instanceof EventRenderGUI){
            FontRenderer fr = mc.fontRendererObj;

            Gui.drawRect(5, 25.5, 62, 30 + Category.values().length * 15, 0x90000000);
            Gui.drawRect(7, 28 + currentTab * 16, 7 + 53, 28 + currentTab * 16 + 11, 0xff0090ff);

            int count = 0;
            for(Category category : Module.Category.values()){
                fr.drawStringWithShadow(category.name, 11, 30 + count*16, -1);

                count++;
            }

            if(expanded) {
                Category category = Module.Category.values()[currentTab];
                List<Module> modules = Client.getModulesByCategory(category);

                if(modules.size() == 0)
                    return;

                Gui.drawRect(62, 25.5, 62 + 60, 30 + modules.size() * 15, 0x90000000);
                Gui.drawRect(62, 28 + category.moduleIndex * 16, 7 + 53 + 60, 28 + category.moduleIndex * 16 + 11, 0xff0090ff);

                count = 0;
                for (Module module : modules) {
                    fr.drawStringWithShadow(module.name, 65, 30 + count * 16, -1);

                    // if(count == category.moduleIndex && module.expanded){
                        // Gui.drawRect(62 + 60, 25.5, 62 + 60 + 60, 30 + module.settings.size() * 15, 0x90000000);
                        // Gui.drawRect(62 + 60, 28 + module.index * 16, 7 + 53 + 60 + 60, 28 + module.index * 16 + 11, 0xff0090ff);
                        // int index = 0;
                        // for (Setting setting : module.settings) {
                            // fr.drawStringWithShadow(setting.name, 65 + 60, 30 + index * 16, -1);
                            // index++;
                        //}
                    // }

                    count++;
                }
            }
        }

        if(event instanceof EventKey){
            int key = ((EventKey)event).key;

            Category category = Module.Category.values()[currentTab];
            List<Module> modules = Client.getModulesByCategory(category);

            if(key == Keyboard.KEY_UP){
                if(expanded){
                    if(category.moduleIndex <= 0){
                        category.moduleIndex = modules.size() - 1;
                    } else
                        category.moduleIndex--;
                } else if(currentTab <= 0){
                    currentTab = Category.values().length - 1;
                } else
                    currentTab--;
            } else if(key == Keyboard.KEY_DOWN){
                if(expanded){
                    if(category.moduleIndex >= modules.size() - 1){
                        category.moduleIndex = 0;
                    } else
                        category.moduleIndex++;
                } else if(currentTab >= Category.values().length - 1){
                    currentTab = 0;
                } else
                    currentTab++;
            } else if(key == Keyboard.KEY_RIGHT){
                if(expanded){
                    Module module = modules.get(category.moduleIndex);
                    if(!module.name.equals("TabGUI"))
                        module.toggle();
                } else {
                    expanded = true;
                }
            } else if(key == Keyboard.KEY_LEFT){
                if(expanded && modules.get(category.moduleIndex).expanded){
                    modules.get(category.moduleIndex).expanded = false;
                }
                expanded = false;
            } else if(key == Keyboard.KEY_RETURN){
                if(expanded){
                    Module module = modules.get(category.moduleIndex);
                    if(!module.name.equals("TabGUI"))
                        module.toggle();
                } else {
                    expanded = true;
                }
            }
        }
    }

}
