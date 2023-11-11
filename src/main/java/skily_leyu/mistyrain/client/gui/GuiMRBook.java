package skily_leyu.mistyrain.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.core.book.Book;
import skily_leyu.mistyrain.common.core.book.PageStage;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class GuiMRBook extends Screen {

    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID,"textures/gui/gui_mr_book.png");
    private static final ResourceLocation PAGE_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID,"textures/gui/gui_mr_book_page.png");

    private final Book book;
    private PageStage pageStage;
    private int x;
    private int y;
    private ImageButton nextPageBtn;
    private ImageButton previousPageBtn;
    private ImageButton upperPageBtn;

    protected GuiMRBook(ITextComponent title, Book book) {
        super(title);
        this.book = book;
        this.pageStage = new PageStage();
    }

    @Override
    protected void init() {
        updateXY();
        this.getMinecraft().keyboardHandler.setSendRepeatsToGui(true);
        this.previousPageBtn = new ImageButton(x+89,y+153,16,16,0,240,-16,BOOK_GUI_TEXTURES,button->{});
        this.upperPageBtn = new ImageButton(x+106,y+153,16,16,16,240,-16,BOOK_GUI_TEXTURES,button->{});
        this.nextPageBtn = new ImageButton(x+123,y+153,16,16,32,240,-16,BOOK_GUI_TEXTURES,button->{});
        this.addButton(previousPageBtn);
        this.addButton(upperPageBtn);
        this.addButton(nextPageBtn);
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F,1.0F,1.0F,1.0F);
        updateXY();
        this.renderPaper(matrixStack);
        this.renderCover(matrixStack);
        this.previousPageBtn.render(matrixStack,mouseX,mouseY,partialTicks);
        this.upperPageBtn.render(matrixStack,mouseX,mouseY,partialTicks);
        this.nextPageBtn.render(matrixStack,mouseX,mouseY,partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * 渲染书页背景
     */
    public void renderPaper(MatrixStack matrixStack){
        if(pageStage.isRoot()){
            this.getMinecraft().getTextureManager().bind(BOOK_GUI_TEXTURES);
        }else {
            this.getMinecraft().getTextureManager().bind(PAGE_GUI_TEXTURES);
        }
        this.blit(matrixStack,x,y-10,0,0,230,160);
    }

    public void renderCover(MatrixStack matrixStack){
        if(pageStage.isRoot()){
            TranslationTextComponent name = this.book.getName();
            float scale = 2.0F;
            matrixStack.pushPose();
            matrixStack.scale(scale,scale,scale);
            this.font.draw(matrixStack,name,(this.x+20)/scale,(this.y+20)/scale,0x473C26);
            matrixStack.popPose();
        }
    }

    private void updateXY(){
        this.x = (this.width-230)/2;
        this.y = (this.height-160)/2;
    }

}
