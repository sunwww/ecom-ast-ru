package test;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

//import com.thebuzzmedia.imgscalr.Scalr;

public class TestImage {
	
		 
		private static final int IMG_WIDTH = 905;
		private static final int IMG_HEIGHT = 1280;
	 
		public static void main(String [] args){
	 
		try{
	 
			BufferedImage originalImage = ImageIO.read(new File("/home/esinev/out/amokb/120726171331.tif"));
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
	 
			BufferedImage resizeImageJpg = resizeImage(originalImage, type);
			ImageIO.write(resizeImageJpg, "jpg", new File("/home/esinev/out/amokb/120726171331_1.jpg")); 
	 
			BufferedImage resizeImagePng = resizeImage(originalImage, type);
			ImageIO.write(resizeImagePng, "png", new File("/home/esinev/out/amokb/120726171331_2.png")); 
	 
			BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
			ImageIO.write(resizeImageHintJpg, "jpg", new File("/home/esinev/out/amokb/120726171331_3.jpg")); 
	 
			BufferedImage resizeImageHintPng = resizeImageWithHint(originalImage, type);
			ImageIO.write(resizeImageHintPng, "png", new File("/home/esinev/out/amokb/120726171331_4.png")); 
	 
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	 
	    }
	 
	    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
	 
		return resizedImage;
	    }
	 
	    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){
	 
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);
	 
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
	 
		return resizedImage;
	    }	
	
	/*
	public static void main(String[] args) {
		System.out.println("ReaderMIMETypes") ;
    	printlist(ImageIO.getReaderMIMETypes()) ;
    	System.out.println("ReaderFormatNames") ;
    	printlist(ImageIO.getReaderFormatNames()) ;
    	System.out.println("WriterMIMETypes") ;
    	printlist(ImageIO.getWriterMIMETypes()) ;
    	System.out.println("WriterFormatNames") ;
    	printlist(ImageIO.getWriterFormatNames()) ;
    	
    	try {
	    	if (true) {
	    		BufferedImage image = null ;
	    		FileImageOutputStream output = null ;
		
        try {
        	File fileI = new File("/home/esinev/out/amokb/120726171331.tif") ;
        	image = ImageIO.read(fileI) ;

        	String expansion = "JPG" ;
        	String expFile = "jpg" ;
        	if (true) {
        		expansion="TIF" ;
        		expFile="tif" ;
        	}
        	String url_image="" ;
        	Date dat = new Date() ;
        	String username = "adm" ;
        	String dirmain = "/home/esinev/out/amokb/loading" ;
        	String diradd = "" ;
    		url_image = dat.getTime()+"-"+username ;
    		boolean isCompress = false ;
    		 long start = System.currentTimeMillis();
    		boolean isRecord = false ;
        	if (false) {
        		url_image = ""+ dat.getTime() ;
        		diradd = "tmp/"+username+"" ;
        	} else {
        		isRecord = true ;
        		Calendar cal = Calendar.getInstance() ;
        		cal.setTime(dat) ;
        		int year = cal.get(Calendar.YEAR) ;
        		int month = cal.get(Calendar.MONTH)+1 ;
        		int day = cal.get(Calendar.DAY_OF_MONTH);
        		//IWebQueryService wqservice = Injection.find(aRequest).getService(IWebQueryService.class) ;
        		String typeString = "unknown" ;
        		//String sql = "select id,code from VocExternalDocumentType where id='"+form.getType()+"'" ;
        		
        		diradd = "orig/"+typeString+"/"+year+"/"+month+"/"+day ;
        		isCompress = true ;
        	}
        	
    		
    		diradd=diradd+"/"+url_image ;
    		File origFile = new File(dirmain+diradd+".jpg") ;
        	ImageIO.write(image, "JPEG", origFile) ;
        	long total = System.currentTimeMillis() - start;
        	System.out.println(( System.currentTimeMillis() - start) + "ms");
        	image = ImageIO.read(origFile) ;
        	System.out.println(( System.currentTimeMillis() - start) + "ms");
        	
        	if (true) {
        		
        			int w = image.getWidth() ;
        			int h = image.getHeight() ;
        			System.out.println("w="+w) ;
        			System.out.println("h="+h) ;
        			System.out.println(( System.currentTimeMillis() - start) + "ms");
        			int warmreps=40, numreps=40, newsize=1024;
        			int nw=newsize,nh=(nw*h)/w ;
        			if (w>h) {
        				nw=nh ;
        				nh=newsize ;
        			}
        			System.out.println(( System.currentTimeMillis() - start) + "ms");
        			BufferedImage tmp = image;
        			//BufferedImage ti = Scalr.resize(image, 905,1280,Scalr.OP_ANTIALIAS) ;
        					//for (int j = 0; j < warmreps; j++) {
                    //   tmp = TestPanel.getScaledInstance(image, w, h,
                    //            RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
                    //}
                    //Toolkit.getDefaultToolkit().sync();
                   
        			System.out.println(( System.currentTimeMillis() - start) + "ms");
                    	w = tmp.getWidth() ; h= tmp.getHeight() ;
                     //   tmp = TestPanel.getScaledInstance(tmp, nw,nh,
                     //   		RenderingHints.VALUE_COLOR_RENDER_QUALITY, false);
                    
                    //Toolkit.getDefaultToolkit().sync();
                    
                    System.out.println(((double)total/numreps) + "ms");
                    
    				/*Graphics2D g = ti.createGraphics() ;
    				g.setComposite(AlphaComposite.Src) ;
    				g.drawImage(image, 0, 0, 905, 1280, null) ;
    				g.dispose() ;
    				*//*
    				//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_STROKE_DEFAULT);
    				
    				//g.drawImage(sImage,null,null) ;
    				//g.dispose() ;
    				String newName = dirmain+diradd+"-comp.jpg" ;
    				File outFile = new File(newName) ;
    				System.out.println(new Date()) ;
    				output = new FileImageOutputStream(outFile) ;
    				System.out.println(( System.currentTimeMillis() - start) + "ms");
    				//ImageIO.write(ti, "JPEG", output) ;
    				System.out.println(( System.currentTimeMillis() - start) + "ms");
    				
        		
        	}
        	if(isRecord) {
        		//service.insertExternalDocumentByObject("Patient", form.getObjectId(), form.getType()
        		//		, diradd+"-comp."+expFile, diradd+"."+expFile, "", username) ;
        	}
        	System.out.println("url_image="+ diradd+"."+expFile) ;
        }catch(Exception e) {
        	System.out.println(e.fillInStackTrace());
        } 
        finally {
            //image.();
        	if (output!=null) output.close() ;
        }
	}
	
	
	
} catch(Exception e) {
	System.out.println(e);
}
	}*/
	private static void printlist(String[] aList) {
		for(String l:aList) {
			System.out.println("    "+l) ;
		}
	}
    /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     *    scaling technique that provides higher quality than the usual
     *    one-step technique (only useful in down-scaling cases, where
     *    {@code targetWidth} or {@code targetHeight} is
     *    smaller than the original dimensions, and generally only when
     *    the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@codey BufferedImage}
     */
    public static BufferedImage getScaledInstance(BufferedImage img,
                                                  int targetWidth,
                                                  int targetHeight,
                                                  Object hint,
                                                  boolean higherQuality)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
            BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }
        
        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }
    
    
    

}
