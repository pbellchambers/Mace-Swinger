package uk.co.pbellchambers.maceswinger.items;

public class MaceHead {
	Material MATERIAL;
	
	public MaceHead(int mat){
		this.MATERIAL=Material.getMaterial(mat);
	}
}
