package net.minecraft.client.gui.components;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.math.Axis;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class PlayerSkinWidget extends AbstractWidget {
   private static final float MODEL_OFFSET = 0.0625F;
   private static final float MODEL_HEIGHT = 2.125F;
   private static final float Z_OFFSET = 100.0F;
   private static final float ROTATION_SENSITIVITY = 2.5F;
   private static final float DEFAULT_ROTATION_X = -5.0F;
   private static final float DEFAULT_ROTATION_Y = 30.0F;
   private static final float ROTATION_X_LIMIT = 50.0F;
   private final PlayerSkinWidget.Model model;
   private final Supplier<PlayerSkin> skin;
   private float rotationX = -5.0F;
   private float rotationY = 30.0F;

   public PlayerSkinWidget(int pWidth, int pHeight, EntityModelSet pModel, Supplier<PlayerSkin> pSkin) {
      super(0, 0, pWidth, pHeight, CommonComponents.EMPTY);
      this.model = PlayerSkinWidget.Model.bake(pModel);
      this.skin = pSkin;
   }

   protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      pGuiGraphics.pose().pushPose();
      pGuiGraphics.pose().translate((float)this.getX() + (float)this.getWidth() / 2.0F, (float)(this.getY() + this.getHeight()), 100.0F);
      float f = (float)this.getHeight() / 2.125F;
      pGuiGraphics.pose().scale(f, f, f);
      pGuiGraphics.pose().translate(0.0F, -0.0625F, 0.0F);
      Matrix4f matrix4f = pGuiGraphics.pose().last().pose();
      matrix4f.rotateAround(Axis.XP.rotationDegrees(this.rotationX), 0.0F, -1.0625F, 0.0F);
      pGuiGraphics.pose().mulPose(Axis.YP.rotationDegrees(this.rotationY));
      this.model.render(pGuiGraphics, this.skin.get());
      pGuiGraphics.pose().popPose();
   }

   protected void onDrag(double pMouseX, double pMouseY, double pDragX, double pDragY) {
      this.rotationX = Mth.clamp(this.rotationX - (float)pDragY * 2.5F, -50.0F, 50.0F);
      this.rotationY += (float)pDragX * 2.5F;
   }

   public void playDownSound(SoundManager pHandler) {
   }

   protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
   }

   /**
    * {@return {@code true} if the element is active, {@code false} otherwise}
    */
   public boolean isActive() {
      return false;
   }

   /**
    * Retrieves the next focus path based on the given focus navigation event.
    * <p>
    * @return the next focus path as a ComponentPath, or {@code null} if there is no next focus path.
    * @param pEvent the focus navigation event.
    */
   @Nullable
   public ComponentPath nextFocusPath(FocusNavigationEvent pEvent) {
      return null;
   }

   @OnlyIn(Dist.CLIENT)
   static record Model(PlayerModel<?> wideModel, PlayerModel<?> slimModel) {
      public static PlayerSkinWidget.Model bake(EntityModelSet pModel) {
         PlayerModel<?> playermodel = new PlayerModel(pModel.bakeLayer(ModelLayers.PLAYER), false);
         PlayerModel<?> playermodel1 = new PlayerModel(pModel.bakeLayer(ModelLayers.PLAYER_SLIM), true);
         playermodel.young = false;
         playermodel1.young = false;
         return new PlayerSkinWidget.Model(playermodel, playermodel1);
      }

      public void render(GuiGraphics pGuiGraphics, PlayerSkin pSkin) {
         pGuiGraphics.flush();
         Lighting.setupForEntityInInventory();
         pGuiGraphics.pose().pushPose();
         pGuiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling(1.0F, 1.0F, -1.0F));
         pGuiGraphics.pose().translate(0.0F, -1.5F, 0.0F);
         PlayerModel<?> playermodel = pSkin.model() == PlayerSkin.Model.SLIM ? this.slimModel : this.wideModel;
         RenderType rendertype = playermodel.renderType(pSkin.texture());
         playermodel.renderToBuffer(pGuiGraphics.pose(), pGuiGraphics.bufferSource().getBuffer(rendertype), 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
         pGuiGraphics.pose().popPose();
         pGuiGraphics.flush();
         Lighting.setupFor3DItems();
      }
   }
}