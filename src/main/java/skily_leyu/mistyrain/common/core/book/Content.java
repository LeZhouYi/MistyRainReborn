package skily_leyu.mistyrain.common.core.book;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class Content {
    private String key; //关键字
    private String parentNode; //所属目录
    private String itemIcon; //物品图标
    private String imagePath; //图标资源路径
    private List<String> texts; //文本

    public void setKey(String key) {
        this.key = key;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public boolean isParentNode(String chapterKey) {
        return this.parentNode != null && !chapterKey.isEmpty() && this.parentNode.equals(chapterKey);
    }

    public int getPage() {
        return this.texts.size();
    }

    public TranslationTextComponent getName() {
        return new TranslationTextComponent(this.key + ".name");
    }

    public ItemStack getItemStack() {
        if (this.itemIcon == null && !this.itemIcon.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.itemIcon)));
    }

    public TranslationTextComponent getDescription() {
        return new TranslationTextComponent(this.key+".description");
    }
}
