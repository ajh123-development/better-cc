/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.entity.robot;

import net.ddns.minersonline.BetterCC.common.entity.Robot;
import net.minecraft.nbt.CompoundTag;

public abstract class AbstractRobotActionType {
    private final int id;

    ///////////////////////////////////////////////////////////////////

    protected AbstractRobotActionType(final int id) {
        this.id = id;
    }

    ///////////////////////////////////////////////////////////////////

    public int getId() {
        return id;
    }

    public void initializeData(final Robot robot) {
    }

    public void performServer(final Robot robot, final AbstractRobotAction currentAction) {
    }

    public void performClient(final Robot robot) {
    }

    public abstract AbstractRobotAction deserialize(final CompoundTag tag);
}
