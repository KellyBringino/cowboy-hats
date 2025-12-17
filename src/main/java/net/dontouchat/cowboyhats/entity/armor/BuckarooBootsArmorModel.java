package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.BuckarooBootsArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class BuckarooBootsArmorModel extends GeoModel<BuckarooBootsArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/buckarooboots.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/buckarooboots.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(BuckarooBootsArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(BuckarooBootsArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(BuckarooBootsArmorItem animatable) {
        return this.animations;
    }
}
