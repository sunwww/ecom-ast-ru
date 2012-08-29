package test;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TestPanel extends JPanel {

    private static boolean dumpImages;
    
    private BufferedImage origLarge, origSmall;
    private boolean testUp = true;
    private boolean started;
    private float alpha;
    
    private int dnOrigWidth = 472;
    private int dnNewWidth = 100;
    private String[] dnDescs = {
        "Nearest",
        "One-step Bilin.",
        "Bicubic",
        "Area Avg.",
        "Multi-step Bilin.",
        // BONUS: uncomment the following...
        // "Trilinear Mipmap",
    };
    private String[] dnTimes = new String[dnDescs.length];
    private Image[] dnImages = new Image[dnDescs.length];
    private Approach[] dnApproaches = new Approach[] {
        new Approach1(),
        new Approach2(),
        new Approach3(),
        new Approach4(),
        new Approach5(),
        // BONUS: uncomment the following...
        // new Approach6(),
    };
    
    private int upOrigWidth = 62;
    private int upNewWidth = 230;
    private String[] upDescs = {
        "Nearest",
        "Area Avg.",
        "Bilinear",
        "Bicubic",
    };
    private String[] upTimes = new String[upDescs.length];
    private Image[] upImages = new Image[upDescs.length];
    private Approach[] upApproaches = new Approach[] {
        new Approach1(),
        new Approach4(),
        new Approach2(),
        new Approach3(),
    };
    
    private JButton button;
    
    public TestPanel() {
        setLayout(null);
        button = new JButton("Run downscaling test...");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button.setEnabled(false);
                if (button.getText().contains("up")) {
                    runUpscaleTest();
                } else {
                    runDownscaleTest();
                }
                repaint();
            }
        });
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        Dimension btnSize = button.getPreferredSize();
        button.setLocation(getPreferredSize().width-btnSize.width-5, 5);
        button.setSize(btnSize);
        add(button);
        
        origLarge = createTestImage(dnOrigWidth, dnOrigWidth);
        origSmall = createTestImage(upOrigWidth, upOrigWidth);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (!started) {
            return;
        }
        
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, alpha));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (testUp) {
            renderUpscaleTest(g2);
        } else {
            renderDownscaleTest(g2);
        }
        g2.dispose();
    }
    
    private void renderDownscaleTest(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        int numBoxes = dnDescs.length;
        int boxw = dnNewWidth + 10;
        int boxh = boxw + 30;
        int pad = 10;
        int boxesw = (numBoxes*boxw) + ((numBoxes-1)*pad);
        g.setColor(Color.WHITE);
        g.drawString("Original Image:", 10, 20);
        g.drawImage(origLarge, w/2 - origLarge.getWidth()/2, 35, null);
        g.drawString("Downscaling Comparison:", 10, 530);
        g.translate(w/2 - (boxesw/2), 540);
        for (int i = 0; i < numBoxes; i++) {
            g.setColor(Color.DARK_GRAY);
            g.drawRect(0, 0, boxw, boxh);
            if (dnImages[i] != null) {
                g.drawImage(dnImages[i], 5, 5, null);
                g.setColor(Color.WHITE);
                g.drawString(dnDescs[i], 5, boxh-20);
                g.setColor(Color.LIGHT_GRAY);
                g.drawString(dnTimes[i], 5, boxh-5);
            }
            g.translate(boxw+pad, 0);
        }
    }
    
    private void renderUpscaleTest(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        int numBoxes = upDescs.length;
        int boxw = upNewWidth + 10;
        int boxh = boxw + 30;
        int pad = 25;
        int numHorizBoxes = numBoxes/2;
        int boxesw = (numHorizBoxes*boxw) + ((numHorizBoxes-1)*pad);
        g.setColor(Color.WHITE);
        g.drawString("Original Image:", 10, 20);
        g.drawImage(origSmall, w/2-(origSmall.getWidth()/2), 30, null);
        g.drawString("Upscaling Comparison:", 10, 115);
        g.translate(w/2 - (boxesw/2), 125);
        for (int i = 0; i < numBoxes; i++) {
            g.setColor(Color.DARK_GRAY);
            g.drawRect(0, 0, boxw, boxh);
            if (upImages[i] != null) {
                g.drawImage(upImages[i], 5, 5, null);
                g.setColor(Color.WHITE);
                g.drawString(upDescs[i], 5, boxh-20);
                g.setColor(Color.LIGHT_GRAY);
                g.drawString(upTimes[i], 5, boxh-5);
            }
            if (i == 1) {
                g.translate(-(boxw+pad), boxh+15);
            } else {
                g.translate(boxw+pad, 0);
            }
        }
    }
    
    private void runDownscaleTest() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                runScaleTest(origLarge,
                             dnApproaches, dnTimes, dnImages,
                             40, 40, dnNewWidth);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        button.setText("Run upscaling test...");
                        button.setEnabled(true);
                    }
                });
            }
        });
        t.start();
    }
    
    private void runUpscaleTest() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                runScaleTest(origSmall,
                             upApproaches, upTimes, upImages,
                             50, 50, upNewWidth);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        button.setText("Run downscaling test...");
                        button.setEnabled(true);
                    }
                });
            }
        });
        t.start();
    }
    
    private void runScaleTest(BufferedImage orig,
                              Approach[] approaches,
                              String[] times, Image[] images,
                              int warmreps, int numreps, int newsize)
    {
        // clear out old results...
        for (int i = 0; i < approaches.length; i++) {
            times[i] = null;
            images[i] = null;
        }
        
        // gratuitous fade in/out effect...
        if (started) {
            alpha = 1.0f;
            do {
                alpha -= 0.05f;
                if (alpha < 0.0f) {
                    alpha = 0.0f;
                }
                try { Thread.sleep(30); } catch (Exception e) {}
                repaint();
            } while (alpha > 0.0f);
        }
        
        testUp = !testUp;
        started = true;
        
        do {
            alpha += 0.05f;
            if (alpha > 1.0f) {
                alpha = 1.0f;
            }
            try { Thread.sleep(30); } catch (Exception e) {}
            repaint();
        } while (alpha < 1.0f);
        
        // run the tests...
        for (int i = 0; i < approaches.length; i++) {
            Approach a = approaches[i];
            Image tmp = null;
            for (int j = 0; j < warmreps; j++) {
                tmp = a.getScaledInstance(orig, newsize, newsize);
            }
            Toolkit.getDefaultToolkit().sync();
            long start = System.currentTimeMillis();
            for (int j = 0; j < numreps; j++) {
                tmp = a.getScaledInstance(orig, newsize, newsize);
            }
            Toolkit.getDefaultToolkit().sync();
            long total = System.currentTimeMillis() - start;
            times[i] = ((double)total/numreps) + "ms";
            images[i] = tmp;
            repaint();
        }
        
        if (dumpImages) {
            BufferedImage capture =
                new BufferedImage(getWidth(), getHeight(),
                                  BufferedImage.TYPE_INT_RGB);
            Graphics g = capture.createGraphics();
            paintComponent(g);
            g.dispose();
            try {
                String name = (orig == origLarge) ? "down.png" : "up.png";
                ImageIO.write(capture, "png", new File(name));
            } catch (Exception e) {
            }
        }
    }
    
    private interface Approach {
        public Image getScaledInstance(BufferedImage img, int w, int h);
    }
    
    private class Approach1 implements Approach {
        public Image getScaledInstance(BufferedImage img, int w, int h) {
            return TestPanel.getScaledInstance(img, w, h,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, false);
        }
    }
    
    private class Approach2 implements Approach {
        public Image getScaledInstance(BufferedImage img, int w, int h) {
            return TestPanel.getScaledInstance(img, w, h,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR, false);
        }
    }
        
    private class Approach3 implements Approach {
        public Image getScaledInstance(BufferedImage img, int w, int h) {
            return TestPanel.getScaledInstance(img, w, h,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC, false);
        }
    }
            
    private class Approach4 implements Approach {
        public Image getScaledInstance(BufferedImage img, int w, int h) {
            Image ret = img.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
            MediaTracker mt = new MediaTracker(TestPanel.this);
            try {
                mt.addImage(ret, 0);
                mt.waitForAll();
            } catch (Exception e) {
            }
            return ret;
        }
    }
                
    private class Approach5 implements Approach {
        public Image getScaledInstance(BufferedImage img, int w, int h) {
            return TestPanel.getScaledInstance(img, w, h,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY, true);
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
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }
    
    /**
     * BONUS: This approach uses a special "trilinear mipmapping" technique
     * written by Jim Graham.  Some developers may find the visual quality
     * provided by this technique to be similar to SCALE_AREA_AVERAGING,
     * but at a fraction of the performance cost (although visually slightly
     * "fuzzier" perhaps).
     */
    private class Approach6 implements Approach {
        public Image getScaledInstance(BufferedImage img,
                                       int targetWidth,
                                       int targetHeight)
        {
            // REMIND: This only works for opaque images...

            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            int iw = img.getWidth();
            int ih = img.getHeight();

            Object hint = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
            int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

            // First get down to no more than 2x in W & H
            while (iw > targetWidth*2 || ih > targetHeight*2) {
                iw = (iw > targetWidth*2) ? iw/2 : iw;
                ih = (ih > targetHeight*2) ? ih/2 : ih;
                img = scaleImage(img, type, hint, iw, ih);
            }

            // REMIND: Conservative approach:
            // first get W right, then worry about H

            // If still too wide - do a horizontal trilinear blend
            // of img and a half-width img
            if (iw > targetWidth) {
                int iw2 = iw/2;
                BufferedImage img2 = scaleImage(img, type, hint, iw2, ih);
                if (iw2 < targetWidth) {
                    img = scaleImage(img, type, hint, targetWidth, ih);
                    img2 = scaleImage(img2, type, hint, targetWidth, ih);
                    interp(img2, img, iw-targetWidth, targetWidth-iw2);
                }
                img = img2;
                iw = targetWidth;
            }
            // iw should now be targetWidth or smaller

            // If still too tall - do a vertical trilinear blend
            // of img and a half-height img
            if (ih > targetHeight) {
                int ih2 = ih/2;
                BufferedImage img2 = scaleImage(img, type, hint, iw, ih2);
                if (ih2 < targetHeight) {
                    img = scaleImage(img, type, hint, iw, targetHeight);
                    img2 = scaleImage(img2, type, hint, iw, targetHeight);
                    interp(img2, img, ih-targetHeight, targetHeight-ih2);
                }
                img = img2;
                ih = targetHeight;
            }
            // ih should now be targetHeight or smaller

            // If we are too small, then it was probably because one of
            // the dimensions was too small from the start.
            if (iw < targetWidth && ih < targetHeight) {
                img = scaleImage(img, type, hint, targetWidth, targetHeight);
            }

            return img;
        }

        private BufferedImage scaleImage(BufferedImage orig,
                                         int type,
                                         Object hint,
                                         int w, int h)
        {
            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(orig, 0, 0, w, h, null);
            g2.dispose();
            return tmp;
        }
        
        private void interp(BufferedImage img1,
                            BufferedImage img2,
                            int weight1,
                            int weight2)
        {
            float alpha = weight1;
            alpha /= (weight1 + weight2);
            Graphics2D g2 = img1.createGraphics();
            g2.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.drawImage(img2, 0, 0, null);
            g2.dispose();
        }
    }
    
    private static BufferedImage createTestImage(int w, int h) {
        int w2 = w/2;
        int h2 = h/2;
        
        BufferedImage img =
            new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, w2, h2);
        g2.fillRect(w2, h2, w2, h2);
        for (int i = 8; i < w; i += 16) {
            g2.setColor(Color.BLUE);
            g2.fillRect(0, i, w, 1);
            g2.setColor(Color.GREEN);
            g2.fillRect(i, 0, 1, h);
        }
        g2.setColor(Color.RED);
        g2.drawLine(w2, 0, w, h2);
        g2.drawLine(w, h2, w2, h);
        g2.drawLine(w2, h, 0, h2);
        g2.drawLine(0, h2, w2, 0);
        g2.drawOval(0, 0, w-1, h-1);
        try {
            BufferedImage photo =
                ImageIO.read(TestPanel.class.getResource("bw.jpg"));
            g2.setClip(new Ellipse2D.Float(w2/2, h2/2, w2, h2));
            g2.drawImage(photo, w2/2, h2/2, null);
        } catch (Exception e) {
        }
        g2.dispose();
        
        return img;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(dnDescs.length*120+30, 690);
    }
    
    public static void main(String[] args) {
        if (args.length > 0) {
            dumpImages = true;
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                        UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception e) {
                }
                TestPanel test = new TestPanel();
                JFrame frame = new JFrame("Image Scaling Demo");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(test);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
    }
}