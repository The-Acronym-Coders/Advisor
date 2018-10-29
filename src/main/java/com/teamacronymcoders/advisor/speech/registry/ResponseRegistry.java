package com.teamacronymcoders.advisor.speech.registry;

import com.teamacronymcoders.advisor.api.speech.Response;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.registrysystem.ModularRegistry;
import com.teamacronymcoders.base.registrysystem.pieces.IRegistryPiece;

import java.util.List;

public class ResponseRegistry extends ModularRegistry<Response> {
    public ResponseRegistry(IBaseMod mod, List<IRegistryPiece> registryPieces) {
        super("RESPONSE", mod, registryPieces);
    }
}
