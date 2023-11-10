package skily_leyu.mistyrain.common.core.book;

import java.util.List;

public class Content {
    private String title; //内容标题
    private String parentNode; //所属目录
    private String description; //描述
    private String itemKey; //物品图标
    private String imagePath; //图标资源路径
    private List<String> texts; //文本

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }
}
