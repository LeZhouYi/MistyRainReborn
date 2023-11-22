package skily_leyu.mistyrain.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.core.book.BookProperty;
import skily_leyu.mistyrain.data.MRData;
import skily_leyu.mistyrain.data.MRSetting;

public class MRGuis{

    public MRGuis(){
        Minecraft.getInstance().setScreen(
                new GuiMRBook(new TranslationTextComponent(MistyRain.MOD_ID+".test"),
                MRSetting.getHerbalsBook(),
                new BookProperty().setColor(MRData.BOOK_COLOR).setTextWidth(MRData.TEXT_WIDTH)
                        .setTextHeight(MRData.TEXT_HEIGHT).setTextOffset(MRData.TEXT_OFFSET)
                        .setLineHeight(MRData.LINE_HEIGHT)
        ));
    }

}
