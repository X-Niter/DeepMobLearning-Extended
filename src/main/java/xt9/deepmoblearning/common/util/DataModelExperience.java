package xt9.deepmoblearning.common.util;

import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.config.Config;

/**
 * Created by xt9 on 2017-06-14.
 */
public class DataModelExperience {
    // Simulations have no multipliers, they are always 1x
    private static final int[] killMultiplier = {
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier0").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier1").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier2").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier3").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier4").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier5").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier6").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier7").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier8").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier9").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier10").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier11").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier12").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier13").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier14").getInt(), 1, 10000),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier15").getInt(), 1, 10000),
        0 // Max tier, no kill multiplier
    };

    private static final int[] maxExperience = {
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier1").getInt(), 1, 200000) * killMultiplier[0],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier2").getInt(), 1, 200000) * killMultiplier[1],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier3").getInt(), 1, 200000) * killMultiplier[2],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier4").getInt(), 1, 200000) * killMultiplier[3],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier5").getInt(), 1, 200000) * killMultiplier[4],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier6").getInt(), 1, 200000) * killMultiplier[5],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier7").getInt(), 1, 200000) * killMultiplier[6],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier8").getInt(), 1, 200000) * killMultiplier[7],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier9").getInt(), 1, 200000) * killMultiplier[8],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier10").getInt(), 1, 200000) * killMultiplier[9],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier11").getInt(), 1, 200000) * killMultiplier[10],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier12").getInt(), 1, 200000) * killMultiplier[11],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier13").getInt(), 1, 200000) * killMultiplier[12],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier14").getInt(), 1, 200000) * killMultiplier[13],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier15").getInt(), 1, 200000) * killMultiplier[14],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier16").getInt(), 1, 200000) * killMultiplier[15],
    };

    /* tier is CURRENT tier, kc is kill count for CURRENT tier, sc is simulation count for CURRENT  tier */
    public static boolean shouldIncreaseTier(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return false;
        }
        int roof = maxExperience[tier];
        int killExperience = kc * killMultiplier[tier];

        return killExperience + sc >= roof;
    }

    public static double getCurrentTierKillCountWithSims(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        return kc + ((double) sc / killMultiplier[tier]);
    }

    public static int getCurrentTierSimulationCountWithKills(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        return sc + (kc * killMultiplier[tier]);
    }

    public static double getKillsToNextTier(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        int killRoof = getTierRoof(tier, true);
        return killRoof - getCurrentTierKillCountWithSims(tier, kc, sc);
    }

    public static int getSimulationsToNextTier(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        int roof = getTierRoof(tier, false);
        return roof - getCurrentTierSimulationCountWithKills(tier, kc, sc);
    }

    public static int getTierRoof(int tier, boolean asKills) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        if(!asKills) {
            return maxExperience[tier];
        } else {
            return maxExperience[tier] / killMultiplier[tier];
        }
    }

    public static int getKillMultiplier(int tier) {
        return killMultiplier[tier];
    }
}
