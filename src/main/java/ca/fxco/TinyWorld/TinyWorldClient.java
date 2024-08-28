package ca.fxco.TinyWorld;

import ca.fxco.TinyWorld.commands.TinyCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;

public class TinyWorldClient implements ClientModInitializer {
    public static BlockRenderDispatcher BLOCK_RENDERER = null;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            TinyCommand.register(dispatcher);
        });
    }
}
