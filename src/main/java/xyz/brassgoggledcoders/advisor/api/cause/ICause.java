package xyz.brassgoggledcoders.advisor.api.cause;

import xyz.brassgoggledcoders.advisor.api.manager.IManagerEntry;

import javax.annotation.Nonnull;

public interface ICause extends IManagerEntry {
    void perform(@Nonnull CauseContext context);
}
