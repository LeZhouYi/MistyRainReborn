package skily_leyu.mistyrain.common.core.book;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class Content {
    private String key; //关键字
    private String parentNode; //所属目录
    private String itemIcon; //物品图标

    public void setKey(String key) {
        this.key = key;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public boolean isParentNode(String chapterKey) {
        return this.parentNode != null && !chapterKey.isEmpty() && this.parentNode.equals(chapterKey);
    }

    public TranslationTextComponent getName() {
        return new TranslationTextComponent(this.key + ".name");
    }

    public TranslationTextComponent getProperty(){
        return new TranslationTextComponent(this.key+".property");
    }

    public ItemStack getItemStack() {
        if (this.itemIcon != null && !this.itemIcon.isEmpty()) {
            return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.itemIcon)));
        }
        return ItemStack.EMPTY;
    }

    public TranslationTextComponent getDescription() {
        return new TranslationTextComponent(this.key+".description");
    }

    public TranslationTextComponent getText(){
        return new TranslationTextComponent(this.key+".text");
    }

}
