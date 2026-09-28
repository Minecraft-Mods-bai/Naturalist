package com.crispytwig.naturalist.world.level.modifiers;

import com.crispytwig.naturalist.NaturalistConfig;
import com.crispytwig.naturalist.neoforge.registry.NaturalistBiomeModifiers;
import com.crispytwig.naturalist.world.level.NaturalistSpawns;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class AddAnimalsBiomeModifier implements BiomeModifier {

  @Override
  public void modify(
      @NonNull RegistryAccess registries,
      @NonNull Holder<Biome> biome,
      Phase phase,
      ModifiableBiomeInfo.BiomeInfo.@NonNull Builder builder) {
    if (phase.equals(Phase.ADD)) {
      NaturalistSpawns.forEachSpawn(
          (hasTag, blacklistTag, category, type, weight, min, max) -> {
            if (NaturalistConfig.isRemoved(type)) {
              return;
            }
            if (biome.is(hasTag) && (blacklistTag == null || !biome.is(blacklistTag))) {
              builder
                  .getMobSpawnSettings()
                  .addSpawn(type, category, weight, UniformInt.of(min, max));
            }
          });
    }
  }

  @Override
  public @NotNull MapCodec<? extends BiomeModifier> codec() {
    return NaturalistBiomeModifiers.ADD_ANIMALS_CODEC.get();
  }
}
