package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.PlasticBootsArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class PlasticBootsArmorModel extends GeoModel<PlasticBootsArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/plasticboots.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/plasticboots.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(PlasticBootsArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(PlasticBootsArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(PlasticBootsArmorItem animatable) {
        return this.animations;
    }
}
