package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.GamblerArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class GamblerArmorModel extends GeoModel<GamblerArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/gambler.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/gambler.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(GamblerArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(GamblerArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(GamblerArmorItem animatable) {
        return this.animations;
    }
}