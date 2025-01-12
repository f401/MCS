package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import net.minecraft.resources.ResourceKey;

public interface WritableRegistry<T> extends Registry<T> {
   Holder.Reference<T> register(ResourceKey<T> pKey, T pValue, Lifecycle pLifecycle);

   boolean isEmpty();

   HolderGetter<T> createRegistrationLookup();
}