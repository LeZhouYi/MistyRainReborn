package skily_leyu.mistyrain.common.core.book;

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
}
