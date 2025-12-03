package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.CattlemanArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CattlemanArmorRenderer extends GeoArmorRenderer<CattlemanArmorItem> {
    public CattlemanArmorRenderer() {
        super(new CattlemanArmorModel());
    }
}