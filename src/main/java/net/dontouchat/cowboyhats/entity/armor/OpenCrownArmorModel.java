package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.OpenCrownArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class OpenCrownArmorModel extends GeoModel<OpenCrownArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/opencrown.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/opencrown.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(OpenCrownArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(OpenCrownArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(OpenCrownArmorItem animatable) {
        return this.animations;
    }
}