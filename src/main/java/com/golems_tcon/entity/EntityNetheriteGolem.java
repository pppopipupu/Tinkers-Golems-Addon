package com.golems_tcon.entity;

import com.golems.entity.GolemBase;
import com.golems_tcon.init.TconGolems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.concurrent.CompletableFuture;

public class EntityNetheriteGolem extends GolemBase {
    private MinecraftServer server;
    public static final String ALLOW_SPECIAL = "Allow Special: Generate Explosion";
    public static final String DESTORY_TERRAIN = "Allow Special: Destroy Terrain";

    public EntityNetheriteGolem(World world) {
        super(world);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.isImmuneToFire = true;
        this.setLootTableLoc(TconGolems.MODID, "golem_netherite");

    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    protected ResourceLocation applyTexture() {
        return GolemBase.makeTexture(TconGolems.MODID, "golem_netherite");
    }

    @Override
    public SoundEvent getGolemSound() {
        return SoundEvents.BLOCK_ANVIL_PLACE;
    }

    @Override
    public ItemStack getPickedResult(final RayTraceResult target) {
        return null;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (this.getAttackingEntity() != null) {
            if (server == null) {
                server = this.world.getMinecraftServer();
            }
            if (server.getTickCounter() % 20 == 0 && !world.isRemote && this.getAttackingEntity() != null) {
                EntityLargeFireball fireball = new EntityLargeFireball(world);
                Vec3d shootVec = new Vec3d(-this.posX + this.getAttackingEntity().posX, -this.posY + this.getAttackingEntity().posY, -this.posZ + this.getAttackingEntity().posZ).normalize();
                double accelX =
                        shootVec.x * 2;
                double accelY =
                        shootVec.y * 2;
                double accelZ =
                        shootVec.z * 2;
                double posX = this.posX;
                double posY = this.posY + this.getEyeHeight();
                double posZ = this.posZ;
                fireball.setLocationAndAngles(posX, posY, posZ, fireball.rotationYaw, fireball.rotationPitch);
                fireball.setPosition(posX, posY, posZ);
                double d0 = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
                fireball.accelerationX = accelX / d0 * 0.1D;
                fireball.accelerationY = accelY / d0 * 0.1D;
                fireball.accelerationZ = accelZ / d0 * 0.1D;
                fireball.shootingEntity = this;
                fireball.explosionPower = 6;
                world.spawnEntity(fireball);


            }
            if (server.getTickCounter() % 60 == 0 && !world.isRemote && this.getAttackingEntity() != null) {
                this.heal(50);


            }

        }


    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (getConfig(this).getBoolean(ALLOW_SPECIAL)) {
                MinecraftServer server = this.getServer();
                CompletableFuture.runAsync(() -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        int finalI = i;
                        server.addScheduledTask(() -> {
                            entity.world.createExplosion((Entity) null, entity.posX, entity.posY + (finalI * 3) - 5, entity.posZ, 4.0F, getConfig(this).getBoolean(DESTORY_TERRAIN));
                            entity.addVelocity(0D, 1D, 0D);
                            //   entity.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.BLOCKS, 100.0F, 1.0F, false);
                        });
                    }

                });
                return true;
            }

        }
        return false;
    }
}
