package io.github.smank.secretdoors;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Door;

/**
 * Original Author: MrChick, updated by dill01 and now updated by Snnappie
 * @author Snnappie
 *
 * Represents a SecretDoor object - contains info about the hidden blocks and anything attached to the blocks (e.g. torches, signs, etc)
 * Attached signs retain their text.
 */
public class SecretDoor implements SecretOpenable {

    private Block doorBlock;
    private Block[] blocks = new Block[2];
    private Material[] materials = new Material[2];
    private BlockData[] data = new BlockData[2];
    private SecretDoorHelper.Orientation orientation = null;

    //// Attached state

    // References to the attached block objects
    private Block[] attachedBlocks;
    // Material type of the blocks while the door is closed (i.e. before they get set to AIR).
    private Material[] attachedMats;
    // API for changing directions is non-functional in Bukkit/Spigot v 1.8 - 2014-12-21
    //private BlockFace[] attachedDirections;// = new BlockFace[8];
    // data values for each attached block
    private BlockData[] attachedData;
    // Count of the number of items attached to this door.
    private int attachedCount = 0;
    // Contains the text of every sign that is attached to this door.
    private String[][] signText;

    public SecretDoor(Block door, Block other, SecretDoorHelper.Orientation orientation, boolean preserveAttachments) {

        if (SecretDoors.DEBUG) {
            System.out.println("Door constructed at location: " + door.getLocation());
            System.out.println("Door is facing: " + SecretDoorHelper.getDoorFace(door));
        }
        if (SecretDoorHelper.isValidDoor(door)) { // is door

            if (SecretDoorHelper.isTopHalf(door)) { // is upper half
                //System.out.println("Top Door");
                doorBlock = door.getRelative(BlockFace.DOWN);

                this.blocks[0] = other;
                this.blocks[1] = other.getRelative(BlockFace.DOWN);

            } else {
                //System.out.println("Bottom Door");
                doorBlock = door;
                this.blocks[1] = other;
                this.blocks[0] = other.getRelative(BlockFace.UP);
            }
        }

        this.materials[0] = this.blocks[0].getType();
        this.materials[1] = this.blocks[1].getType();

        this.data[0] = this.blocks[0].getState().getBlockData().clone();
        this.data[1] = this.blocks[1].getState().getBlockData().clone();

        if (SecretDoors.DEBUG) {
            System.out.println("Door blocks:" +
                    "\n\tblocks[0] == " + blocks[0] +
                    "\n\tblocks[1] == " + blocks[1] +
                    "\n\tdata[0] == " + data[0] +
                    "\n\tdata[1] == " + data[1]
            );

        }

        this.orientation = orientation;

        // handle attached blocks (torches, signs, etc)
        if (!preserveAttachments) {
            return;
        }

        BlockFace[] faces = { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
        for (BlockFace face : faces) {
            for (int i = 0; i < 2; i++) {
                Block attached = blocks[i].getRelative(face);

                // if it is a simple attachable item (wall-mounted, Directional)
                Directional sam = SecretDoorHelper.getAttachableFromBlock(attached);
                if (SecretDoors.DEBUG && sam != null) {
                    System.out.println("Found attachable at " + attached.getLocation() +
                        " type=" + attached.getType() +
                        " facing=" + sam.getFacing() +
                        " checkFace=" + face);
                }
                if (sam != null && sam.getFacing() != null) {
                    // skip if torch isn't attached to our concealing block
                    // A torch EAST of concealing block, attached to it, faces EAST (away from attachment)
                    // face = EAST (direction we're checking), torch.getFacing() = EAST → match!
                    if (sam.getFacing() != face) {
                        if (SecretDoors.DEBUG) {
                            System.out.println("  Skipping: facing " + sam.getFacing() + " != checkFace " + face);
                        }
                        continue;
                    }
                    if (SecretDoors.DEBUG) {
                        System.out.println("  ADDING attachment: " + attached.getType() + " at " + attached.getLocation());
                    }

                    addAttachment(attached);
                }
            }
        }

        // Check for floor torches on top of concealing blocks (not Directional)
        for (int i = 0; i < 2; i++) {
            Block onTop = blocks[i].getRelative(BlockFace.UP);
            if (SecretDoorHelper.isFloorTorch(onTop.getType())) {
                if (SecretDoors.DEBUG) {
                    System.out.println("  ADDING floor torch: " + onTop.getType() + " at " + onTop.getLocation());
                }
                addAttachment(onTop);
            }
        }
    }

    private void addAttachment(Block attached) {
        // First time we've been here, initialize the arrays
        if (attachedCount == 0) {
            attachedBlocks = new Block[12];
            attachedMats = new Material[12];
            attachedData = new BlockData[12];
            signText = new String[12][4];
        }

        // handle sign text
        if (SecretDoorHelper.isAnySign(attached.getType())) {
            handleSignText(attached);
        }

        attachedBlocks[attachedCount] = attached;
        attachedMats[attachedCount] = attached.getType();
        // Clone the BlockData to avoid issues with mutable references
        attachedData[attachedCount] = attached.getBlockData().clone();
        attachedCount++;
    }

    private void handleSignText(Block block) {
        Sign s = (Sign) (block.getState());
        signText[attachedCount] = s.getLines();
    }


    @Override
    public void close() {
        if (SecretDoors.DEBUG) {
            System.out.println("Closing door with " + attachedCount + " attached blocks to restore");
        }
        for (int i = 0; i < 2; i++) {
            if (SecretDoors.DEBUG) {
                System.out.println("Setting data[" + i + "] from " + this.blocks[i].getType() + " to " + this.materials[i]);
                System.out.println("Setting " + this.blocks[i].getState().getData() + " to " + this.data[i]);
            }

            this.blocks[i].setType(this.materials[i]);
            // Use setBlockData directly on the block, not on a snapshot
            this.blocks[i].setBlockData(this.data[i]);
        }

        // Close the actual door
        BlockState doorState = this.doorBlock.getState();
        Door doorData = (Door) doorState.getBlockData();
        doorData.setOpen(false);
        doorState.setBlockData(doorData);
        doorState.update();

        doorBlock.getWorld().playEffect(doorBlock.getLocation(), Effect.DOOR_TOGGLE, 0);

        // handle attached blocks
        for (int i = 0; i < attachedCount; i++) {
            if (SecretDoors.DEBUG) {
                System.out.println("Restoring attached block " + i + ": " + attachedMats[i] + " at " + attachedBlocks[i].getLocation());
                System.out.println("  BlockData to restore: " + attachedData[i]);
            }

            attachedBlocks[i].setType(attachedMats[i]);

            if (SecretDoors.DEBUG) {
                System.out.println("attachedBlocks[" + i + "] is facing " + ((Directional) attachedBlocks[i].getBlockData()).getFacing());
                System.out.println("attachedBlocks[" + i + "] data == " + attachedBlocks[i].getBlockData());
            }

            // This API is non-functional as of Bukkit/Spigot 1.8 - 2014-12-21
            attachedBlocks[i].setBlockData(attachedData[i]);
            //attachedBlocks[i].se
            if (SecretDoors.DEBUG) {
                System.out.println("attachedBlocks[" + i + "] is facing " + ((Directional) attachedBlocks[i].getBlockData()).getFacing());
                System.out.println("attachedBlocks[" + i + "] data == " + attachedBlocks[i].getBlockData());
            }

            // handle sign text
            if (SecretDoorHelper.isAnySign(attachedBlocks[i].getType())) {
                Sign s = (Sign) (attachedBlocks[i].getState());
                for (int j = 0; j < 4; j++) {
                    s.setLine(j, signText[i][j]);
                }
                s.update(true);
            }
        }
    }


    @Override
    public void open() {
        if (SecretDoors.DEBUG) {
            System.out.println("Opening door with " + attachedCount + " attached blocks");
        }

        // handle attached blocks
        for (int i = 0; i < attachedCount; i++) {
            if (SecretDoors.DEBUG) {
                System.out.println("  Removing attached block " + i + ": " + attachedBlocks[i].getType() + " at " + attachedBlocks[i].getLocation());
            }
            attachedBlocks[i].setType(Material.AIR);
        }

        for (int i = 0; i < 2; i++) {
            this.blocks[i].setType(Material.AIR);
        }

        if (this.orientation == SecretDoorHelper.Orientation.BLOCK_FIRST) {
            BlockState doorState = this.doorBlock.getState();
            Door doorData = (Door) doorState.getBlockData();
            doorData.setOpen(true);
            doorState.setBlockData(doorData);
            doorState.update();

            doorBlock.getWorld().playEffect(doorBlock.getLocation(), Effect.DOOR_TOGGLE, 0);
        }

    }

    @Override
    public Block getKey() {
        return doorBlock;
    }
}