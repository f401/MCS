package net.minecraft.client.gui.components;

import javax.annotation.Nullable;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractContainerWidget extends AbstractWidget implements ContainerEventHandler {
   @Nullable
   private GuiEventListener focused;
   private boolean isDragging;

   public AbstractContainerWidget(int p_310492_, int p_309402_, int p_313085_, int p_312513_, Component p_310986_) {
      super(p_310492_, p_309402_, p_313085_, p_312513_, p_310986_);
   }

   /**
    * {@return {@code true} if the GUI element is dragging, {@code false} otherwise}
    */
   public final boolean isDragging() {
      return this.isDragging;
   }

   /**
    * Sets if the GUI element is dragging or not.
    * @param pIsDragging the dragging state of the GUI element.
    */
   public final void setDragging(boolean p_311596_) {
      this.isDragging = p_311596_;
   }

   /**
    * Gets the focused GUI element.
    */
   @Nullable
   public GuiEventListener getFocused() {
      return this.focused;
   }

   /**
    * Sets the focus state of the GUI element.
    * @param pFocused the focused GUI element.
    */
   public void setFocused(@Nullable GuiEventListener p_312828_) {
      if (this.focused != null) {
         this.focused.setFocused(false);
      }

      if (p_312828_ != null) {
         p_312828_.setFocused(true);
      }

      this.focused = p_312828_;
   }

   /**
    * Retrieves the next focus path based on the given focus navigation event.
    * <p>
    * @return the next focus path as a ComponentPath, or {@code null} if there is no next focus path.
    * @param pEvent the focus navigation event.
    */
   @Nullable
   public ComponentPath nextFocusPath(FocusNavigationEvent p_311207_) {
      return ContainerEventHandler.super.nextFocusPath(p_311207_);
   }

   /**
    * Called when a mouse button is clicked within the GUI element.
    * <p>
    * @return {@code true} if the event is consumed, {@code false} otherwise.
    * @param pMouseX the X coordinate of the mouse.
    * @param pMouseY the Y coordinate of the mouse.
    * @param pButton the button that was clicked.
    */
   public boolean mouseClicked(double p_312130_, double p_311814_, int p_312053_) {
      return ContainerEventHandler.super.mouseClicked(p_312130_, p_311814_, p_312053_);
   }

   /**
    * Called when a mouse button is released within the GUI element.
    * <p>
    * @return {@code true} if the event is consumed, {@code false} otherwise.
    * @param pMouseX the X coordinate of the mouse.
    * @param pMouseY the Y coordinate of the mouse.
    * @param pButton the button that was released.
    */
   public boolean mouseReleased(double p_311513_, double p_312630_, int p_310317_) {
      return ContainerEventHandler.super.mouseReleased(p_311513_, p_312630_, p_310317_);
   }

   /**
    * Called when the mouse is dragged within the GUI element.
    * <p>
    * @return {@code true} if the event is consumed, {@code false} otherwise.
    * @param pMouseX the X coordinate of the mouse.
    * @param pMouseY the Y coordinate of the mouse.
    * @param pButton the button that is being dragged.
    * @param pDragX the X distance of the drag.
    * @param pDragY the Y distance of the drag.
    */
   public boolean mouseDragged(double p_310748_, double p_313111_, int p_309710_, double p_312859_, double p_310378_) {
      return ContainerEventHandler.super.mouseDragged(p_310748_, p_313111_, p_309710_, p_312859_, p_310378_);
   }

   /**
    * {@return {@code true} if the GUI element is focused, {@code false} otherwise}
    */
   public boolean isFocused() {
      return ContainerEventHandler.super.isFocused();
   }

   /**
    * Sets the focus state of the GUI element.
    * @param pFocused {@code true} to apply focus, {@code false} to remove focus
    */
   public void setFocused(boolean p_310891_) {
      ContainerEventHandler.super.setFocused(p_310891_);
   }
}