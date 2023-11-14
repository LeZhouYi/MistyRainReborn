package skily_leyu.mistyrain.common.core.book;

import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

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

    public List<Content> getContents(int chapterIndex) {
        List<Content> teContents = new ArrayList<>();
        if (chapterIndex >= 0 && chapterIndex < this.chapters.size()) {
            String chapterKey = this.chapters.get(chapterIndex).getKey();
            for (Content c : this.contents) {
                if (c.isParentNode(chapterKey)) {
                    teContents.add(c);
                }
            }
        }
        return teContents;
    }

    public boolean hasNext(PageStage pageStage) {
        if (pageStage.isRoot()) {
            int size = this.getRootChapter().size();
            int page = pageStage.getPage();
            return (page==0 && 16<size-1)||(page!=0 && (page+1)*16<size-1);
        } else if (pageStage.isChapter()) {
            int size = this.getContents(pageStage.getUpper()).size();
            return pageStage.getPage() * 16 < size - 1;
        } else {
            Content content = this.getContent(pageStage.getIndex());
            return content != null && content.getPage()+2 < pageStage.getPage();
        }
    }

    public Content getContent(int index) {
        if (index >= 0 && index < this.contents.size()) {
            return this.contents.get(index);
        }
        return null;
    }
}
