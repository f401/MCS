package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.timers.FunctionCallback;
import net.minecraft.world.level.timers.FunctionTagCallback;
import net.minecraft.world.level.timers.TimerQueue;

public class ScheduleCommand {
   private static final SimpleCommandExceptionType ERROR_SAME_TICK = new SimpleCommandExceptionType(Component.translatable("commands.schedule.same_tick"));
   private static final DynamicCommandExceptionType ERROR_CANT_REMOVE = new DynamicCommandExceptionType((p_308808_) -> {
      return Component.translatableEscape("commands.schedule.cleared.failure", p_308808_);
   });
   private static final SuggestionProvider<CommandSourceStack> SUGGEST_SCHEDULE = (p_138424_, p_138425_) -> {
      return SharedSuggestionProvider.suggest(p_138424_.getSource().getServer().getWorldData().overworldData().getScheduledEvents().getEventsIds(), p_138425_);
   };

   public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
      pDispatcher.register(Commands.literal("schedule").requires((p_138427_) -> {
         return p_138427_.hasPermission(2);
      }).then(Commands.literal("function").then(Commands.argument("function", FunctionArgument.functions()).suggests(FunctionCommand.SUGGEST_FUNCTION).then(Commands.argument("time", TimeArgument.time()).executes((p_138459_) -> {
         return schedule(p_138459_.getSource(), FunctionArgument.getFunctionOrTag(p_138459_, "function"), IntegerArgumentType.getInteger(p_138459_, "time"), true);
      }).then(Commands.literal("append").executes((p_138457_) -> {
         return schedule(p_138457_.getSource(), FunctionArgument.getFunctionOrTag(p_138457_, "function"), IntegerArgumentType.getInteger(p_138457_, "time"), false);
      })).then(Commands.literal("replace").executes((p_138455_) -> {
         return schedule(p_138455_.getSource(), FunctionArgument.getFunctionOrTag(p_138455_, "function"), IntegerArgumentType.getInteger(p_138455_, "time"), true);
      }))))).then(Commands.literal("clear").then(Commands.argument("function", StringArgumentType.greedyString()).suggests(SUGGEST_SCHEDULE).executes((p_138422_) -> {
         return remove(p_138422_.getSource(), StringArgumentType.getString(p_138422_, "function"));
      }))));
   }

   private static int schedule(CommandSourceStack pSource, Pair<ResourceLocation, Either<CommandFunction<CommandSourceStack>, Collection<CommandFunction<CommandSourceStack>>>> pFunction, int pTime, boolean pAppend) throws CommandSyntaxException {
      if (pTime == 0) {
         throw ERROR_SAME_TICK.create();
      } else {
         long i = pSource.getLevel().getGameTime() + (long)pTime;
         ResourceLocation resourcelocation = pFunction.getFirst();
         TimerQueue<MinecraftServer> timerqueue = pSource.getServer().getWorldData().overworldData().getScheduledEvents();
         pFunction.getSecond().ifLeft((p_312526_) -> {
            String s = resourcelocation.toString();
            if (pAppend) {
               timerqueue.remove(s);
            }

            timerqueue.schedule(s, i, new FunctionCallback(resourcelocation));
            pSource.sendSuccess(() -> {
               return Component.translatable("commands.schedule.created.function", Component.translationArg(resourcelocation), pTime, i);
            }, true);
         }).ifRight((p_288548_) -> {
            String s = "#" + resourcelocation;
            if (pAppend) {
               timerqueue.remove(s);
            }

            timerqueue.schedule(s, i, new FunctionTagCallback(resourcelocation));
            pSource.sendSuccess(() -> {
               return Component.translatable("commands.schedule.created.tag", Component.translationArg(resourcelocation), pTime, i);
            }, true);
         });
         return Math.floorMod(i, Integer.MAX_VALUE);
      }
   }

   private static int remove(CommandSourceStack pSource, String pFunction) throws CommandSyntaxException {
      int i = pSource.getServer().getWorldData().overworldData().getScheduledEvents().remove(pFunction);
      if (i == 0) {
         throw ERROR_CANT_REMOVE.create(pFunction);
      } else {
         pSource.sendSuccess(() -> {
            return Component.translatable("commands.schedule.cleared.success", i, pFunction);
         }, true);
         return i;
      }
   }
}