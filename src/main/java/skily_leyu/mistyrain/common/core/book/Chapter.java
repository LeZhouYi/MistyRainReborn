package skily_leyu.mistyrain.common.core.book;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class Chapter {

    private String key; //关键字
    private String icon; //图标路径
    private String parentNode; //所属目录

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isRoot() {
        return parentNode == null;
    }

    public TranslationTextComponent getName() {
        return new TranslationTextComponent(this.key + ".name");
    }

    public TranslationTextComponent getDescription() {
        return new TranslationTextComponent(this.key + ".description");
    }

    public ItemStack getItemStack() {
        if (this.icon == null || this.icon.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(icon)));
    }

    public String getKey() {
        return this.key;
    }

    public boolean isParentEqual(String parentNode) {
        return this.parentNode != null && !this.parentNode.isEmpty() && this.parentNode.equals(parentNode);
    }

    public String getParentNode() {
        return this.parentNode;
    }
}
