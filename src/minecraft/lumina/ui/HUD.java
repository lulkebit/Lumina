package lumina.ui;

import lumina.Client;
import lumina.events.listeners.EventRenderGUI;
import lumina.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.Comparator;

public class HUD {

    public Minecraft mc = Minecraft.getMinecraft();


    public void draw(){
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        Client.modules.sort(Comparator.comparingInt(module ->
                fr.getStringWidth(((Module)module).name))
                .reversed()
        );

        GlStateManager.pushMatrix();
        GlStateManager.translate(4, 4, 0);
        GlStateManager.scale(2, 2, 1);
        GlStateManager.translate(-4, -4, 0);
        //fr.drawStringWithShadow(Client.name, 4, 4, -1);
        mc.getTextureManager().bindTexture(new ResourceLocation("lumina/logo.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(4, 4, 0, 0, 50, 8, 50, 8);
        GlStateManager.popMatrix();

        int count = 0;
        for(Module module : Client.modules){
            if(!module.isToggled() || module.name.equals("TabGUI"))
                continue;

            double offset = count*(fr.FONT_HEIGHT + 6);

            Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 10, offset, sr.getScaledWidth() - fr.getStringWidth(module.name) - 8, 6 + fr.FONT_HEIGHT + offset, 0xff0090ff);
            Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 8, offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, 0x90000000);
            fr.drawStringWithShadow(module.name, sr.getScaledWidth() - fr.getStringWidth(module.name) - 4, 4 + offset, -1);

            count++;
        }

        Client.onEvent(new EventRenderGUI());
    }
}
