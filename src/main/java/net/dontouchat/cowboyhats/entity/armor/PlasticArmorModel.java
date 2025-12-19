package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.PlasticArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class PlasticArmorModel extends GeoModel<PlasticArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/plastic.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/plastic.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(PlasticArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(PlasticArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(PlasticArmorItem animatable) {
        return this.animations;
    }
}
