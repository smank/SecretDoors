/*
 * SecretDoorHelper.java
 * Last modified: 2014 12 21
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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Door;

/**
 * Set of static utility helpers for operating on SecretDoors.
 * @author Snnappie
 */
public class SecretDoorHelper {

    private SecretDoorHelper() {}

    /**
     * The most commonly used BlockFace directions for door operations.
     */
    // Lack of trust for the JVM - make this static and allocate it once.  The JVM is probably smarter than me anyway,
    // but I don't trust it.
    public static BlockFace[] DIRECTIONS = { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };

    /**
     * @return true if the received Block is considered a valid door type.
     */
    public static boolean isValidDoor(Block door) {
        if (door == null)
            return false;
        switch (door.getType()) {
            case OAK_DOOR:
            case ACACIA_DOOR:
            case BIRCH_DOOR:
            case DARK_OAK_DOOR:
            case JUNGLE_DOOR:
            case SPRUCE_DOOR:
            case CRIMSON_DOOR:
            case WARPED_DOOR:
            case MANGROVE_DOOR:
            case CHERRY_DOOR:
            case BAMBOO_DOOR:
            case COPPER_DOOR:
            case EXPOSED_COPPER_DOOR:
            case WEATHERED_COPPER_DOOR:
            case OXIDIZED_COPPER_DOOR:
            case WAXED_COPPER_DOOR:
            case WAXED_EXPOSED_COPPER_DOOR:
            case WAXED_WEATHERED_COPPER_DOOR:
            case WAXED_OXIDIZED_COPPER_DOOR:
                return true;
        }

        return false;
    }

    public static boolean isValidTrapDoor(Block door) {
        if (door == null)
            return false;
        switch (door.getType()) {
            case OAK_TRAPDOOR:
            case ACACIA_TRAPDOOR:
            case BIRCH_TRAPDOOR:
            case DARK_OAK_TRAPDOOR:
            case JUNGLE_TRAPDOOR:
            case SPRUCE_TRAPDOOR:
            case CRIMSON_TRAPDOOR:
            case WARPED_TRAPDOOR:
            case MANGROVE_TRAPDOOR:
            case CHERRY_TRAPDOOR:
            case BAMBOO_TRAPDOOR:
            case IRON_TRAPDOOR:
            case COPPER_TRAPDOOR:
            case EXPOSED_COPPER_TRAPDOOR:
            case WEATHERED_COPPER_TRAPDOOR:
            case OXIDIZED_COPPER_TRAPDOOR:
            case WAXED_COPPER_TRAPDOOR:
            case WAXED_EXPOSED_COPPER_TRAPDOOR:
            case WAXED_WEATHERED_COPPER_TRAPDOOR:
            case WAXED_OXIDIZED_COPPER_TRAPDOOR:
                return true;
        }

        return false;
    }


    /**
     * @return true if the received block is of type WOODEN_DOOR and is the top block of the door.
     */
    public static boolean isTopHalf(Block door) {
        return (isValidDoor(door)) && ((Door)door.getBlockData()).getHalf() == Bisected.Half.TOP;
    }

    /**
     * @return the bottom half of the door block if {@code isValidDoor(block)}.  Returns {@code null}
     *         otherwise.
     */
    public static Block getKeyFromBlock(Block block) {
        Block door = null;

        if (isValidDoor(block)) {
            // return lower half only
            if (isTopHalf(block))
                door = block.getRelative(BlockFace.DOWN);
            else {
                door = block;
            }
        }
        return door;
    }
    public static Block getKeyFromTrapDoorBlock(Block block) {
        Block door = null;

        if (isValidTrapDoor(block)) {
            door = block;
        }
        return door;
    }


