/*
 * SecretTrapdoor.java
 * Last modified: 2014 12 20
 *
 * In place of a legal notice,
 * here is the author's adaptation to the sqlite3 blessing:
 *
 * 	May you do good and not evil.
 * 	May you find forgiveness for yourself and forgive others.
 * 	May you share freely, never taking more than you give.
 *
 * 	May you love the Lord your God with all your heart,
 * 	with all your soul,
 * 	and with all your mind.
 */

package io.github.smank.secretdoors;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.TrapDoor;

public class SecretTrapdoor implements SecretOpenable {

    private Block doorBlock;
    private BlockData doorData;
    private BlockFace direction;

    private Block above;
    private Material mat;
    private Material doorMat;
    private BlockData aboveData;
    private boolean fromAbove;

    public SecretTrapdoor(Block doorBlock, Block above, boolean fromAbove) {
        if (SecretDoorHelper.isValidTrapDoor(doorBlock)) {
            this.doorBlock = doorBlock;
            this.fromAbove = fromAbove;
            direction = ((Directional) doorBlock.getBlockData()).getFacing().getOppositeFace();
            this.above = above;

            mat = this.above.getType();
            aboveData = this.above.getBlockData();
            doorData = this.doorBlock.getBlockData();
        }
    }

    @Override
    public void open() {
        //doorBlock.setBlockData(getKey().getBlockData());//setData(getDirectionData());

        above.setType(Material.AIR);

        BlockState doorState = this.doorBlock.getState();
        TrapDoor doorData = (TrapDoor) doorState.getBlockData();
        doorData.setOpen(true);
        doorState.setBlockData(doorData);
        doorState.update();
        if (fromAbove)
            doorBlock.getWorld().playEffect(doorBlock.getLocation(), Effect.DOOR_TOGGLE, 0);
    }

    @Override
    public void close() {
        // Close the actual trapdoor
        BlockState doorState = this.doorBlock.getState();
        TrapDoor trapDoorData = (TrapDoor) doorState.getBlockData();
        trapDoorData.setOpen(false);
        doorState.setBlockData(trapDoorData);
        doorState.update();

        above.setType(mat);
        above.setBlockData(aboveData);
        doorBlock.getWorld().playEffect(doorBlock.getLocation(), Effect.DOOR_TOGGLE, 0);
    }

    @Override
    public Block getKey() {
        return doorBlock;
    }
}
