package skily_leyu.mistyrain.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.core.RenderUtils;
import skily_leyu.mistyrain.common.core.book.Chapter;
import skily_leyu.mistyrain.common.core.book.Content;

import java.util.ArrayList;
import java.util.List;

public class ButtonContent extends Button {

    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID, "textures/gui/gui_mr_book.png");
    private final Content content;

    public ButtonContent(int x, int y, int width, int height, Content content,Button.IPressable pressable) {
        super(x, y, width, height, content.getName(), pressable);
        this.content = content;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        Minecraft minecraft = Minecraft.getInstance();
//        ItemStack itemIcon = this.chapter.getItemStack();
//        minecraft.getItemRenderer().renderGuiItem(itemIcon,this.x,this.y);
//        if(this.isHovered()){
//            minecraft.getTextureManager().bind(BOOK_GUI_TEXTURES);
//            this.blit(matrixStack,this.x-3,this.y-2,235,235,21,21);
//            List<TranslationTextComponent> tooltip = new ArrayList<>();
//            tooltip.add(this.chapter.getDescription());
//            if(minecraft.screen!=null){
//                GuiUtils.drawHoveringText(matrixStack,tooltip, this.x+8, this.y-2, minecraft.screen.width,  minecraft.screen.height, 100, minecraft.font);
//            }
//        }
//        RenderUtils.drawCenterString(matrixStack,minecraft.font,chapter.getName(),this.x+9F,this.y+20F,0x473C26);
    }

}
