package uk.co.pbellchambers.maceswinger.items;

public class MaceHandle {
	Material MATERIAL;
	
	public MaceHandle(int mat){
		this.MATERIAL=Material.getMaterial(mat);
	}
}
