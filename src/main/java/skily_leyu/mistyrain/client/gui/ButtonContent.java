package skily_leyu.mistyrain.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.core.book.Content;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class ButtonContent extends Button {

    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID, "textures/gui/gui_mr_book.png");
    private final Content content;

    public ButtonContent(int x, int y, int width, int height, Content content, Button.IPressable pressable) {
        super(x, y, width, height, content.getName(), pressable);
        this.content = content;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        ItemStack itemIcon = this.content.getItemStack();
        float scale = 0.8F;
        float rescale = 1.25F;
        RenderSystem.enableDepthTest();
        RenderSystem.scalef(scale, scale, scale);
        minecraft.getItemRenderer().renderGuiItem(itemIcon, (int) (this.x / scale), (int) (this.y / scale));
        if (this.isHovered()) {
            minecraft.getTextureManager().bind(BOOK_GUI_TEXTURES);
            this.blit(matrixStack, (int) ((this.x - 3) / scale), (int) ((this.y - 2) / scale), 235, 235, 21, 21);
        }
        RenderSystem.scalef(rescale, rescale, rescale);
        if (this.isHovered()) {
            List<TranslationTextComponent> tooltip = new ArrayList<>();
            tooltip.add(this.content.getDescription());
            if (minecraft.screen != null) {
                GuiUtils.drawHoveringText(matrixStack, tooltip, this.x + 8, this.y - 2, minecraft.screen.width, minecraft.screen.height, 100, minecraft.font);
            }
        }
        minecraft.font.draw(matrixStack, this.content.getName(), this.x + 16F, this.y + 3F, 0x473C26);
    }

}
