package xt9.deepmoblearning.common.util;

import xt9.deepmoblearning.common.config.Config;

/**
 * Created by xt9 on 2018-03-25.
 * DataModel tiers, used for datamodels & TileEntityRelation keys
 */
public class Tier {
    public static int getPristineChance(int tier) {
        switch(tier) {
            case 0: return 0;
            case 1: return Config.pristineChance.get("tier1").getInt();
            case 2: return Config.pristineChance.get("tier2").getInt();
            case 3: return Config.pristineChance.get("tier3").getInt();
            case 4: return Config.pristineChance.get("tier4").getInt();
            case 5: return Config.pristineChance.get("tier5").getInt();
            case 6: return Config.pristineChance.get("tier6").getInt();
            case 7: return Config.pristineChance.get("tier7").getInt();
            case 8: return Config.pristineChance.get("tier8").getInt();
            case 9: return Config.pristineChance.get("tier9").getInt();
            case 10: return Config.pristineChance.get("tier10").getInt();
            case 11: return Config.pristineChance.get("tier11").getInt();
            case 12: return Config.pristineChance.get("tier12").getInt();
            case 13: return Config.pristineChance.get("tier13").getInt();
            case 14: return Config.pristineChance.get("tier14").getInt();
            case 15: return Config.pristineChance.get("tier15").getInt();
            case 16: return Config.pristineChance.get("tier16").getInt();
            default: return 0;
        }
    }

    public static String getTierName(int tier, boolean getNextTierName) {
        int addTiers = getNextTierName ? 1 : 0;
        switch(tier + addTiers) {
            case 0: return "§8Faulty§r";
            case 1: return "§7Unrefined§r";
            case 2: return "§aBasic§r";
            case 3: return "Common";
            case 4: return "§eFormal§r";
			case 5: return "§2Uncommon§r";
            case 6: return "§eFixed§r";
            case 7: return "§9Advanced§r";
            case 8: return "§dSuperior§r";
            case 9: return "§1Rare§r";
            case 10: return "§bReflecting§r";
            case 11: return "§3Rumored§r";
            case 12: return "§5Epic§r";
            case 13: return "§aRadiant§r";
            case 14: return "§5Self §1Aware§r";
            case 15: return "§6Legendary§r";
            case 16: return "§4P§6e§2r§bf§1e§9c§5t§r";
            default: return "§8Faulty§r";
        }
    }

    public static boolean isMaxTier(int tier) {
        return tier == 16;
    }
}
