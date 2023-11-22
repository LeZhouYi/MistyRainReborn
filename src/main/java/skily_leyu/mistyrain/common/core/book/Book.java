package skily_leyu.mistyrain.common.core.book;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {
    private String key; //关键字
    private List<Chapter> chapters; //目录
    private List<Content> contents; //文章

    public void setKey(String key) {
        this.key = key;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public TranslationTextComponent getName() {
        return new TranslationTextComponent(key + ".name");
    }

    public TranslationTextComponent getAuthor() {
        return new TranslationTextComponent(key + ".author");
    }

    public TranslationTextComponent getDescription() {
        return new TranslationTextComponent(key + ".description");
    }

    public List<Chapter> getRootChapter() {
        List<Chapter> rootChapter = new ArrayList<>();
        for (Chapter chapter : this.chapters) {
            if (chapter.isRoot()) {
                rootChapter.add(chapter);
            }
        }
        return rootChapter;
    }

    public List<Integer> getContentIndexSet(int chapterIndex) {
        List<Integer> indexSet = new ArrayList<>();
        if (chapterIndex >= 0 && chapterIndex < this.chapters.size()) {
            String chapterKey = this.chapters.get(chapterIndex).getKey();
            int index = 0;
            for (Content c : this.contents) {
                if (c.isParentNode(chapterKey)) {
                    indexSet.add(index);
                }
                index += 1;
            }
        }
        return indexSet;
    }

    public List<Chapter> getChapters(Chapter chapter) {
        List<Chapter> teChapters = new ArrayList<>();
        if (chapter != null) {
            String parentNode = chapter.getParentNode();
            if (parentNode != null && !parentNode.isEmpty()) {
                for (Chapter teChapter : this.chapters) {
                    if (teChapter.isParentEqual(parentNode)) {
                        teChapters.add(teChapter);
                    }
                }
            }
        }
        return teChapters;
    }

    public boolean hasNext(PageStage pageStage, FontRenderer fontRenderer, BookProperty bookProperty) {
        if (pageStage.isRoot()) {
            int size = this.getRootChapter().size();
            int page = pageStage.getPage();
            return (page == 0 && 16 < size - 1) || (page != 0 && (page + 1) * 16 < size - 1);
        } else if (pageStage.isChapter()) {
            int index = pageStage.getIndex();
            if (index < this.chapters.size()) {
                Chapter chapter = this.chapters.get(pageStage.getIndex());
                int size = this.getChapters(chapter).size();
                return pageStage.getPage() * 16 < size - 1;
            }
            return false;
        } else {
            Content content = this.getContent(pageStage.getIndex());
            List<IReorderingProcessor> texts = fontRenderer.split(Objects.requireNonNull(content).getText(), bookProperty.getTextWidth());
            int lineIndexStart = bookProperty.getLineIndex(pageStage.getPage() + 2);
            return lineIndexStart < texts.size();
        }
    }

    @Nullable
    public Content getContent(int index) {
        if (index >= 0 && index < this.contents.size()) {
            return this.contents.get(index);
        }
        return null;
    }

    @Nullable
    public Chapter getChapter(int index) {
        if (index >= 0 && index < this.chapters.size()) {
            return this.chapters.get(index);
        }
        return null;
    }
}
