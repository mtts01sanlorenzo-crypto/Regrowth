// Fabric 26.3 port: simple properties-file config replacing Forge's ForgeConfigSpec.
package com.mactso.regrowth.config;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MyConfig {

	public static boolean CANCEL_EVENT = true;
	public static boolean CONTINUE_EVENT = false;

	public static boolean tagsInitialized = false;

	// ---- defaults ----
	// mod:mob,type(eat,cut,grow,both,tall,villagerflags),Seconds;
	private static final String DEFAULT_REGROWTH_MOBS = "minecraft:cow,both,300.0;" + "minecraft:horse,eat,180.0;"
			+ "minecraft:donkey,eat,180.0;" + "minecraft:sheep,eat,120.0;" + "minecraft:pig,reforest,450.0;"
			+ "minecraft:bee,grow,500.0;" + "minecraft:chicken,grow,320.0;" + "minecraft:villager,chrwvt,2.0;"
			+ "minecraft:creeper,tall,90.0;" + "minecraft:zombie,stumble, 30.0;" + "minecraft:bat,stumble, 30.0;"
			+ "minecraft:skeleton,mushroom, 40.0;" + "minecraft:tropical_fish,coral, 15.0;"
			+ "minecraft:squid,coral, 15.0;";

	// blocks walls can be built on
	private static final String DEFAULT_WALL_FOUNDATIONS = "minecraft:grass_block;minecraft:sand;minecraft:red_sand;"
			+ "minecraft:netherrack;minecraft:sandstone;minecraft:podzol;minecraft:dirt;minecraft:stone;"
			+ "minecraft:coarse_dirt";

	// biome to get biome category, wall size, wall block type
	private static final String DEFAULT_BIOME_WALL_DATA = "Regrowth:default,40,minecraft:cobblestone_wall,minecraft:oak_fence;"
			+ "minecraft:plains,40,minecraft:cobblestone_wall,minecraft:oak_fence;"
			+ "minecraft:desert,40,minecraft:sandstone_wall,minecraft:birch_fence;"
			+ "minecraft:extreme_hills,40,minecraft:cobblestone_wall,minecraft:spruce_fence;"
			+ "minecraft:taiga,40,minecraft:mossy_cobblestone_wall,minecraft:spruce_fence;"
			+ "minecraft:savanna,40,minecraft:stone_brick_wall,minecraft:acacia_fence;"
			+ "minecraft:icy,40,minecraft:diorite_wall,minecraft:spruce_fence;"
			+ "minecraft:the_end,40,minecraft:end_stone_brick_wall,minecraft:birch_fence;"
			+ "minecraft:beach,40,minecraft:sandstone_wall,minecraft:oak_fence;"
			+ "minecraft:forest,40,minecraft:mossy_stone_brick_wall,minecraft:oak_fence;"
			+ "minecraft:mesa,40,minecraft:red_sandstone_wall,minecraft:oak_fence;"
			+ "minecraft:jungle,40,minecraft:granite_wall,minecraft:jungle_fence;"
			+ "minecraft:river,40,minecraft:mossy_cobblestone_wall,minecraft:oak_fence;"
			+ "minecraft:nether,40,minecraft:blackstone_wall,minecraft:nether_brick_fence;"
			+ "Regrowth:minimum,32,regrowth:minimum_wall_size,regrowth:fence_placeholder";

	// ---- live values ----
	private static int debugLevel = 0;
	public static double eatingHealsOdds = 0.99;
	public static Block playerWallControlBlock = Blocks.COBBLESTONE_WALL;
	public static Block torchBlock = Blocks.TORCH;

	public static String[] defaultRegrowthMobs;
	public static String defaultRegrowthMobs6464 = DEFAULT_REGROWTH_MOBS;
	public static String[] defaultWallFoundationsArray = DEFAULT_WALL_FOUNDATIONS.split(";");
	public static String[] defaultWallBiomeData;
	public static String defaultWallBiomeData6464 = DEFAULT_BIOME_WALL_DATA;

	private static int torchLightLevel = 3;
	private static int mushroomDensity = 7;
	private static int mushroomXDensity = 6;
	private static int mushroomZDensity = 6;
	private static double mushroomMinTemp = 0.2;
	private static double mushroomMaxTemp = 1.2;

	private static String playerWallControlBlockString = "minecraft:cobblestone_wall";
	private static String torchBlockString = "minecraft:torch";

	public static int getaDebugLevel() {
		return debugLevel;
	}

	public static int getDebugLevel() {
		return debugLevel;
	}

	public static void setaDebugLevel(int debugLevel) {
		MyConfig.debugLevel = debugLevel;
	}

	public static void setDebugLevel(int debugLevel) {
		MyConfig.debugLevel = debugLevel;
	}

	public static double getEatingHealsOdds() {
		return eatingHealsOdds;
	}

	public static void setEatingHeals(double aEatingHeals) {
		MyConfig.eatingHealsOdds = aEatingHeals;
	}

	public static Block getPlayerWallControlBlock() {
		return playerWallControlBlock;
	}

	public static void setPlayerWallControlBlock(Block playerWallControlBlock) {
		MyConfig.playerWallControlBlock = playerWallControlBlock;
	}

	public static Block getTorchBlock() {
		return torchBlock;
	}

	public static void setTorchBlock(Block torchBlock) {
		MyConfig.torchBlock = torchBlock;
	}

	public static int getMushroomDensity() {
		return MyConfig.mushroomDensity;
	}

	public static int getMushroomXDensity() {
		return MyConfig.mushroomXDensity;
	}

	public static int getMushroomZDensity() {
		return MyConfig.mushroomZDensity;
	}

	public static double getMushroomMinTemp() {
		return MyConfig.mushroomMinTemp;
	}

	public static double getMushroomMaxTemp() {
		return MyConfig.mushroomMaxTemp;
	}

	public static int getTorchLightLevel() {
		return torchLightLevel;
	}

	// ---- load / save ----

	private static Path configPath() {
		return FabricLoader.getInstance().getConfigDir().resolve("regrowth.properties");
	}

	public static void load() {
		Properties p = new Properties();
		Path path = configPath();
		if (Files.exists(path)) {
			try (Reader r = Files.newBufferedReader(path)) {
				p.load(r);
			} catch (IOException e) {
				System.out.println("Regrowth: could not read config, using defaults: " + e);
			}
		}

		debugLevel = clamp(parseInt(p, "debugLevel", 0), 0, 2);
		eatingHealsOdds = clamp(parseDouble(p, "eatingHeals", 0.99), 0.0, 1.0);
		torchLightLevel = clamp(parseInt(p, "torchLightLevel", 3), 0, 10);
		mushroomDensity = clamp(parseInt(p, "mushroomDensity", 7), 3, 21);
		mushroomXDensity = clamp(parseInt(p, "mushroomXDensity", 6), 3, 11);
		mushroomZDensity = clamp(parseInt(p, "mushroomZDensity", 6), 3, 11);
		mushroomMinTemp = clamp(parseDouble(p, "mushroomMinTemp", 0.2), -2.0, 2.0);
		mushroomMaxTemp = clamp(parseDouble(p, "mushroomMaxTemp", 1.2), -2.0, 2.0);
		playerWallControlBlockString = p.getProperty("playerWallControlBlockString", "minecraft:cobblestone_wall").trim();
		torchBlockString = p.getProperty("torchBlockString", "minecraft:torch").trim();
		defaultRegrowthMobs6464 = p.getProperty("regrowthMobs", DEFAULT_REGROWTH_MOBS);
		defaultWallFoundationsArray = p.getProperty("wallFoundations", DEFAULT_WALL_FOUNDATIONS).trim().split("\\s*;\\s*");
		defaultWallBiomeData6464 = p.getProperty("biomeWallData", DEFAULT_BIOME_WALL_DATA);

		resolveBlocks();
		RegrowthEntitiesManager.regrowthMobInit();
		WallFoundationDataManager.wallFoundationsInit();

		if (!Files.exists(path)) {
			save();
		}
		if (debugLevel > 0) {
			System.out.println("Regrowth Debug Level: " + debugLevel);
		}
	}

	public static void save() {
		Path path = configPath();
		try (Writer w = Files.newBufferedWriter(path)) {
			w.write("# Regrowth config (Fabric 26.3 port)\n");
			w.write("# Debug Level: 0 = Off, 1 = Log, 2 = Chat+Log\n");
			w.write("debugLevel=" + debugLevel + "\n");
			w.write("# Eating heals odds, 0.0 - 1.0\n");
			w.write("eatingHeals=" + eatingHealsOdds + "\n");
			w.write("# Villagers only place torches on blocks this dark or darker (0-10)\n");
			w.write("torchLightLevel=" + torchLightLevel + "\n");
			w.write("# Mushroom density: 3 dense to 21 very sparse\n");
			w.write("mushroomDensity=" + mushroomDensity + "\n");
			w.write("# Mushroom X / Z axis density: 3 dense to 11 sparse\n");
			w.write("mushroomXDensity=" + mushroomXDensity + "\n");
			w.write("mushroomZDensity=" + mushroomZDensity + "\n");
			w.write("# Mushroom minimum / maximum biome temperature\n");
			w.write("mushroomMinTemp=" + mushroomMinTemp + "\n");
			w.write("mushroomMaxTemp=" + mushroomMaxTemp + "\n");
			w.write("# Block placed over the bell; villagers build walls when it is present. 'minecraft:air' = players can't turn walls off\n");
			w.write("playerWallControlBlockString=" + playerWallControlBlockString + "\n");
			w.write("# Torch the villagers place (can be a modded torch)\n");
			w.write("torchBlockString=" + torchBlockString + "\n");
			w.write("# mod:mob,action,seconds;  actions: eat,cut,grow,both,tall,reforest,mushroom,stumble,coral + villager flags\n");
			w.write("regrowthMobs=" + defaultRegrowthMobs6464 + "\n");
			w.write("# Blocks villagers can build walls on, separated by ;\n");
			w.write("wallFoundations=" + String.join(";", defaultWallFoundationsArray) + "\n");
			w.write("# biome,wall size,wall block,fence block;\n");
			w.write("biomeWallData=" + defaultWallBiomeData6464 + "\n");
		} catch (IOException e) {
			System.out.println("Regrowth: could not write config: " + e);
		}
	}

	private static void resolveBlocks() {
		try {
			playerWallControlBlock = BuiltInRegistries.BLOCK.getValue(Identifier.parse(playerWallControlBlockString));
			torchBlock = BuiltInRegistries.BLOCK.getValue(Identifier.parse(torchBlockString));
		} catch (Exception e) {
			System.out.println("Regrowth Debug:  Player Wall Control Block Illegal Config (uPper CaSe?): "
					+ playerWallControlBlockString);
		}
		if (playerWallControlBlock == Blocks.AIR) {
			System.out.println("Regrowth Warn:  Player Wall Control Block is : " + playerWallControlBlockString);
		}
	}

	private static int parseInt(Properties p, String key, int def) {
		try {
			return Integer.parseInt(p.getProperty(key, String.valueOf(def)).trim());
		} catch (NumberFormatException e) {
			return def;
		}
	}

	private static double parseDouble(Properties p, String key, double def) {
		try {
			return Double.parseDouble(p.getProperty(key, String.valueOf(def)).trim());
		} catch (NumberFormatException e) {
			return def;
		}
	}

	private static int clamp(int v, int lo, int hi) {
		return Math.max(lo, Math.min(hi, v));
	}

	private static double clamp(double v, double lo, double hi) {
		return Math.max(lo, Math.min(hi, v));
	}
}
