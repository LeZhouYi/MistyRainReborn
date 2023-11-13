package skily_leyu.mistyrain.common.core.book;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class Chapter {

    private String key; //关键字
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

    public boolean isRoot(){
        return parentNode == null;
    }

    public TranslationTextComponent getName(){
        return new TranslationTextComponent(this.key+"."+this.name);
    }

    public ItemStack getItemstack(){
        if(this.name.isEmpty()){
            return ItemStack.EMPTY;
        }
        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(icon)));
    }

}
