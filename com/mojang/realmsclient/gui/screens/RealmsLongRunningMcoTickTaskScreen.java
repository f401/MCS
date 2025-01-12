package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsLongRunningMcoTickTaskScreen extends RealmsLongRunningMcoTaskScreen {
   private final LongRunningTask task;

   public RealmsLongRunningMcoTickTaskScreen(Screen pLastScreen, LongRunningTask pTask) {
      super(pLastScreen, pTask);
      this.task = pTask;
   }

   public void tick() {
      super.tick();
      this.task.tick();
   }

   protected void cancel() {
      this.task.abortTask();
      super.cancel();
   }
}