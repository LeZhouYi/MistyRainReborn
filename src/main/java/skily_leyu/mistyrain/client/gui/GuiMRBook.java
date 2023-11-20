package skily_leyu.mistyrain.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Level;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.core.RenderUtils;
import skily_leyu.mistyrain.common.core.book.Book;
import skily_leyu.mistyrain.common.core.book.Chapter;
import skily_leyu.mistyrain.common.core.book.Content;
import skily_leyu.mistyrain.common.core.book.PageStage;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class GuiMRBook extends Screen {

    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID, "textures/gui/gui_mr_book.png");
    private static final ResourceLocation PAGE_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID, "textures/gui/gui_mr_book_page.png");

    private final Book book; //书相关设置
    private PageStage pageStage; //当前页
    private List<PageStage> historiesPage; //历史翻页
    private int x;
    private int y;
    private ImageButton nextPageBtn; //下一页
    private ImageButton previousPageBtn; //上一页
    private ImageButton upperPageBtn; //返回上一级目录

    private List<Button> buttonChapters; //动态缓存目录按钮

    protected GuiMRBook(ITextComponent title, Book book) {
        super(title);
        this.book = book;
        this.pageStage = new PageStage();
        this.initHistories();
    }

    public void initHistories() {
        this.historiesPage = new ArrayList<>();
    }

    @Override
    protected void init() {
        updateXY();
        this.getMinecraft().keyboardHandler.setSendRepeatsToGui(true);
        this.previousPageBtn = new ImageButton(x + 89, y + 153, 16, 16, 0, 240, -16, BOOK_GUI_TEXTURES, button -> {
            if (this.pageStage.getPage() >= 2) {
                this.pageStage.addPage(-2);
                this.updateChapter();
            }
        });
        this.upperPageBtn = new ImageButton(x + 106, y + 153, 16, 16, 16, 240, -16, BOOK_GUI_TEXTURES, button -> {
            if (!this.pageStage.isRoot() && !this.historiesPage.isEmpty()) {
                this.pageStage = this.historiesPage.remove(this.historiesPage.size() - 1);
                this.updateChapter();
            }
        });
        this.nextPageBtn = new ImageButton(x + 123, y + 153, 16, 16, 32, 240, -16, BOOK_GUI_TEXTURES, button -> {
            if (this.book.hasNext(this.pageStage)) {
                this.pageStage.addPage(2);
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

    protected void updateRootChapter() {
        if (!this.pageStage.isChapter() || !this.pageStage.isRoot()) {
            return;//非法
        }
        List<Chapter> rootChapter = this.book.getRootChapter();//获取根目录
        if (rootChapter.isEmpty()) {
            return;
        }
        int page = this.pageStage.getPage(); //所在页数
        for (int i = 0; i < 16; i++) {
            //渲染右侧目录
            int chapterRight = page * 16 + i;
            if (chapterRight < rootChapter.size()) {
                //数量满足，添加按钮
                int tempX = i % 4;
                int tempY = i / 4;
                ButtonChapter buttonChapter = new ButtonChapter(x + 124 + tempX * 26, y + 2 + tempY * 34, 16, 16, rootChapter.get(i), button -> {
                    this.historiesPage.add(this.pageStage);
                    this.pageStage = new PageStage(true, false, 0, chapterRight);
                    this.updateChapter();
                });
                this.buttonChapters.add(buttonChapter);
                this.addButton(buttonChapter);
            }
        }
        if (page == 0) {
            return;//第一页为目录，不渲染按钮
        }
        for (int i = 0; i < 16; i++) {
            int chapterLeft = (page - 1) * 16 + i;
            if (chapterLeft < rootChapter.size()) {
                //数量满足，添加按钮
                int tempX = i % 4;
                int tempY = i / 4;
                ButtonChapter buttonChapter = new ButtonChapter(x + 12 + tempX * 26, y + 2 + tempY * 34, 16, 16, rootChapter.get(i), button -> {
                    this.historiesPage.add(this.pageStage);
                    this.pageStage = new PageStage(true, false, 0, chapterLeft);
                    this.updateChapter();
                });
                this.buttonChapters.add(buttonChapter);
                this.addButton(buttonChapter);
            }
        }
    }

    protected void updateChildChapter() {
        if (!this.pageStage.isChapter() || this.pageStage.isRoot()) {
            return; //非法
        }
        int index = this.pageStage.getIndex();
        List<Content> contents = this.book.getContents(index);
        if (contents.isEmpty()) {
            return;
        }
        MistyRain.getLogger().log(Level.DEBUG, "test");
        for (int i = 0; i < 8; i++) {
            int leftIndex = this.pageStage.getPage() * 8 + i;
            if (leftIndex < contents.size()) {
                ButtonContent buttonContent = new ButtonContent(x + 14, y + i * 17, 45, 16, contents.get(leftIndex), button -> {
                    this.historiesPage.add(this.pageStage);
                    this.pageStage = new PageStage(false, false, 0, leftIndex);
                    this.updateChapter();
                });
                this.buttonChapters.add(buttonContent);
                this.addButton(buttonContent);
            }
            int rightIndex = (this.pageStage.getPage() + 1) * 8 + i;
            if (rightIndex < contents.size()) {
                ButtonContent buttonContent = new ButtonContent(x + 126, y + i * 17, 45, 16, contents.get(leftIndex), button -> {
                    this.historiesPage.add(this.pageStage);
                    this.pageStage = new PageStage(false, false, 0, rightIndex);
                    this.updateChapter();
                });
                this.buttonChapters.add(buttonContent);
                this.addButton(buttonContent);
            }
        }
    }

    protected void updateChapter() {
        if (!this.buttonChapters.isEmpty()) {
            this.buttons.removeAll(this.buttonChapters);
            this.buttonChapters.clear();
        }
        this.updateRootChapter();
        this.updateChildChapter();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrixStack);
        updateXY();
        this.renderPaper(matrixStack);
        this.renderCover(matrixStack);
        this.renderContent(matrixStack);
        this.previousPageBtn.render(matrixStack, mouseX, mouseY, partialTicks);
        this.upperPageBtn.render(matrixStack, mouseX, mouseY, partialTicks);
        this.nextPageBtn.render(matrixStack, mouseX, mouseY, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void renderContent(MatrixStack matrixStack) {
        if (this.pageStage.isChapter()) {
            return;
        }
        int index = this.pageStage.getIndex();
        Content content = this.book.getContent(index);
        int page = this.pageStage.getPage();
        if (page == 0) {
            ItemStack itemStack = content.getItemStack();
            if (!itemStack.isEmpty()) {
                RenderSystem.scalef(2.0F,2.0F,2.0F);
                minecraft.getItemRenderer().renderGuiItem(itemStack, (this.x+76)/2, (this.y-4)/2);
                RenderSystem.scalef(0.5F,0.5F,0.5F);
            }
            this.font.draw(matrixStack, content.getName(), this.x + 12F, this.y + 4F, 0x473C26);
            this.font.draw(matrixStack, content.getProperty(), this.x + 12F, this.y + 16F, 0x473C26);
            if (content.getPage() > 0) {
                this.font.drawWordWrap(content.getText(0), this.x + 12, this.y + 28, 100, 0x473C26);
            }
        } else {
            if (content.getPage() > 0 && page < content.getPage()) {
                this.font.drawWordWrap(content.getText(page), this.x + 4, this.y + 4, 100, 0x473C26);
            }
        }
        if (content.getPage() > 0 && page + 1 < content.getPage()) {
            this.font.drawWordWrap(content.getText(page+1),this.x+124,this.y+4,100,0x473C26);
        }
    }

    /**
     * 渲染书页背景
     */
    public void renderPaper(MatrixStack matrixStack) {
        if (pageStage.isRoot() && pageStage.getPage() == 0) {
            this.getMinecraft().getTextureManager().bind(BOOK_GUI_TEXTURES);
        } else {
            this.getMinecraft().getTextureManager().bind(PAGE_GUI_TEXTURES);
        }
        this.blit(matrixStack, x, y - 10, 0, 0, 230, 160);
    }

    public void renderCover(MatrixStack matrixStack) {
        if (pageStage.isRoot() && pageStage.getPage() == 0) {
            float scale = 2.0F;
            matrixStack.pushPose();
            this.font.drawWordWrap(this.book.getDescription(), this.x + 12, this.y + 60, 100, 0x473C26);
            RenderUtils.drawRightString(matrixStack, this.font, this.book.getAuthor(), this.x + 106F, this.y + 78F, 0x473C26);
            matrixStack.scale(scale, scale, scale);
            RenderUtils.drawCenterString(matrixStack, this.font, this.book.getName(), (this.x + 60) / scale, (this.y + 20) / scale, 0x473C26);
            matrixStack.popPose();
        }
    }

    private void updateXY() {
        this.x = (this.width - 230) / 2;
        this.y = (this.height - 160) / 2;
    }

}
