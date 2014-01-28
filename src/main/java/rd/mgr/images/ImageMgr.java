package rd.mgr.images;

import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;

import rd.util.db.DBUtil;

/**
 * Saving images uploaded via cms admin system
 * Retrieval of images for generating content
 * 
 * @author ruben
 *
 */
public class ImageMgr implements IImageMgr{

	@Override
	public Image getImageByID(long id) {		
		return DBUtil.getEntityMgr().find(Image.class, id);
	}

	@Override
	public Image saveImage(Image img) {
		if(img.getId() <= 0){
			DBUtil.getEntityMgr().persist(img);
		}else{
			DBUtil.getEntityMgr().merge(img);
		}
		return img;
	}

	@Override
	public Image[] getImageByName(String name) {
		TypedQuery<Image> qry = DBUtil.getEntityMgr().createNamedQuery("getImageByName", Image.class);
		qry.setParameter("name", name);
		return convertToArr(qry.getResultList());
	}

	@Override
	public Image[] getAllImages() {
		TypedQuery<Image> qry = DBUtil.getEntityMgr().createNamedQuery("Image.findAll", Image.class);
		return convertToArr(qry.getResultList());
	}

	
	
	private Image[] convertToArr(List<Image> imgs) {
		Image[] result = new Image[imgs.size()];
		Iterator<Image> it = imgs.iterator();
		for(int i = 0; it.hasNext(); i++){
			result[i] = it.next();
		}
		return result;
	}

}
