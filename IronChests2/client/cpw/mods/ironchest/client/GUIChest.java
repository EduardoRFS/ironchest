package cpw.mods.ironchest.client;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ModLoader;

import org.lwjgl.opengl.GL11;

import cpw.mods.ironchest.ContainerIronChestBase;
import cpw.mods.ironchest.IronChestType;
import cpw.mods.ironchest.TileEntityIronChest;

public class GUIChest extends GuiContainer {
	public enum GUI {
		IRON(184,202,"/cpw/mods/ironchest/sprites/ironcontainer.png",IronChestType.IRON),
		GOLD(184,256,"/cpw/mods/ironchest/sprites/goldcontainer.png",IronChestType.GOLD),
		DIAMOND(238,256,"/cpw/mods/ironchest/sprites/diamondcontainer.png",IronChestType.DIAMOND),
		COPPER(184,184,"/cpw/mods/ironchest/sprites/coppercontainer.png",IronChestType.COPPER),
		SILVER(184,238,"/cpw/mods/ironchest/sprites/silvercontainer.png",IronChestType.SILVER),
		CRYSTAL(184,238,"/cpw/mods/ironchest/sprites/silvercontainer.png",IronChestType.CRYSTAL);
		
		private int xSize;
		private int ySize;
		private String guiTexture;
		private IronChestType mainType;

		private GUI(int xSize, int ySize, String guiTexture, IronChestType mainType) {
			this.xSize=xSize;
			this.ySize=ySize;
			this.guiTexture=guiTexture;
			this.mainType=mainType;
		}
		
		protected Container makeContainer(IInventory player, IInventory chest) {
			return new ContainerIronChestBase(player,chest, mainType, xSize, ySize);
		}
		
		public static GUIChest buildGUI(IronChestType type, IInventory playerInventory, TileEntityIronChest chestInventory) {
			for (GUI gui : values()) {
				if (chestInventory.getType()==gui.mainType) {
					return new GUIChest(gui,playerInventory,chestInventory);
				}
			}
			return null;
		}
		public static void showGUI(TileEntityIronChest te, EntityPlayer player) {
			GUIChest gui=buildGUI(te.getType(),player.inventory,te);
			if (gui!=null) {
				ModLoader.OpenGUI(player, gui);
			} else {
				player.displayGUIChest(te);
			}
		}
	}

	public int getRowLength() {
		return type.mainType.getRowLength();
	}
	private GUI type;

	private GUIChest(GUI type, IInventory player, IInventory chest) {
		super(type.makeContainer(player,chest));
		this.type=type;
		this.xSize=type.xSize;
		this.ySize=type.ySize;
		this.allowUserInput=false;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        int tex = mc.renderEngine.getTexture(type.guiTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(tex);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
