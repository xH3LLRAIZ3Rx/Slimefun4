package me.mrCookieSlime.Slimefun.Objects;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.mrCookieSlime.CSCoreLibPlugin.compatibility.MaterialHelper;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunMachine;

public class MultiBlock {
	
	private Material[] blocks;
	private BlockFace trigger;
	private boolean isSymmetric;

	@Deprecated
	public MultiBlock(Material[] build, Material trigger) {
		this.blocks = build;
		this.isSymmetric = isSymmetric(build);
		//Hacky, the other constructor should be used
		for (int i = 1; i < 9; i +=3)
		{
			if (trigger.equals(build[i]))
			{
				switch (i) {
					case 1:
						this.trigger = BlockFace.DOWN;
						break;
					case 4:
						this.trigger = BlockFace.SELF;
						break;
					case 7:
						this.trigger = BlockFace.UP;
						break;
				}
				break;
			}
		}
	}
	
	public MultiBlock(Material[] build, BlockFace trigger)
	{
		this.blocks = build;
		this.trigger = trigger;
		this.isSymmetric = isSymmetric(build);
	}
	
	private static boolean isSymmetric(Material[] blocks)
	{
		return blocks[0] == blocks[2]
			&& blocks[3] == blocks[5]
			&& blocks[6] == blocks[8];
	}
	
	public Material[] getBuild() {
		return this.blocks;
	}
	
	public BlockFace getTriggerBlock() {
		return this.trigger;
	}
	
	public void register() {
		SlimefunPlugin.getUtilities().allMultiblocks.add(this);
	}
	
	public static List<MultiBlock> list() {
		return SlimefunPlugin.getUtilities().allMultiblocks;
	}
	
	public boolean isMultiBlock(SlimefunItem machine) {
		if (machine instanceof SlimefunMachine) {
			return isMultiBlock(((SlimefunMachine) machine).toMultiBlock());
		}
		else return false;
	}
	
	public boolean isMultiBlock(MultiBlock mb) {
		if (mb == null) return false;
		
		if (trigger == mb.getTriggerBlock()) {
			for (int i = 0; i < mb.getBuild().length; i++) {
				if (!compareBlocks(blocks[i], mb.getBuild()[i])) return false;
			}
			
			return true;
		}
		
		return false;
	}

	private boolean compareBlocks(Material a, Material b) {
		if (b != null) {
			if (MaterialHelper.isLog(b)) {
				return MaterialHelper.isLog(a);
			}
			
			if (b != a) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isSymmetric()
	{
		return this.isSymmetric;
	}

}
