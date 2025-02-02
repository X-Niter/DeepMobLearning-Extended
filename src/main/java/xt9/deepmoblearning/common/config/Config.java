package xt9.deepmoblearning.common.config;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;
import xt9.deepmoblearning.common.mobmetas.MobKey;
import xt9.deepmoblearning.common.util.ItemStackNBTHelper;

import java.io.File;

/**
 * Created by xt9 on 2017-06-08.
 */
@Mod.EventBusSubscriber
public class Config {
    private static Configuration config = new DMLConfiguration(new File("config/" + DeepConstants.MODID + ".cfg"));
    public static ConfigCategory dataModel = new ConfigCategory("data model simulation costs");
    public static ConfigCategory dataModelMobNames = new ConfigCategory("data model mob names");
    public static ConfigCategory pristineChance = new ConfigCategory("pristine matter chance");
    public static ConfigCategory modelExperience = new ConfigCategory("model experience tweaks");
    public static ConfigCategory pristineOutputs = new ConfigCategory("pristine output items");
    public static ConfigCategory livingMatterEXP = new ConfigCategory("matter experience");
    public static ConfigCategory trialRewards = new ConfigCategory("trial max tier rewards");

    public static Property rfCostExtractionChamber;
    public static Property rfStorageExtractionChamber;
    public static Property rfInputExtractionChamber;
    public static Property rfStorageSimulationChamber;
    public static Property rfInputSimulationChamber;
    public static Property isSootedRedstoneCraftingEnabled;
    public static Property isGlitchArmorCreativeFlightEnabled;

    public static Property guiOverlaySide;
    public static Property guiOverlayVerticalSpacing;
    public static Property guiOverlayHorizontalSpacing;

    public static void load() {
        config.load();
    }

    public static void initConfigValues() {
        initLivingMatterEXP();
        initDataModelRFCost();
        initDataModelMobs();
        initPristineChance();
        initModelExperience();
        initPristineOutputs();
        initTrialRewards();

        rfCostExtractionChamber = config.get(Configuration.CATEGORY_GENERAL, "rfCostLootFabricator", 256, "Rf per tic (Tic is 1 second) cost for the Loot Fabricator, Max is 999999999 RF/t");
        rfStorageExtractionChamber = config.get(Configuration.CATEGORY_GENERAL, "rfStorageLootFabricator", 8000000, "RF Storage for the Loot Fabricator, Max is 999999999");
        rfInputExtractionChamber = config.get(Configuration.CATEGORY_GENERAL, "rfInputLootFabricator", 256000, "Rf Input per tic (Tic is 1 second) for the Loot Fabricator, Max is 999999999");

        rfStorageSimulationChamber = config.get(Configuration.CATEGORY_GENERAL, "rfStorageSimulationChamber", 8000000, "RF Storage for the Simulation Chamber, Max is 999999999");
        rfInputSimulationChamber = config.get(Configuration.CATEGORY_GENERAL, "rfInputSimulationChamber", 256000, "Rf Input per tic (Tic is 1 second) for the Simulation Chamber, Max is 999999999");

        isSootedRedstoneCraftingEnabled = config.get(Configuration.CATEGORY_GENERAL, "isSootedRedstoneCraftingEnabled", true, "Enable the Crafting of sooted redstone on Vanilla blocks of coal");
        isGlitchArmorCreativeFlightEnabled = config.get(Configuration.CATEGORY_GENERAL, "isGlitchArmorCreativeFlightEnabled", true, "Set to false to disable the creative flight portion of the Glitch armorset.");

        guiOverlaySide = config.get(Configuration.CATEGORY_GENERAL, "guiOverlaySide", "topleft", "Which position on the screen the Deep learner gui will appear on. (bottomleft will clash with the chat) [values: topleft/topright/bottomleft/bottomright]");
        guiOverlayVerticalSpacing = config.get(Configuration.CATEGORY_GENERAL, "guiOverlayVerticalSpacing", 0, "Vertical spacing from the selected corner, values can be both positive and negative");
        guiOverlayHorizontalSpacing = config.get(Configuration.CATEGORY_GENERAL, "guiOverlayHorizontalSpacing", 0, "Horizontal spacing from the selected corner, values can be both positive and negative");
        config.save();
    }

