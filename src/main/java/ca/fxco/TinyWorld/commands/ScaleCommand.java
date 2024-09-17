package ca.fxco.TinyWorld.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;

import java.util.List;

// Command /scale <entities> <scale>
public class ScaleCommand {
    private static final List<Holder<Attribute>> ATTRIBUTES_TO_SCALE = Lists.newArrayList(
            Attributes.SCALE,
            Attributes.STEP_HEIGHT,
            Attributes.JUMP_STRENGTH,
            Attributes.GRAVITY,
            Attributes.SAFE_FALL_DISTANCE,
            Attributes.MOVEMENT_SPEED,
            Attributes.KNOCKBACK_RESISTANCE,
            Attributes.BLOCK_INTERACTION_RANGE,
            Attributes.ENTITY_INTERACTION_RANGE
    );

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                Commands.literal("scale")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(Commands.argument("entities", EntityArgument.entities())
                                .then(Commands.argument("scale", DoubleArgumentType.doubleArg(1 / 16d, 16d))
                                        .executes(context -> {
                                            double scale = DoubleArgumentType.getDouble(context, "scale");
                                            EntityArgument.getEntities(context, "entities").forEach(entity -> {
                                                if (!entity.isAlive())
                                                    return;
                                                LivingEntity livingEntity = (LivingEntity) entity;
                                                AttributeSupplier defaultAttributes = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) livingEntity.getType());
                                                for (Holder<Attribute> holder : ATTRIBUTES_TO_SCALE) {
                                                    AttributeInstance attr = livingEntity.getAttribute(holder);
                                                    if (attr != null)
                                                        attr.setBaseValue(defaultAttributes.getBaseValue(holder) * scale);
                                                }
                                            });
                                            return 1;
                                        }))));
    }
}
