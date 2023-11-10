package skily_leyu.mistyrain.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.data.MRSetting;

public class MRGuis{

    public MRGuis(){
        Minecraft.getInstance().setScreen(new GuiMRBook(new TranslationTextComponent(MistyRain.MOD_ID+".test"),MRSetting.getHerbalsBook()));
    }

}
