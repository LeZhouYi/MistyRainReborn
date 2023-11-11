package skily_leyu.mistyrain.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class MRFontRender extends FontRenderer {
    public MRFontRender(Function<ResourceLocation, Font> p_i232249_1_) {
        super(p_i232249_1_);
    }

}
