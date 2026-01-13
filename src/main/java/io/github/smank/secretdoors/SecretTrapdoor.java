package io.github.smank.secretdoors;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.TrapDoor;

public class SecretTrapdoor implements SecretOpenable {

    private Block doorBlock;
    private BlockData doorData;
    private BlockFace direction;

    private Block above;
    private Material mat;
    private BlockData aboveData;
    private boolean fromAbove;

    // Attachment handling
    private Block[] attachedBlocks;
    private Material[] attachedMats;
    private BlockData[] attachedData;
    private int attachedCount = 0;
    private String[][] signText;

    public SecretTrapdoor(Block doorBlock, Block above, boolean fromAbove) {
        this(doorBlock, above, fromAbove, true);
    }

    public SecretTrapdoor(Block doorBlock, Block above, boolean fromAbove, boolean preserveAttachments) {
        if (SecretDoorHelper.isValidTrapDoor(doorBlock)) {
            this.doorBlock = doorBlock;
            this.fromAbove = fromAbove;
            direction = ((Directional) doorBlock.getBlockData()).getFacing().getOppositeFace();
            this.above = above;

            mat = this.above.getType();
            aboveData = this.above.getBlockData().clone();
            doorData = this.doorBlock.getBlockData();

            if (!preserveAttachments) {
                return;
            }

            // Check for attachments on the concealing block (sides)
            BlockFace[] faces = { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
            for (BlockFace face : faces) {
                Block attached = above.getRelative(face);

                if (SecretDoors.DEBUG) {
                    System.out.println("SecretTrapdoor: checking face " + face + " -> " + attached.getType() + " at " + attached.getLocation());
                }

                Directional sam = SecretDoorHelper.getAttachableFromBlock(attached);
                if (sam != null && sam.getFacing() != null) {
                    if (SecretDoors.DEBUG) {
                        System.out.println("  Found attachable! facing=" + sam.getFacing() + ", expected=" + face);
                    }
                    // Skip if not attached to our concealing block
                    if (sam.getFacing() != face) {
                        if (SecretDoors.DEBUG) {
                            System.out.println("  Skipping - facing doesn't match");
                        }
                        continue;
                    }

                    if (attachedCount == 0) {
                        attachedBlocks = new Block[6];
                        attachedMats = new Material[6];
                        attachedData = new BlockData[6];
                        signText = new String[6][4];
                    }

                    if (SecretDoorHelper.isWallSign(attached.getType())) {
                        Sign s = (Sign) attached.getState();
                        signText[attachedCount] = s.getLines();
                    }

                    attachedBlocks[attachedCount] = attached;
                    attachedMats[attachedCount] = attached.getType();
                    attachedData[attachedCount] = attached.getBlockData().clone();
                    attachedCount++;
                    if (SecretDoors.DEBUG) {
                        System.out.println("  ADDED wall attachment!");
                    }
                }
            }

            // Check for floor torches and standing signs on top of the concealing block
            Block onTop = above.getRelative(BlockFace.UP);
            if (SecretDoors.DEBUG) {
                System.out.println("SecretTrapdoor: checking block on top of concealing block");
                System.out.println("  Concealing block: " + above.getLocation() + " type=" + above.getType());
                System.out.println("  Block on top: " + onTop.getLocation() + " type=" + onTop.getType());
                System.out.println("  Is floor torch? " + SecretDoorHelper.isFloorTorch(onTop.getType()));
                System.out.println("  Is standing sign? " + SecretDoorHelper.isStandingSign(onTop.getType()));
            }
            if (SecretDoorHelper.isFloorTorch(onTop.getType()) || SecretDoorHelper.isStandingSign(onTop.getType())) {
                if (attachedCount == 0) {
                    attachedBlocks = new Block[6];
                    attachedMats = new Material[6];
                    attachedData = new BlockData[6];
                    signText = new String[6][4];
                }

                // Save sign text if it's a sign
                if (SecretDoorHelper.isStandingSign(onTop.getType())) {
                    Sign s = (Sign) onTop.getState();
                    signText[attachedCount] = s.getLines();
                }

                attachedBlocks[attachedCount] = onTop;
                attachedMats[attachedCount] = onTop.getType();
                attachedData[attachedCount] = onTop.getBlockData().clone();
                attachedCount++;
                if (SecretDoors.DEBUG) {
                    System.out.println("  ADDED top attachment: " + onTop.getType());
                }
            }
            if (SecretDoors.DEBUG) {
                System.out.println("SecretTrapdoor: total attachedCount = " + attachedCount);
            }
        }
    }

    @Override
    public void open() {
        if (SecretDoors.DEBUG) {
            System.out.println("SecretTrapdoor.open() with " + attachedCount + " attachments");
        }
        // Remove attached blocks first
        for (int i = 0; i < attachedCount; i++) {
            if (SecretDoors.DEBUG) {
                System.out.println("  Removing attachment " + i + ": " + attachedBlocks[i].getType() + " at " + attachedBlocks[i].getLocation());
            }
            attachedBlocks[i].setType(Material.AIR);
        }

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
        if (SecretDoors.DEBUG) {
            System.out.println("SecretTrapdoor.close() with " + attachedCount + " attachments to restore");
        }
        // Close the actual trapdoor
        BlockState doorState = this.doorBlock.getState();
        TrapDoor trapDoorData = (TrapDoor) doorState.getBlockData();
        trapDoorData.setOpen(false);
        doorState.setBlockData(trapDoorData);
        doorState.update();

        // Restore concealing block
        above.setType(mat);
        above.setBlockData(aboveData);

        // Restore attached blocks
        for (int i = 0; i < attachedCount; i++) {
            if (SecretDoors.DEBUG) {
                System.out.println("  Restoring attachment " + i + ": " + attachedMats[i] + " at " + attachedBlocks[i].getLocation());
            }
            attachedBlocks[i].setType(attachedMats[i]);
            attachedBlocks[i].setBlockData(attachedData[i]);

            if (SecretDoorHelper.isWallSign(attachedBlocks[i].getType()) || SecretDoorHelper.isStandingSign(attachedBlocks[i].getType())) {
                Sign s = (Sign) attachedBlocks[i].getState();
                for (int j = 0; j < 4; j++) {
                    s.setLine(j, signText[i][j]);
                }
                s.update(true);
            }
        }

        doorBlock.getWorld().playEffect(doorBlock.getLocation(), Effect.DOOR_TOGGLE, 0);
    }

    @Override
    public Block getKey() {
        return doorBlock;
    }
}
