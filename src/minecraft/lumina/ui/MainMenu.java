package lumina.ui;

import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class MainMenu extends GuiScreen {

    public MainMenu(){

    }

    @Override
    public void initGui(){

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        mc.getTextureManager().bindTexture(new ResourceLocation("lumina/background.jpg"));
        this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        this.drawGradientRect(0, height - 100, width, height, 0x00000000, 0xff000000);

        String[] buttons = { "Singleplayer", "Multiplayer", "Settings", "Quit" };

        int count = 0;
        for(String name : buttons){
            float x = (width/buttons.length) * count + (width/buttons.length)/2 + 8 - mc.fontRendererObj.getStringWidth(name)/2;
            float y = height - 20;

            boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;

            this.drawCenteredString(mc.fontRendererObj, name, (width/buttons.length) * count + (width/buttons.length)/2 + 8, height - 20, hovered ? 0xff0090ff : -1);
            count++;
        }

        mc.getTextureManager().bindTexture(new ResourceLocation("lumina/logo.jpg"));
        this.drawModalRectWithCustomSizedTexture(width/2 - 110, height/2 - 30, 0, 0, 206, 36, 206, 36);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button){
        String[] buttons = { "Singleplayer", "Multiplayer", "Settings", "Quit" };

        int count = 0;
        for(String name : buttons){
            float x = (width/buttons.length) * count + (width/buttons.length)/2 + 8 - mc.fontRendererObj.getStringWidth(name)/2;
            float y = height - 20;

            if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT){
                switch (name){
                    case "Singleplayer":
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;

                    case "Multiplayer":
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;

                    case "Settings":
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;

                    case "Quit":
                        mc.shutdown();
                        break;
                }
            }

            count++;
        }
    }

    @Override
    public void onGuiClosed(){

    }
}
