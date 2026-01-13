package io.github.smank.secretdoors;


import org.bukkit.block.Block;

/**
 * Standard interface for "secret" openable types.  SecretOpenables must be openable, closeable, and return a unique
 * key that properly implements hashCode and equals.  SecretOpenables themselves are not required to implement
 * equality and hashCode.
 * SecretOpenable implementations are required to keep all necessary state when opened for close operations to
 * return the involved blocks to their exact state before the SecretOpenable was created.  That is, SecretOpenable
 * implementations must not alter involved blocks any further beyond the lifetime of the object.
 */
public interface SecretOpenable {

    /**
     * Must open the object.  That is, open must clear a passage in which a Player may pass through an otherwise
     * closed and hidden set of blocks.
     */
    public void open();

    /**
     * Must close the object.  Close in this context means to revert the blocks involved in creating this instance
     * to their original state, effectively closing the passage.
     */
    public void close();

    /**
     * Returns a unique {@link org.bukkit.block.Block} object that can be used to identify this instance in an opened state.
     * It is not strictly required to identify this while this is in a closed state.
     * @return A unique identifier for this in an opened state.
     */
    public Block getKey();
}
