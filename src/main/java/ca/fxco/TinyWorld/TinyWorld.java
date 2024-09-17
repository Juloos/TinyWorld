package ca.fxco.TinyWorld;

import ca.fxco.TinyWorld.commands.ScaleCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class TinyWorld implements ModInitializer {

    public static final String CUSTOM_COLLISION_KEY = "useCustomCollisionShape";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
            ScaleCommand.register(dispatcher);
        });
    }
}
