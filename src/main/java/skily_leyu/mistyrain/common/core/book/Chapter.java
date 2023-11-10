package skily_leyu.mistyrain.common.core.book;

public class Chapter {

    private String name; //章名
    private String icon; //图标路径
    private String parentNode; //所属目录
    private String description; //描述

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
