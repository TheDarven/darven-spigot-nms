package fr.thedarven.nmstry.utils.anvil;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.EntityHuman;

public class AnvilCustom extends ContainerAnvil {

    private final AnvilGUI anvilGUI;

    public AnvilCustom(EntityHuman entityHuman, AnvilGUI anvilGUI) {
        super(entityHuman.inventory, entityHuman.world, new BlockPosition(0, 0, 0), entityHuman);
        this.anvilGUI = anvilGUI;
    }

    /**
     * Allows to open the anvil's inventory
     */
    @Override
    public boolean a(EntityHuman human){
        return true;
    }

    public void setItemName(String name) {
        this.anvilGUI.setItemName(name);
        super.a(name);
    }

}