    /**
     * Returns true if item is a placeable attachable item (held in hand).
     * Used to check if player is trying to place something on a concealing block.
     * @param item Material type to be checked.
     * @return true if item is a placeable attachable item, false otherwise.
     */
    public static boolean isPlaceableAttachable(Material item) {
        if (item == null) return false;
        switch (item) {
            // Torch items (become WALL_TORCH when placed on side)
            case TORCH:
            case SOUL_TORCH:
            case REDSTONE_TORCH:
            // Signs, buttons, levers, ladders (item = block name)
            case OAK_SIGN:
            case ACACIA_SIGN:
            case BIRCH_SIGN:
            case DARK_OAK_SIGN:
            case JUNGLE_SIGN:
            case SPRUCE_SIGN:
            case CRIMSON_SIGN:
            case WARPED_SIGN:
            case MANGROVE_SIGN:
            case CHERRY_SIGN:
            case BAMBOO_SIGN:
            case LEVER:
            case STONE_BUTTON:
            case OAK_BUTTON:
            case ACACIA_BUTTON:
            case BIRCH_BUTTON:
            case DARK_OAK_BUTTON:
            case JUNGLE_BUTTON:
            case SPRUCE_BUTTON:
            case CRIMSON_BUTTON:
            case WARPED_BUTTON:
            case MANGROVE_BUTTON:
            case CHERRY_BUTTON:
            case BAMBOO_BUTTON:
            case POLISHED_BLACKSTONE_BUTTON:
            case LADDER:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns true if item is a placed attachable block (wall-mounted, implements Directional).
     * Used to detect attached blocks on concealing blocks.
     * @param item Material type to be checked.
     * @return true if item is an attachable block, false otherwise.
     */
    public static boolean isAttachableItem(Material item) {

        if (item != null) {
            switch (item) {
                // Wall-mounted torches only (Directional)
                case WALL_TORCH:
                case SOUL_WALL_TORCH:
                case REDSTONE_WALL_TORCH:
                case OAK_SIGN:
                case ACACIA_SIGN:
                case BIRCH_SIGN:
                case DARK_OAK_SIGN:
                case JUNGLE_SIGN:
                case SPRUCE_SIGN:
                case CRIMSON_SIGN:
                case WARPED_SIGN:
                case MANGROVE_SIGN:
                case CHERRY_SIGN:
                case BAMBOO_SIGN:
                case OAK_WALL_SIGN:
                case ACACIA_WALL_SIGN:
                case BIRCH_WALL_SIGN:
                case DARK_OAK_WALL_SIGN:
                case JUNGLE_WALL_SIGN:
                case SPRUCE_WALL_SIGN:
                case CRIMSON_WALL_SIGN:
                case WARPED_WALL_SIGN:
                case MANGROVE_WALL_SIGN:
                case CHERRY_WALL_SIGN:
                case BAMBOO_WALL_SIGN:
                case OAK_HANGING_SIGN:
                case ACACIA_HANGING_SIGN:
                case BIRCH_HANGING_SIGN:
                case DARK_OAK_HANGING_SIGN:
                case JUNGLE_HANGING_SIGN:
                case SPRUCE_HANGING_SIGN:
                case CRIMSON_HANGING_SIGN:
                case WARPED_HANGING_SIGN:
                case MANGROVE_HANGING_SIGN:
                case CHERRY_HANGING_SIGN:
                case BAMBOO_HANGING_SIGN:
                case OAK_WALL_HANGING_SIGN:
                case ACACIA_WALL_HANGING_SIGN:
                case BIRCH_WALL_HANGING_SIGN:
                case DARK_OAK_WALL_HANGING_SIGN:
                case JUNGLE_WALL_HANGING_SIGN:
                case SPRUCE_WALL_HANGING_SIGN:
                case CRIMSON_WALL_HANGING_SIGN:
                case WARPED_WALL_HANGING_SIGN:
                case MANGROVE_WALL_HANGING_SIGN:
                case CHERRY_WALL_HANGING_SIGN:
                case BAMBOO_WALL_HANGING_SIGN:
                case LEVER:
                case STONE_BUTTON:
                case OAK_BUTTON:
                case ACACIA_BUTTON:
                case BIRCH_BUTTON:
                case DARK_OAK_BUTTON:
                case JUNGLE_BUTTON:
                case SPRUCE_BUTTON:
                case CRIMSON_BUTTON:
                case WARPED_BUTTON:
                case MANGROVE_BUTTON:
                case CHERRY_BUTTON:
                case BAMBOO_BUTTON:
                case POLISHED_BLACKSTONE_BUTTON:
                case LADDER:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * Casts the received block to {@link org.bukkit.material.Attachable} if it is considered a valid attachable type.
     * Note that valid attachable types are defined by {@link #isAttachableItem(org.bukkit.Material)}.
     * If block is not a valid attachable type, then null is returned instead.
     * @param block Block to be cast to Attachable
     * @return block as an attachable, or null if it could not be cast.
     */
    public static Directional getAttachableFromBlock(Block block) {
        return isAttachableItem(block.getType()) ? ((Directional) block.getBlockData()) : null;
    }

    /**
     * Returns the {@link org.bukkit.block.BlockFace} that the door block will be facing while it is closed.
     * If door is not of type WOODEN_DOOR (that is, {@code !isValidDoor(door)}, then {@code null}
     * is returned instead.
     * @param door door block to determine it's facing.
     * @return Returns the direction the door is facing while closed or null if door is not a WOODEN_DOOR.
     */
    public static BlockFace getDoorFace(Block door) {
        Block key = getKeyFromBlock(door);
        // This abstraction is broken with the new doors - they are not linked to Door.class and thus cannot be
        // down cast to Directional.
        // Instead we will check the bits ourselves
//        return key != null ? ((Directional) key.getState().getData()).getFacing() : null;
        if (key == null)
            return null;
        BlockFace data = ((Directional) key.getBlockData()).getFacing();
        switch (data) {
            case EAST: return BlockFace.WEST;
            case SOUTH: return BlockFace.NORTH;
            case WEST: return BlockFace.EAST;
            case NORTH: return BlockFace.SOUTH;
        }

        return null;
    }

    /**
     * Orientation represents the state of which side of the door was clicked.  That is, Orientation has
     * two enumerated values, BLOCK_FIRST and DOOR_FIRST.
     */
    public static enum Orientation {
        BLOCK_FIRST, DOOR_FIRST
    }


    public static BlockFace getAttachableface(Directional block) {
        switch (block.getFacing()) {
            case NORTH:
                return BlockFace.SOUTH;
            case SOUTH:
                return BlockFace.NORTH;
            case EAST:
                return BlockFace.WEST;
            case WEST:
                return BlockFace.EAST;
            default:
                return null;
        }
    }
}
