package lumina.ui;

import lumina.Client;
import lumina.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.util.Collections;
import java.util.Comparator;

public class HUD {

    public Minecraft mc = Minecraft.getMinecraft();

    public static class ModuleComparator implements Comparator<Module> {

        @Override
        public int compare(Module o1, Module o2) {
            if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(o1.name) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(o2.name)){
                return -1;
            } else if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(o1.name) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(o2.name)){
                return 1;
            }
            return 0;
        }
    }

    public void draw(){
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        Collections.sort(Client.modules, new ModuleComparator());

        GlStateManager.translate(4, 4, 0);
        GlStateManager.scale(2, 2, 1);
        GlStateManager.translate(-4, -4, 0);
        fr.drawStringWithShadow(Client.name, 4, 4, -1);
        GlStateManager.translate(4, 4, 0);
        GlStateManager.scale(0.5, 0.5, 1);
        GlStateManager.translate(-4, -4, 0);

        int count = 0;
        for(Module module : Client.modules){
            if(!module.isToggled())
                continue;

            double offset = count*(fr.FONT_HEIGHT + 6);

            Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 10, offset, sr.getScaledWidth() - fr.getStringWidth(module.name) - 8, 6 + fr.FONT_HEIGHT + offset, 0xff0090ff);
            Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 8, offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, 0x90000000);
            fr.drawStringWithShadow(module.name, sr.getScaledWidth() - fr.getStringWidth(module.name) - 4, 4 + offset, -1);

            count++;
        }
    }

}
