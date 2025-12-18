package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.BuckarooBootsArmorItem;
import net.dontouchat.cowboyhats.item.custom.SnakeskinBootsArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class SnakeskinBootsArmorModel extends GeoModel<SnakeskinBootsArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/snakeskinboots.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/snakeskinboots.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(SnakeskinBootsArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(SnakeskinBootsArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(SnakeskinBootsArmorItem animatable) {
        return this.animations;
    }
}
