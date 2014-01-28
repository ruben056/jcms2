package rd.mgr.images;

public interface IImageMgr {

	public Image getImageByID(long id);
	
	public Image saveImage(Image img);
	
	public Image[] getImageByName(String name);
	
	public Image[] getAllImages();
}
