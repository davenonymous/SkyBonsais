package org.dave.skybonsais.integration;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.dave.bonsaitrees.api.*;

import org.dave.skybonsais.util.Logz;
import sky_orchards.blocks.EnumWood;

import java.util.Random;

@BonsaiIntegration(mod = "sky_orchards")
public class SkyOrchards implements IBonsaiIntegration {
    private void addItemStackDrop(TreeTypeSimple treeType, Block block, int amount, float chance) {
        if(amount <= 0 || chance <= 0) {
            return;
        }

        treeType.addDrop(new ItemStack(block, amount, 0), chance);
    }

    private void addItemStackDrop(TreeTypeSimple treeType, Item item, int amount, float chance) {
        if(amount <= 0 || chance <= 0) {
            return;
        }

        treeType.addDrop(new ItemStack(item, amount, 0), chance);
    }

    @Override
    public void registerTrees(ITreeTypeRegistry registry) {
        for(EnumWood wood : EnumWood.values()) {
            TreeTypeSimple treeType = new TreeTypeSimple("sky_orchard:" + wood.getName(), new ItemStack(wood.getSapling()));

            addItemStackDrop(treeType, wood.getSapling(), SkyOrchardsDropChances.saplingAmount, SkyOrchardsDropChances.saplingChance);
            addItemStackDrop(treeType, Items.STICK, SkyOrchardsDropChances.stickAmount, SkyOrchardsDropChances.stickChance);
            addItemStackDrop(treeType, wood.getResin(), SkyOrchardsDropChances.resinAmount, SkyOrchardsDropChances.resinChance);
            addItemStackDrop(treeType, Blocks.LOG, SkyOrchardsDropChances.logAmount, SkyOrchardsDropChances.logChance);
            addItemStackDrop(treeType, wood.getLeaves(), SkyOrchardsDropChances.leafAmount, SkyOrchardsDropChances.leafChance);
            addItemStackDrop(treeType, wood.getAcorn(), SkyOrchardsDropChances.acornAmount, SkyOrchardsDropChances.acornChance);
            addItemStackDrop(treeType, wood.getAmber(), SkyOrchardsDropChances.amberAmount, SkyOrchardsDropChances.amberChance);

            if (!wood.getLeafDrop().equalsIgnoreCase("unused") && !wood.getLeafDrop().equalsIgnoreCase("null")) {
                String[] finalEntry = wood.getLeafDrop().trim().split("#");
                if (finalEntry.length != 4) {
                    // This should not be happening since it is already checked by SkyOrchards in the first place.
                    continue;
                }

                ItemStack stack = new ItemStack((Item)Item.REGISTRY.getObject(new ResourceLocation(finalEntry[0])), Integer.valueOf(finalEntry[1]), Integer.valueOf(finalEntry[2]));
                int chanceInv = Integer.valueOf(finalEntry[3]);
                float chance = chanceInv == 0 ? 0.0f : 1.0f / chanceInv;

                treeType.addDrop(stack, chance);
            }

            registry.registerTreeType(this, treeType);
        }
    }

    @Override
    public void generateTree(IBonsaiTreeType type, World world, BlockPos pos, Random rand) {
        if(!(type.getExampleStack().getItem() instanceof ItemBlock)) {
            Logz.warn("Sapling is no ItemBlock! This should not be happening!");
            return;
        }

        Block sapling = ((ItemBlock) type.getExampleStack().getItem()).getBlock();
        if(!(sapling instanceof BlockSapling)) {
            Logz.info("Not an ore sapling");
            return;
        }

        world.setBlockState(pos, sapling.getDefaultState());

        BlockSapling oreSapling = (BlockSapling)sapling;
        oreSapling.generateTree(world, pos, null, rand);
    }
}
