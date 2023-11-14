package skily_leyu.mistyrain.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Level;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.core.RenderUtils;
import skily_leyu.mistyrain.common.core.book.Book;
import skily_leyu.mistyrain.common.core.book.Chapter;
import skily_leyu.mistyrain.common.core.book.PageStage;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class GuiMRBook extends Screen {

    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID, "textures/gui/gui_mr_book.png");
    private static final ResourceLocation PAGE_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID, "textures/gui/gui_mr_book_page.png");

    private final Book book;
    private PageStage pageStage;
    private int x;
    private int y;
    private ImageButton nextPageBtn;
    private ImageButton previousPageBtn;
    private ImageButton upperPageBtn;

    private List<ButtonChapter> buttonChapters;

    protected GuiMRBook(ITextComponent title, Book book) {
        super(title);
        this.book = book;
        this.pageStage = new PageStage();
    }

    public void setPageStage(PageStage pageStage) {
        this.pageStage = pageStage;
    }

    @Override
    protected void init() {
        updateXY();
        this.getMinecraft().keyboardHandler.setSendRepeatsToGui(true);
        this.previousPageBtn = new ImageButton(x + 89, y + 153, 16, 16, 0, 240, -16, BOOK_GUI_TEXTURES, button -> {
        });
        this.upperPageBtn = new ImageButton(x + 106, y + 153, 16, 16, 16, 240, -16, BOOK_GUI_TEXTURES, button -> {
        });
        this.nextPageBtn = new ImageButton(x + 123, y + 153, 16, 16, 32, 240, -16, BOOK_GUI_TEXTURES, button -> {
            if(this.book.hasNext(this.pageStage)){
                this.pageStage.addPage(2);
                MistyRain.getLogger().log(Level.DEBUG,this.pageStage.getPage());
                this.updateChapter();
            }
        });
        this.addButton(previousPageBtn);
        this.addButton(upperPageBtn);
        this.addButton(nextPageBtn);
        this.buttonChapters = new ArrayList<>();
        this.updateChapter();
        super.init();
    }

    protected  void updateRootChapter(){
        List<Chapter> rootChapter = this.book.getRootChapter();//获取根目录
        int page = pageStage.getPage(); //所在页数
        if(page<0){
            return;//非法
        }
        for(int i = 0;i<16;i++){
            //渲染右侧目录
            int chapterRight = page*16+i;
            if(chapterRight<rootChapter.size()){
                //数量满足，添加按钮
                int tempX = i%4;
                int tempY = i/4;
                ButtonChapter buttonChapter = new ButtonChapter(x+124+tempX*26,y+2+tempY*34,16,16,rootChapter.get(i),
                        button-> this.setPageStage(new PageStage(true,false,0, chapterRight)));
                this.buttonChapters.add(buttonChapter);
                this.addButton(buttonChapter);
            }
            if(page==0){
                continue;//第一页为目录，不渲染按钮
            }
            int chapterLeft = (page-1)*16+i;
            if(chapterLeft<rootChapter.size()){
                //数量满足，添加按钮
                int tempX = i%4;
                int tempY = i/4;
                ButtonChapter buttonChapter = new ButtonChapter(x+12+tempX*26,y+2+tempY*34,16,16,rootChapter.get(i),
                        button-> this.setPageStage(new PageStage(true,false,0, chapterLeft)));
                this.buttonChapters.add(buttonChapter);
                this.addButton(buttonChapter);
            }
        }
    }

    protected void updateChapter(){
        if(!this.buttonChapters.isEmpty()){
            this.buttons.removeAll(this.buttonChapters);
            this.buttonChapters.clear();
        }
        if(!pageStage.isChapter()){
            return;
        }
        if(pageStage.isRoot()){
            this.updateRootChapter();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        updateXY();
        this.renderPaper(matrixStack);
        this.renderCover(matrixStack);
        this.previousPageBtn.render(matrixStack, mouseX, mouseY, partialTicks);
        this.upperPageBtn.render(matrixStack, mouseX, mouseY, partialTicks);
        this.nextPageBtn.render(matrixStack, mouseX, mouseY, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * 渲染书页背景
     */
    public void renderPaper(MatrixStack matrixStack) {
        if (pageStage.isRoot()&&pageStage.getPage()==0) {
            this.getMinecraft().getTextureManager().bind(BOOK_GUI_TEXTURES);
        } else {
            this.getMinecraft().getTextureManager().bind(PAGE_GUI_TEXTURES);
        }
        this.blit(matrixStack, x, y - 10, 0, 0, 230, 160);
    }

    public void renderCover(MatrixStack matrixStack) {
        if (pageStage.isRoot()&&pageStage.getPage()==0) {
            float scale = 2.0F;
            matrixStack.pushPose();
            this.font.drawWordWrap(this.book.getDescription(),this.x+12,this.y+60,100,0x473C26);
            RenderUtils.drawRightString(matrixStack,this.font,this.book.getAuthor(),this.x+105F,this.y+78F,0x473C26);
            matrixStack.scale(scale, scale, scale);
            RenderUtils.drawCenterString(matrixStack,this.font,this.book.getName(),(this.x + 60) / scale, (this.y + 20) / scale,0x473C26);
            matrixStack.popPose();
        }
    }

    private void updateXY() {
        this.x = (this.width - 230) / 2;
        this.y = (this.height - 160) / 2;
    }

}
