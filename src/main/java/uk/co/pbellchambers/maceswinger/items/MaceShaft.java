package uk.co.pbellchambers.maceswinger.items;

public class MaceShaft {
	Material MATERIAL;
	
	public MaceShaft(int mat){
		this.MATERIAL=Material.getMaterial(mat);
	}
}
