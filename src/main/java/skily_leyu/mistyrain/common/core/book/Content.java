package skily_leyu.mistyrain.common.core.book;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class Content {
    private String key; //关键字
    private String parentNode; //所属目录
    private String itemKey; //物品图标
    private String imagePath; //图标资源路径
    private List<String> texts; //文本

    public void setKey(String key) {
        this.key = key;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
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

    public boolean isParentNode(String chapterKey) {
        return this.parentNode!=null&&!chapterKey.isEmpty()&&this.parentNode.equals(chapterKey);
    }

    public int getPage() {
        return this.texts.size();
    }

    public TranslationTextComponent getName() {
        return new TranslationTextComponent(this.key+".name");
    }
}
