/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.item;

public final class BlockOperationsModule extends ModItem {
    public static final int DURABILITY = 2500;

    ///////////////////////////////////////////////////////////////////

    public BlockOperationsModule() {
        super(createProperties().durability(DURABILITY));
    }
}
