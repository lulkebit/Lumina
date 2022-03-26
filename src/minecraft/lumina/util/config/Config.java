package lumina.util.config;

import lumina.Client;
import lumina.modules.Module;

import java.io.File;
import java.io.IOException;

public class Config {

    public File configFolder = new File("Lumina");
    public File modulesFolder = new File("Lumina/Modules");

    public Configuration config, configToSave = ConfigurationAPI.newConfiguration(new File("Lumina/Modules/ModuleConfiguration.lumina"));

    public void saveModuleConfig(){
        if(!configFolder.exists()){
            configFolder.mkdirs();
        }

        if(!modulesFolder.exists()){
            modulesFolder.mkdirs();
        }

        System.out.println("Saving Module Config...");

        for(Module module : Client.modules){
            configToSave.set(module.name.toLowerCase() + " key", module.getKey());
            configToSave.set(module.name.toLowerCase() + " enabled", module.isToggled());
        }

        try {
            configToSave.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadModuleConfig() {
        try {
            config = ConfigurationAPI.loadExistingConfiguration(new File("Lumina/Modules/ModuleConfiguration.lumina"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
