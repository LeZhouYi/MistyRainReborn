package skily_leyu.mistyrain.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.common.core.book.Chapter;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ChapterButton extends Button {

    private Chapter chapter;
    public ChapterButton(int x, int y, int width, int height, Chapter chapter) {
        super(x, y, width, height, chapter.getName(), button->{
        }, null);
        this.chapter = chapter;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        ItemStack itemIcon = this.chapter.getItemstack();
        minecraft.getItemRenderer().renderGuiItem(itemIcon,this.x,this.y);
    }

}
