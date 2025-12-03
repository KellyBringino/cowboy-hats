package net.dontouchat.cowboyhats.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class CowboyHatItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CowboyHatItem(ArmorMaterial pMaterial, Type slot, Properties pProperties) {
        super(pMaterial,slot,pProperties);

    }

    // Let's add our animation controller
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            // Apply our generic idle animation.
            // Whether it plays or not is decided down below.
            state.setAnimation(DefaultAnimations.IDLE);

            // This is the entity that is currently wearing/holding the item
            Entity entity = state.getData(DataTickets.ENTITY);

            // We'll just have ArmorStands always animate, so we can return here
            if (entity instanceof ArmorStand)
                return PlayState.CONTINUE;

            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}