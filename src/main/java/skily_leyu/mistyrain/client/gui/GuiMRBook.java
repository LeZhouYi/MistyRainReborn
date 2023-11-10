package skily_leyu.mistyrain.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.core.book.Book;
import skily_leyu.mistyrain.common.core.book.PageStage;

public class GuiMRBook extends Screen {

    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID,"textures/gui/gui_mr_book.png");
    private static final ResourceLocation PAGE_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID,"textures/gui/gui_mr_book_page.png");

    private final Book book;

    private PageStage pageStage;
    private int x,y;

    protected GuiMRBook(ITextComponent title,Book book) {
        super(title);
        this.book = book;
        this.pageStage = new PageStage();
        this.x = (this.width-230)/2;
        this.y = (this.height-156)/2;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.color4f(1.0F,1.0F,1.0F,1.0F);
        if(pageStage.isRoot()){
            this.getMinecraft().getTextureManager().bind(BOOK_GUI_TEXTURES);
        }else {
            this.getMinecraft().getTextureManager().bind(PAGE_GUI_TEXTURES);
        }
        this.blit(matrixStack,x,y-10,0,0,230,150);
    }
}