    private static void initLivingMatterEXP() {
        livingMatterEXP.setComment("Experience values for the different \"realm\" matters, maxValue: 999");
        config.setCategoryComment(livingMatterEXP.getName(), livingMatterEXP.getComment());

        livingMatterEXP.put("overworldian", config.get(livingMatterEXP.getName(), "overworldian", 10, null, 1, 999));
        livingMatterEXP.put("hellish", config.get(livingMatterEXP.getName(), "hellish", 14, null, 1, 999));
        livingMatterEXP.put("extraterrestrial", config.get(livingMatterEXP.getName(), "extraterrestrial", 20, null, 1, 999));

        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            livingMatterEXP.put("twilight", config.get(livingMatterEXP.getName(), "twilight", 30, null, 1, 999));
        }
    }

    private static void initDataModelRFCost() {
        dataModel.setComment("Simulation costs for all the data models (in RF/t)\nCost should not exceed a full machine buffer (Max 66666666 RF/t)\nValues over the max will be set to the max");
        config.setCategoryComment(dataModel.getName(), dataModel.getComment());

        dataModel.put(MobKey.ZOMBIE, config.get(dataModel.getName(), MobKey.ZOMBIE, 80, null, 1, 66666666));
        dataModel.put(MobKey.SKELETON, config.get(dataModel.getName(), MobKey.SKELETON, 80, null, 1, 66666666));
        dataModel.put(MobKey.BLAZE, config.get(dataModel.getName(), MobKey.BLAZE, 256, null, 1, 66666666));
        dataModel.put(MobKey.ENDERMAN, config.get(dataModel.getName(), MobKey.ENDERMAN, 512, null, 1, 66666666));
        dataModel.put(MobKey.WITHER, config.get(dataModel.getName(), MobKey.WITHER, 2048, null, 1, 66666666));
        dataModel.put(MobKey.WITCH, config.get(dataModel.getName(), MobKey.WITCH, 120, null, 1, 66666666));
        dataModel.put(MobKey.SPIDER, config.get(dataModel.getName(), MobKey.SPIDER, 80, null, 1, 66666666));
        dataModel.put(MobKey.CREEPER, config.get(dataModel.getName(), MobKey.CREEPER, 80, null, 1, 66666666));
        dataModel.put(MobKey.GHAST, config.get(dataModel.getName(), MobKey.GHAST, 372, null, 1, 66666666));
        dataModel.put(MobKey.SLIME, config.get(dataModel.getName(), MobKey.SLIME, 150, null, 1, 66666666));
        dataModel.put(MobKey.DRAGON, config.get(dataModel.getName(), MobKey.DRAGON, 2560, null, 1, 66666666));
        dataModel.put(MobKey.SHULKER, config.get(dataModel.getName(), MobKey.SHULKER, 256, null, 1, 66666666));
        dataModel.put(MobKey.GUARDIAN, config.get(dataModel.getName(), MobKey.GUARDIAN, 340,null, 1, 66666666));
        dataModel.put(MobKey.WITHERSKELETON, config.get(dataModel.getName(), MobKey.WITHERSKELETON, 880,null, 1, 66666666));

        /* Extension models */
        if(DeepConstants.MOD_TE_LOADED) {
            dataModel.put(MobKey.TE, config.get(dataModel.getName(), MobKey.TE, 256,null, 1, 256000));
        }
        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            dataModel.put(MobKey.TWILIGHTFOREST, config.get(dataModel.getName(), MobKey.TWILIGHTFOREST, 256,null, 1, 256000));
            dataModel.put(MobKey.TWILIGHTSWAMP, config.get(dataModel.getName(), MobKey.TWILIGHTSWAMP, 256,null, 1, 256000));
            dataModel.put(MobKey.TWILIGHTDARKWOOD, config.get(dataModel.getName(), MobKey.TWILIGHTDARKWOOD, 256,null, 1, 256000));
            dataModel.put(MobKey.TWILIGHTGLACIER, config.get(dataModel.getName(), MobKey.TWILIGHTGLACIER, 256,null, 1, 256000));
        }
        if(DeepConstants.MOD_TCON_LOADED) {
            dataModel.put(MobKey.TINKERSLIME, config.get(dataModel.getName(), MobKey.TINKERSLIME, 256,null, 1, 256000));
        }
        if(DeepConstants.MOD_MO_LOADED) {
            dataModel.put(MobKey.MO_ANDROID, config.get(dataModel.getName(), MobKey.MO_ANDROID, 256,null, 1, 256000));
        }
    }

    private static void initDataModelMobs() {
        dataModelMobNames.setComment("Register entities that count towards leveling up the model\nFormat is modname:entity_name");
        config.setCategoryComment(dataModelMobNames.getName(), dataModelMobNames.getComment());

        dataModelMobNames.put(MobKey.ZOMBIE, new Property(MobKey.ZOMBIE, config.getStringList(MobKey.ZOMBIE, dataModelMobNames.getName(), DeepConstants.MOBS.ZOMBIE, "Zombie"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.SKELETON, new Property(MobKey.SKELETON, config.getStringList(MobKey.SKELETON, dataModelMobNames.getName(), DeepConstants.MOBS.SKELETON, "Creeper"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.BLAZE, new Property(MobKey.BLAZE, config.getStringList(MobKey.BLAZE, dataModelMobNames.getName(), DeepConstants.MOBS.BLAZE, "Blaze"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.ENDERMAN, new Property(MobKey.ENDERMAN, config.getStringList(MobKey.ENDERMAN, dataModelMobNames.getName(), DeepConstants.MOBS.ENDERMAN, "Enderman"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.WITHER, new Property(MobKey.WITHER, config.getStringList(MobKey.WITHER, dataModelMobNames.getName(), DeepConstants.MOBS.WITHER, "Wither"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.WITCH, new Property(MobKey.WITCH, config.getStringList(MobKey.WITCH, dataModelMobNames.getName(), DeepConstants.MOBS.WITCH, "Witch"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.SPIDER, new Property(MobKey.SPIDER, config.getStringList(MobKey.SPIDER, dataModelMobNames.getName(), DeepConstants.MOBS.SPIDER, "Spider"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.CREEPER, new Property(MobKey.CREEPER, config.getStringList(MobKey.CREEPER, dataModelMobNames.getName(), DeepConstants.MOBS.CREEPER, "Creeper"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.GHAST, new Property(MobKey.GHAST, config.getStringList(MobKey.GHAST, dataModelMobNames.getName(), DeepConstants.MOBS.GHAST, "Ghast"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.SLIME, new Property(MobKey.SLIME, config.getStringList(MobKey.SLIME, dataModelMobNames.getName(), DeepConstants.MOBS.SLIME, "Slime"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.DRAGON, new Property(MobKey.DRAGON, config.getStringList(MobKey.DRAGON, dataModelMobNames.getName(), DeepConstants.MOBS.DRAGON, "Dragon"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.SHULKER, new Property(MobKey.SHULKER, config.getStringList(MobKey.SHULKER, dataModelMobNames.getName(), DeepConstants.MOBS.SHULKER, "Shulker"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.GUARDIAN, new Property(MobKey.GUARDIAN, config.getStringList(MobKey.GUARDIAN, dataModelMobNames.getName(), DeepConstants.MOBS.GUARDIAN, "Guardian"), Property.Type.STRING));
        dataModelMobNames.put(MobKey.WITHERSKELETON, new Property(MobKey.WITHERSKELETON, config.getStringList(MobKey.WITHERSKELETON, dataModelMobNames.getName(), DeepConstants.MOBS.WITHERSKELETON, "Wither Skeleton"), Property.Type.STRING));

        //Vanilla Passive Entities
        dataModelMobNames.put(MobKey.SHEEP, new Property(MobKey.SHEEP, config.getStringList(MobKey.SHEEP, dataModelMobNames.getName(), DeepConstants.MOBS.SHEEP, "Sheep"), Property.Type.STRING));

        /* Extension models */
        if(DeepConstants.MOD_TE_LOADED) {
            dataModelMobNames.put(MobKey.TE, new Property(MobKey.TE, config.getStringList(MobKey.TE, dataModelMobNames.getName(), DeepConstants.MOBS.THERMALELEMENTAL, "Thermal Elemental"), Property.Type.STRING));
        }
        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            dataModelMobNames.put(MobKey.TWILIGHTFOREST, new Property(MobKey.TWILIGHTFOREST, config.getStringList(MobKey.TWILIGHTFOREST, dataModelMobNames.getName(), DeepConstants.MOBS.TWILIGHTFOREST, "Twilight Forest(Biome, not the whole mod)"), Property.Type.STRING));
            dataModelMobNames.put(MobKey.TWILIGHTSWAMP, new Property(MobKey.TWILIGHTSWAMP, config.getStringList(MobKey.TWILIGHTSWAMP, dataModelMobNames.getName(), DeepConstants.MOBS.TWILIGHTSWAMP, "Twilight Swamp creatures"), Property.Type.STRING));
            dataModelMobNames.put(MobKey.TWILIGHTDARKWOOD, new Property(MobKey.TWILIGHTDARKWOOD, config.getStringList(MobKey.TWILIGHTDARKWOOD, dataModelMobNames.getName(), DeepConstants.MOBS.TWILIGHTDARKWOOD, "Twilight Darkwood creatures"), Property.Type.STRING));
            dataModelMobNames.put(MobKey.TWILIGHTGLACIER, new Property(MobKey.TWILIGHTGLACIER, config.getStringList(MobKey.TWILIGHTGLACIER, dataModelMobNames.getName(), DeepConstants.MOBS.TWILIGHTGLACIER, "Twilight Glacier creatures"), Property.Type.STRING));
        }
        if(DeepConstants.MOD_TCON_LOADED) {
            dataModelMobNames.put(MobKey.TINKERSLIME, new Property(MobKey.TINKERSLIME, config.getStringList(MobKey.TINKERSLIME, dataModelMobNames.getName(), DeepConstants.MOBS.TINKERSLIME, "Tinker construct slime"), Property.Type.STRING));
        }
        if(DeepConstants.MOD_MO_LOADED) {
            dataModelMobNames.put(MobKey.MO_ANDROID, new Property(MobKey.MO_ANDROID, config.getStringList(MobKey.MO_ANDROID, dataModelMobNames.getName(), DeepConstants.MOBS.MOAndroid, "Matter Overdrive Androids"), Property.Type.STRING));
        }
    }

    private static void initPristineChance() {
        pristineChance.setComment("The chance to output pristine matter from the various tiers\nStarts at basic since faulty models can't be used in the simulation chamber");
        config.setCategoryComment(pristineChance.getName(), pristineChance.getComment());

        pristineChance.put("tier1", config.get(pristineChance.getName(), "tier1", 4, null, 1, 100));
        pristineChance.put("tier2", config.get(pristineChance.getName(), "tier2", 8, null, 1, 100));
        pristineChance.put("tier3", config.get(pristineChance.getName(), "tier3", 12, null, 1, 100));
        pristineChance.put("tier4", config.get(pristineChance.getName(), "tier4", 16, null, 1, 100));
        pristineChance.put("tier5", config.get(pristineChance.getName(), "tier5", 20, null, 1, 100));
        pristineChance.put("tier6", config.get(pristineChance.getName(), "tier6", 24, null, 1, 100));
        pristineChance.put("tier7", config.get(pristineChance.getName(), "tier7", 28, null, 1, 100));
        pristineChance.put("tier8", config.get(pristineChance.getName(), "tier8", 32, null, 1, 100));
        pristineChance.put("tier9", config.get(pristineChance.getName(), "tier9", 36, null, 1, 100));
        pristineChance.put("tier10", config.get(pristineChance.getName(), "tier10", 40, null, 1, 100));
        pristineChance.put("tier11", config.get(pristineChance.getName(), "tier11", 44, null, 1, 100));
        pristineChance.put("tier12", config.get(pristineChance.getName(), "tier12", 48, null, 1, 100));
        pristineChance.put("tier13", config.get(pristineChance.getName(), "tier13", 52, null, 1, 100));
        pristineChance.put("tier14", config.get(pristineChance.getName(), "tier14", 56, null, 1, 100));
        pristineChance.put("tier15", config.get(pristineChance.getName(), "tier15", 60, null, 1, 100));
        pristineChance.put("tier16", config.get(pristineChance.getName(), "tier16", 70, null, 1, 100));
    }

    private static void initModelExperience() {
        modelExperience.setComment("DISCLAIMER: Please tweak these values responsibly if you're building modpacks for the public, This mods intent is not to be grindy or \"timegated\"\nRemember that a high kill multiplier devalues how much you can use the simulation chamber for leveling\nFor example, a killmultiplier of 100 and 2 required kills to next tier is the equivalent of 200 simulations\nData formula: killmultiplier * requiredKills\nMax multiplier:10000 \nMax kills to next tier: 200000");
        config.setCategoryComment(modelExperience.getName(), modelExperience.getComment());

        modelExperience.put("killMultiplierTier0", config.get(modelExperience.getName(), "killMultiplierTier0", 1, "How much data you get per kill on the Faulty tier (It's Best to leave this at 1, as a baseline)", 1, 10000));
        modelExperience.put("killMultiplierTier1", config.get(modelExperience.getName(), "killMultiplierTier1", 2, "How much data you get per kill on the Unrefined tier", 1, 10000));
        modelExperience.put("killMultiplierTier2", config.get(modelExperience.getName(), "killMultiplierTier2", 4, "How much data you get per kill on the Basic tier", 1, 10000));
        modelExperience.put("killMultiplierTier3", config.get(modelExperience.getName(), "killMultiplierTier3", 6, "How much data you get per kill on the Common tier", 1, 10000));
        modelExperience.put("killMultiplierTier4", config.get(modelExperience.getName(), "killMultiplierTier4", 8, "How much data you get per kill on the Formal tier", 1, 10000));
        modelExperience.put("killMultiplierTier5", config.get(modelExperience.getName(), "killMultiplierTier5", 16, "How much data you get per kill on the Uncommon tier", 1, 10000));
        modelExperience.put("killMultiplierTier6", config.get(modelExperience.getName(), "killMultiplierTier6", 24, "How much data you get per kill on the Fixed tier", 1, 10000));
        modelExperience.put("killMultiplierTier7", config.get(modelExperience.getName(), "killMultiplierTier7", 32, "How much data you get per kill on the Advanced tier", 1, 10000));
        modelExperience.put("killMultiplierTier8", config.get(modelExperience.getName(), "killMultiplierTier8", 64, "How much data you get per kill on the Superior tier", 1, 10000));
        modelExperience.put("killMultiplierTier9", config.get(modelExperience.getName(), "killMultiplierTier9", 96, "How much data you get per kill on the Rare tier", 1, 10000));
        modelExperience.put("killMultiplierTier10", config.get(modelExperience.getName(), "killMultiplierTier10", 128, "How much data you get per kill on the Reflecting tier", 1, 10000));
        modelExperience.put("killMultiplierTier11", config.get(modelExperience.getName(), "killMultiplierTier11", 192, "How much data you get per kill on the Rumored tier", 1, 10000));
        modelExperience.put("killMultiplierTier12", config.get(modelExperience.getName(), "killMultiplierTier12", 256, "How much data you get per kill on the Epic tier", 1, 10000));
        modelExperience.put("killMultiplierTier13", config.get(modelExperience.getName(), "killMultiplierTier13", 384, "How much data you get per kill on the Radiant tier", 1, 10000));
        modelExperience.put("killMultiplierTier14", config.get(modelExperience.getName(), "killMultiplierTier14", 512, "How much data you get per kill on the Self Aware tier", 1, 10000));
        modelExperience.put("killMultiplierTier15", config.get(modelExperience.getName(), "killMultiplierTier15", 1024, "How much data you get per kill on the Legendary tier", 1, 10000));

        modelExperience.put("killsToTier1", config.get(modelExperience.getName(), "killsToTier1", 6, "Number of kills to reach the Unrefined tier.", 1, 200000));
        modelExperience.put("killsToTier2", config.get(modelExperience.getName(), "killsToTier2", 12, "Number of kills to reach the Basic tier.", 1, 200000));
        modelExperience.put("killsToTier3", config.get(modelExperience.getName(), "killsToTier3", 24, "Number of kills to reach the Common tier.", 1, 200000));
        modelExperience.put("killsToTier4", config.get(modelExperience.getName(), "killsToTier4", 48, "Number of kills to reach the Formal tier.", 1, 200000));
        modelExperience.put("killsToTier5", config.get(modelExperience.getName(), "killsToTier5", 96, "Number of kills to reach the Uncommon tier.", 1, 200000));
        modelExperience.put("killsToTier6", config.get(modelExperience.getName(), "killsToTier6", 192, "Number of kills to reach the Fixed tier.", 1, 200000));
        modelExperience.put("killsToTier7", config.get(modelExperience.getName(), "killsToTier7", 384, "Number of kills to reach the Advanced tier.", 1, 200000));
        modelExperience.put("killsToTier8", config.get(modelExperience.getName(), "killsToTier8", 768, "Number of kills to reach the Superior tier.", 1, 200000));
        modelExperience.put("killsToTier9", config.get(modelExperience.getName(), "killsToTier9", 1536, "Number of kills to reach the Rare tier.", 1, 200000));
        modelExperience.put("killsToTier10", config.get(modelExperience.getName(), "killsToTier10", 3072, "Number of kills to reach the Reflecting tier.", 1, 200000));
        modelExperience.put("killsToTier11", config.get(modelExperience.getName(), "killsToTier11", 6144, "Number of kills to reach the Rumored tier.", 1, 200000));
        modelExperience.put("killsToTier12", config.get(modelExperience.getName(), "killsToTier12", 12288, "Number of kills to reach the Epic tier.", 1, 200000));
        modelExperience.put("killsToTier13", config.get(modelExperience.getName(), "killsToTier13", 24576, "Number of kills to reach the Radiant tier.", 1, 200000));
        modelExperience.put("killsToTier14", config.get(modelExperience.getName(), "killsToTier14", 49152, "Number of kills to reach the Self Aware tier.", 1, 200000));
        modelExperience.put("killsToTier15", config.get(modelExperience.getName(), "killsToTier15", 98304, "Number of kills to reach the Legendary tier.", 1, 200000));
        modelExperience.put("killsToTier16", config.get(modelExperience.getName(), "killsToTier16", 196608, "Number of kills to reach the Perfect tier.", 1, 200000));
    }

    private static void initPristineOutputs() {
        pristineOutputs.setComment("Entries for Loot fabricator outputs from the different pristine items.\nInput format \"minecraft:coal,64,0\"\nWhere minecraft:coal is the registryName, 64 is the amount and 0 is the damagevalue/meta. \",\" is the delimiter.\nOptionally supports NBT tags, example:\ndeepmoblearning:glitch_infused_sword,1,0,{display:{Name:\"Not a sword really\"}}");
        config.setCategoryComment(pristineOutputs.getName(), pristineOutputs.getComment());

        pristineOutputs.put(MobKey.BLAZE, new Property(MobKey.BLAZE, config.getStringList(MobKey.BLAZE, pristineOutputs.getName(), DeepConstants.LOOT.BLAZE, "Blaze"), Property.Type.STRING));
        pristineOutputs.put(MobKey.CREEPER, new Property(MobKey.CREEPER, config.getStringList(MobKey.CREEPER, pristineOutputs.getName(), DeepConstants.LOOT.CREEPER, "Creeper"), Property.Type.STRING));
        pristineOutputs.put(MobKey.DRAGON, new Property(MobKey.DRAGON, config.getStringList(MobKey.DRAGON, pristineOutputs.getName(), DeepConstants.LOOT.DRAGON, "Dragon"), Property.Type.STRING));
        pristineOutputs.put(MobKey.ENDERMAN, new Property(MobKey.ENDERMAN, config.getStringList(MobKey.ENDERMAN, pristineOutputs.getName(), DeepConstants.LOOT.ENDERMAN, "Enderman"), Property.Type.STRING));
        pristineOutputs.put(MobKey.GHAST, new Property(MobKey.GHAST, config.getStringList(MobKey.GHAST, pristineOutputs.getName(), DeepConstants.LOOT.GHAST, "Ghast"), Property.Type.STRING));
        pristineOutputs.put(MobKey.SKELETON, new Property(MobKey.SKELETON, config.getStringList(MobKey.SKELETON, pristineOutputs.getName(), DeepConstants.LOOT.SKELETON, "Skeleton"), Property.Type.STRING));
        pristineOutputs.put(MobKey.SLIME, new Property(MobKey.SLIME, config.getStringList(MobKey.SLIME, pristineOutputs.getName(), DeepConstants.LOOT.SLIME, "Slime"), Property.Type.STRING));
        pristineOutputs.put(MobKey.SPIDER, new Property(MobKey.SPIDER, config.getStringList(MobKey.SPIDER, pristineOutputs.getName(), DeepConstants.LOOT.SPIDER, "Spider"), Property.Type.STRING));
        pristineOutputs.put(MobKey.WITCH, new Property(MobKey.WITCH, config.getStringList(MobKey.WITCH, pristineOutputs.getName(), DeepConstants.LOOT.WITCH, "Witch"), Property.Type.STRING));
        pristineOutputs.put(MobKey.WITHERSKELETON, new Property(MobKey.WITHERSKELETON, config.getStringList(MobKey.WITHERSKELETON, pristineOutputs.getName(), DeepConstants.LOOT.WITHERSKELETON, "Wither Skeleton"), Property.Type.STRING));
        pristineOutputs.put(MobKey.WITHER, new Property(MobKey.WITHER, config.getStringList(MobKey.WITHER, pristineOutputs.getName(), DeepConstants.LOOT.WITHER, "Wither"), Property.Type.STRING));
        pristineOutputs.put(MobKey.ZOMBIE, new Property(MobKey.ZOMBIE, config.getStringList(MobKey.ZOMBIE, pristineOutputs.getName(), DeepConstants.LOOT.ZOMBIE, "Zombie"), Property.Type.STRING));
        pristineOutputs.put(MobKey.SHULKER, new Property(MobKey.SHULKER, config.getStringList(MobKey.SHULKER, pristineOutputs.getName(), DeepConstants.LOOT.SHULKER, "Shulker"), Property.Type.STRING));
        pristineOutputs.put(MobKey.GUARDIAN, new Property(MobKey.GUARDIAN, config.getStringList(MobKey.GUARDIAN, pristineOutputs.getName(), DeepConstants.LOOT.GUARDIAN, "Guardian"), Property.Type.STRING));

        //Vanilla Passive Entities
        pristineOutputs.put(MobKey.SHEEP, new Property(MobKey.SHEEP, config.getStringList(MobKey.SHEEP, pristineOutputs.getName(), DeepConstants.LOOT.SHEEP, "Guardian"), Property.Type.STRING));

        if(DeepConstants.MOD_TE_LOADED) {
            pristineOutputs.put(MobKey.TE, new Property(MobKey.TE, config.getStringList(MobKey.TE, pristineOutputs.getName(), DeepConstants.LOOT.THERMALELEMENTAL, "Thermal Elemental"), Property.Type.STRING));
        }

        if(DeepConstants.MOD_TCON_LOADED) {
            pristineOutputs.put(MobKey.TINKERSLIME, new Property(MobKey.TINKERSLIME, config.getStringList(MobKey.TINKERSLIME, pristineOutputs.getName(), DeepConstants.LOOT.TINKERSLIME, "Tinker construct slime"), Property.Type.STRING));
        }

        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            pristineOutputs.put(MobKey.TWILIGHTFOREST, new Property(MobKey.TWILIGHTFOREST, config.getStringList(MobKey.TWILIGHTFOREST, pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTFOREST, "Twilight Forest(Biome, not the whole mod)"), Property.Type.STRING));
            pristineOutputs.put(MobKey.TWILIGHTSWAMP, new Property(MobKey.TWILIGHTSWAMP, config.getStringList(MobKey.TWILIGHTSWAMP, pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTSWAMP, "Twilight Swamp creatures"), Property.Type.STRING));
            pristineOutputs.put(MobKey.TWILIGHTDARKWOOD, new Property(MobKey.TWILIGHTDARKWOOD, config.getStringList(MobKey.TWILIGHTDARKWOOD, pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTDARKWOOD, "Twilight Darkwood creatures"), Property.Type.STRING));
            pristineOutputs.put(MobKey.TWILIGHTGLACIER, new Property(MobKey.TWILIGHTGLACIER, config.getStringList(MobKey.TWILIGHTGLACIER, pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTGLACIER, "Twilight Glacier creatures"), Property.Type.STRING));
        }

        if(DeepConstants.MOD_MO_LOADED) {
            pristineOutputs.put(MobKey.MO_ANDROID, new Property(MobKey.MO_ANDROID, config.getStringList(MobKey.MO_ANDROID, pristineOutputs.getName(), DeepConstants.LOOT.MOAndroid, "Matter Overdrive Androids"), Property.Type.STRING));
        }
    }

    private static void initTrialRewards() {
        trialRewards.setComment("Rewards for the Max tier of trials.\nCAUTION: Max 3 items per list, anything after that will be trimmed. \nInput format \"minecraft:coal,64,0\"\nWhere minecraft:coal is the registryName, 64 is the amount and 0 is the damagevalue/meta. \",\" is the delimiter.\nOptionally supports NBT tags, example:\ndeepmoblearning:glitch_infused_sword,1,0,{display:{Name:\"Not a sword really\"}}");
        config.setCategoryComment(trialRewards.getName(), trialRewards.getComment());

        trialRewards.put(MobKey.ZOMBIE, new Property(MobKey.ZOMBIE, config.getStringList(MobKey.ZOMBIE, trialRewards.getName(), DeepConstants.TRIAL_REWARD.ZOMBIE, "Zombie Trial Reward"), Property.Type.STRING));
        trialRewards.put(MobKey.ENDERMAN, new Property(MobKey.ENDERMAN, config.getStringList(MobKey.ENDERMAN, trialRewards.getName(), DeepConstants.TRIAL_REWARD.ENDERMAN, "Enderman Trial Reward"), Property.Type.STRING));
        trialRewards.put(MobKey.SKELETON, new Property(MobKey.SKELETON, config.getStringList(MobKey.SKELETON, trialRewards.getName(), DeepConstants.TRIAL_REWARD.SKELETON, "Skeleton Trial Reward"), Property.Type.STRING));
        trialRewards.put(MobKey.WITCH, new Property(MobKey.WITCH, config.getStringList(MobKey.WITCH, trialRewards.getName(), DeepConstants.TRIAL_REWARD.WITCH, "Witch Trial Reward"), Property.Type.STRING));
        trialRewards.put(MobKey.SPIDER, new Property(MobKey.SPIDER, config.getStringList(MobKey.SPIDER, trialRewards.getName(), DeepConstants.TRIAL_REWARD.SPIDER, "Spider Trial Reward"), Property.Type.STRING));
        trialRewards.put(MobKey.SLIME, new Property(MobKey.SLIME, config.getStringList(MobKey.SLIME, trialRewards.getName(), DeepConstants.TRIAL_REWARD.SLIME, "Slime Trial Reward"), Property.Type.STRING));
        trialRewards.put(MobKey.WITHERSKELETON, new Property(MobKey.WITHERSKELETON, config.getStringList(MobKey.WITHERSKELETON, trialRewards.getName(), DeepConstants.TRIAL_REWARD.WITHERSKELETON, "Slime Trial Reward"), Property.Type.STRING));
    }

    @SubscribeEvent
    public static void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equalsIgnoreCase(DeepConstants.MODID)) {
            // Reload the values if something was changed so the config accessors are "up to date"
            initConfigValues();
        }
    }

    public static class LootParser {

        public static NonNullList<ItemStack> getTrialRewards(String key) {
            NonNullList<ItemStack> list = NonNullList.create();

            String[] toParseList;
            if(trialRewards.containsKey(key)) {
                toParseList = trialRewards.get(key).getStringList();
            } else {
                return list;
            }

            for(int i = 0; i < toParseList.length; i++) {
                if(!getStackFromConfigLine(toParseList[i]).isEmpty()) {
                    list.add(getStackFromConfigLine(toParseList[i]));
                }
            }

            return list;
        }

        public static NonNullList<ItemStack> getPristineLootEntries(String key) {
            NonNullList<ItemStack> list = NonNullList.create();

            String[] toParseList;
            if(pristineOutputs.containsKey(key)) {
                toParseList = pristineOutputs.get(key).getStringList();
            } else {
                return list;
            }

            for(int i = 0; i < toParseList.length; i++) {
                if(!getStackFromConfigLine(toParseList[i]).isEmpty()) {
                    list.add(getStackFromConfigLine(toParseList[i]));
                }
            }

            return list;
        }

        private static ItemStack getStackFromConfigLine(String line) {
            String[] vals = line.split(",");

            if (vals.length < 3) {
                return ItemStack.EMPTY;
            }

            String itemName = vals[0];
            int amount;
            int meta;
            NBTTagCompound nbt = null;

            try {
                amount = Integer.parseInt(vals[1]);
                meta = Integer.parseInt(vals[2]);
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number for meta or amount");
                return ItemStack.EMPTY;
            }

            if(vals.length > 3) {
                StringBuilder nbtString = new StringBuilder();
                for (int i = 3; i < vals.length; i++) {
                    nbtString.append(vals[i]);
                    nbtString.append(i == (vals.length - 1) ? "" : ",");
                }
                try {
                    nbt = JsonToNBT.getTagFromJson(nbtString.toString());
                } catch (NBTException e) {
                    e.printStackTrace();
                    return ItemStack.EMPTY;
                }
            }

            Item item = Item.getByNameOrId(itemName);
            if(item != null && nbt != null) {
                ItemStack nbtStack = new ItemStack(item, amount, meta);
                nbtStack.setTagCompound(nbt);
                return nbtStack;
            } else if(item != null) {
                return new ItemStack(item, amount, meta);
            } else {
                return ItemStack.EMPTY;
            }
        }
    }
}
