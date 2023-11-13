package skily_leyu.mistyrain.common.core.book;

import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String key; //关键字
    private String name; //书名
    private String author; //作者
    private String description; //描述
    private List<Chapter> chapters; //目录
    private List<Content> contents; //文章

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TranslationTextComponent getName(){
        return new TranslationTextComponent(key+"."+name);
    }

    public TranslationTextComponent getAuthor(){
        return new TranslationTextComponent(key+"."+author);
    }

    public TranslationTextComponent getDescription(){
        return new TranslationTextComponent(key+"."+description);
    }

    public List<Chapter> getRootChapter(){
        List<Chapter> rootChapter = new ArrayList<>();
        for(Chapter chapter: this.chapters){
            if(chapter.isRoot()){
                rootChapter.add(chapter);
            }
        }
        return rootChapter;
    }

}
