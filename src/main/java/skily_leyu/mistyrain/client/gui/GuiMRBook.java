package skily_leyu.mistyrain.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.core.RenderUtils;
import skily_leyu.mistyrain.common.core.book.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class GuiMRBook extends Screen {

    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID, "textures/gui/gui_mr_book.png");
    private static final ResourceLocation PAGE_GUI_TEXTURES = new ResourceLocation(MistyRain.MOD_ID, "textures/gui/gui_mr_book_page.png");

    private final Book book; //书相关设置
    private PageStage pageStage; //当前页
    private final List<PageStage> historiesPage; //历史翻页
    private int x;
    private int y;
    private ImageButton nextPageBtn; //下一页
    private ImageButton previousPageBtn; //上一页
    private ImageButton upperPageBtn; //返回上一级目录
    private List<Button> buttonChapters; //动态缓存目录按钮
    private final BookProperty bookProperty;

    protected GuiMRBook(ITextComponent title, Book book, BookProperty bookProperty) {
        super(title);
        this.book = book;
        this.pageStage = new PageStage();
        this.historiesPage = new ArrayList<>();
        this.bookProperty = bookProperty;
    }

    @Override
    protected void init() {
        updateXY();
        this.getMinecraft().keyboardHandler.setSendRepeatsToGui(true);
        this.previousPageBtn = this.addButton(
                new ImageButton(x + 89, y + 153, 16, 16, 0, 240, -16, BOOK_GUI_TEXTURES, button -> {
                    if (this.pageStage.getPage() >= 2) {
                        this.pageStage.addPage(-2);
                        this.updateChapter();
                    }
                })
        );
        this.upperPageBtn = this.addButton(
                new ImageButton(x + 106, y + 153, 16, 16, 16, 240, -16, BOOK_GUI_TEXTURES, button -> {
                    if (!this.pageStage.isRoot() && !this.historiesPage.isEmpty()) {
                        this.pageStage = this.historiesPage.remove(this.historiesPage.size() - 1);
                        this.updateChapter();
                    }
                })
        );
        this.nextPageBtn = this.addButton(new ImageButton(x + 123, y + 153, 16, 16, 32, 240, -16, BOOK_GUI_TEXTURES, button -> {
            if (this.book.hasNext(this.pageStage, this.font, bookProperty)) {
                this.pageStage.addPage(2);
                this.updateChapter();
            }
        }));
        this.updateChapter();
    }

    /**
     * 更新根目录
     */
    protected void updateRootChapter() {
        if (!this.pageStage.isRoot()) {
            return;//非法
        }
        List<Chapter> rootChapter = this.book.getRootChapter();//获取根目录
        if (rootChapter.isEmpty()) {
            return;
        }
        int page = this.pageStage.getPage(); //所在页数
        for (int tePage : new int[]{page, page + 1}) {
            int xOffset = (tePage % 2 == 0) ? 12 : 124;
            if (tePage > 0) {
                for (int i = 0; i < 16; i++) {
                    int chapterIndex = (tePage - 1) * 16 + i;
                    if (chapterIndex < rootChapter.size()) {
                        //数量满足，添加按钮
                        int tempX = i % 4;
                        int tempY = i / 4;
                        ButtonChapter buttonChapter = this.addButton(
                                new ButtonChapter(x + xOffset + tempX * 26, y + 2 + tempY * 34, 16, 16, rootChapter.get(i), button -> {
                                    this.historiesPage.add(this.pageStage);
                                    this.pageStage = new PageStage(true, false, 0, chapterIndex);
                                    this.updateChapter();
                                })
                        );
                        this.buttonChapters.add(buttonChapter);
                    }
                }
            }
        }
    }

    /**
     * 更新子目录
     */
    protected void updateChildChapter() {
        if (this.pageStage.isRoot()) {
            return; //非法
        }
        int index = this.pageStage.getIndex();
        List<Integer> indexSet = this.book.getContentIndexSet(index);
        if (indexSet.isEmpty()) {
            return;
        }
        int page = this.pageStage.getPage();
        for (int tePage : new int[]{page, page + 1}) {
            int xOffset = (tePage % 2 == 0) ? 14 : 126;
            for (int i = 0; i < 8; i++) {
                int teIndex = tePage * 8 + i;
                if (teIndex < indexSet.size()) {
                    int contentIndex = indexSet.get(teIndex);
                    Content content = this.book.getContent(contentIndex);
                    if(content==null){
                        continue;
                    }
                    ButtonContent buttonContent = this.addButton(
                            new ButtonContent(x + xOffset, y + i * 17, 45, 16, content, button -> {
                                this.historiesPage.add(this.pageStage);
                                this.pageStage = new PageStage(false, false, 0, contentIndex);
                                this.updateChapter();
                            })
                    );
                    this.buttonChapters.add(buttonContent);
                }
            }
        }
    }

    /**
     * 更新/清除/添加目录相关控件
     */
    protected void updateChapter() {
        if (this.buttonChapters == null) {
            this.buttonChapters = new ArrayList<>();
        }
        if (!this.buttonChapters.isEmpty()) {
            this.buttons.removeAll(this.buttonChapters);
            for (Button button : this.buttonChapters) {
                button.active = false;
            }
            this.buttonChapters.clear();
        }
        if (this.pageStage.isChapter()) {
            if (this.pageStage.isRoot()) {
                this.updateRootChapter();
            } else {
                this.updateChildChapter();
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!RenderSystem.isOnGameThread()||this.getMinecraft()==null) {
            return;
        }
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrixStack);
        updateXY();
        this.renderPaper(matrixStack);
        this.renderCover(matrixStack);
        if (!this.pageStage.isChapter()) {
            this.renderHeader(matrixStack);
            this.renderContent(matrixStack);
        }
        this.previousPageBtn.render(matrixStack, mouseX, mouseY, partialTicks);
        this.upperPageBtn.render(matrixStack, mouseX, mouseY, partialTicks);
        this.nextPageBtn.render(matrixStack, mouseX, mouseY, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * 渲染书页内容的标题部分
     */
    public void renderHeader(MatrixStack matrixStack) {
        int index = this.pageStage.getIndex();
        Content content = this.book.getContent(index);
        if (this.pageStage.getPage() == 0&&content!=null) {
            ItemStack itemStack = content.getItemStack();
            ItemRenderer itemRenderer = Objects.requireNonNull(this.minecraft).getItemRenderer();
            if (!itemStack.isEmpty()) {
                RenderSystem.scalef(1.6F, 1.6F, 1.6F);
                itemRenderer.renderGuiItem(itemStack, (int) ((this.x + 76) / 1.6), (int) ((this.y - 2) / 1.6));
                RenderSystem.scalef(0.625F, 0.625F, 0.625F);
            }
            this.font.draw(matrixStack, content.getName(), this.x + 12F, this.y + 4F, this.bookProperty.getColor());
            this.font.draw(matrixStack, content.getProperty(), this.x + 12F, this.y + 14F, this.bookProperty.getColor());

        }
    }

    /**
     * 渲染书页内容
     */
    public void renderContent(MatrixStack matrixStack) {
        Content content = this.book.getContent(this.pageStage.getIndex());
        int page = this.pageStage.getPage();
        List<IReorderingProcessor> texts = this.font.split(Objects.requireNonNull(content).getText(), this.bookProperty.getTextWidth());
        for (int tePage : new int[]{page, page + 1}) {
            int xOffset = (tePage % 2 == 0) ? 12 : 124;
            int yOffset = (tePage == 0) ? this.bookProperty.getTextOffset() : 4;
            int unit = this.bookProperty.getUnit(tePage);
            int lineIndexStart = this.bookProperty.getLineIndex(tePage);
            for (int i = 0; i < unit; i++) {
                int lineIndex = lineIndexStart + i;
                if (lineIndex < texts.size()) {
                    this.font.draw(matrixStack, texts.get(lineIndex),
                            this.x + (float) xOffset,
                            this.y + (float) yOffset + i * bookProperty.getLineHeight(),
                            this.bookProperty.getColor());
                }
            }
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

    /**
     * 渲染封面的内容
     */
    public void renderCover(MatrixStack matrixStack) {
        if (pageStage.isRoot() && pageStage.getPage() == 0) {
            float scale = 2.0F;
            matrixStack.pushPose();
            this.font.drawWordWrap(this.book.getDescription(), this.x + 12, this.y + 60, 100, this.bookProperty.getColor());
            RenderUtils.drawRightString(matrixStack, this.font, this.book.getAuthor(), this.x + 106F, this.y + 78F, this.bookProperty.getColor());
            matrixStack.scale(scale, scale, scale);
            RenderUtils.drawCenterString(matrixStack, this.font, this.book.getName(), (this.x + 60) / scale, (this.y + 20) / scale, this.bookProperty.getColor());
            matrixStack.popPose();
        }
    }

    /**
     * 计算渲染中心位置
     */
    private void updateXY() {
        this.x = (this.width - 230) / 2;
        this.y = (this.height - 160) / 2;
    }

}
