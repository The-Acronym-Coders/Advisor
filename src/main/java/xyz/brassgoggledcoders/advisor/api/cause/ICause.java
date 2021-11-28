package xyz.brassgoggledcoders.advisor.api.cause;

import javax.annotation.Nonnull;

public interface ICause {
    void perform(@Nonnull CauseContext context);
}
