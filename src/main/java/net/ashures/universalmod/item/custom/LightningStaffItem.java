package net.ashures.universalmod.item.custom;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightningStaffItem extends Item {
    public LightningStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        HitResult blockCheck = user.raycast(30, 1, true);
        BlockPos blockPos = new BlockPos(blockCheck.getPos());
        BlockState hitBlock = world.getBlockState(blockPos);

        if (hitBlock.getBlock().equals(Blocks.AIR))
        {
            hitBlock = world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ()));
        }

        if (!hitBlock.getBlock().equals(Blocks.AIR)) {
            castSpell(world, blockCheck.getPos(), user);
            return TypedActionResult.success(user.getMainHandStack(), true);
        } else {
            return super.use(world, user, hand);
        }
    }

    public void castSpell(World world, Vec3d spawnPos, PlayerEntity player) {
        BlockPos blockPos = new BlockPos(spawnPos);
        BlockState blockState = AbstractFireBlock.getState(world, blockPos);

        LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        entity.setPosition(spawnPos);

        if (world.getBlockState(blockPos).isAir() && blockState.canPlaceAt(world, blockPos))
            world.setBlockState(blockPos, blockState);

        world.spawnEntity(entity);
    }
}
