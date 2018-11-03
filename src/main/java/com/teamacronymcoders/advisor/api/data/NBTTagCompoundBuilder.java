package com.teamacronymcoders.advisor.api.data;

import net.minecraft.nbt.NBTTagCompound;

public class NBTTagCompoundBuilder {
    private NBTTagCompound nbtTagCompound;

    public NBTTagCompoundBuilder() {
        nbtTagCompound = new NBTTagCompound();
    }

    public NBTTagCompoundBuilder withBoolean(String name, boolean value) {
        nbtTagCompound.setBoolean(name, value);
        return this;
    }

    public NBTTagCompound create() {
        return nbtTagCompound;
    }
}
