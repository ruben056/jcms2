package rd.servlet.system;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import rd.mgr.images.Image;
import rd.util.ComponentFactory;
import rd.util.db.DBUtil;

@MultipartConfig(fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/admin/uploader/upload"})
public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().write(this.getClass().getName() + "is online.");
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		response.setContentType("text/html;charset=UTF-8");

	    // Create path components to save the file
//		final String path = RDProperties.getString(RDProperties.ABSOLUTE_UPLOAD_FOLDER); //TODO this will have to be configurable
	    
	    final Part filePart = request.getPart("upload");
	    final String fileName = getFileName(filePart);
	    
//	    OutputStream out = null;
	    ByteArrayOutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();

	    EntityManager eMgr = DBUtil.initEmgr();
		eMgr.getTransaction().begin();
	    try {
//	        out = new FileOutputStream(new File(path + File.separator+ fileName));
	    	out = new ByteArrayOutputStream();
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        
	        Image img = new Image();
	        img.setImageData(out.toByteArray());
	        img.setName(fileName);
	        ComponentFactory.getImgMgr().saveImage(img);
	        eMgr.getTransaction().commit();
	    } catch (FileNotFoundException fne) {
	        writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + fne.getMessage());
	        fne.printStackTrace();
	        eMgr.getTransaction().rollback();
	    } catch(Exception e ){
	    	writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + e.getMessage());
	        e.printStackTrace();
	        eMgr.getTransaction().rollback();
	    }finally {
	    	eMgr.close();
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        if (writer != null) {
	            writer.close();
	        }
	    }
	}

	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    for (String content : partHeader.split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}

}
