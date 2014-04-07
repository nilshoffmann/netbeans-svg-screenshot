/* 
 * Copyright 2014 Nils Hoffmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.nilshoffmann.svg.actions;

import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.geom.AffineTransform.getTranslateInstance;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import static java.lang.System.getProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import static org.apache.batik.dom.GenericDOMImplementation.getDOMImplementation;
import org.apache.batik.svggen.CachedImageHandlerBase64Encoder;
import org.apache.batik.svggen.GenericImageHandler;
import org.apache.batik.svggen.SVGGeneratorContext;
import static org.apache.batik.svggen.SVGGeneratorContext.createDefault;
import org.apache.batik.svggen.SVGGraphics2D;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

@ActionID(
        category = "Window",
        id = "net.nilshoffmann.svg.actions.SvgScreenshot"
)
@ActionRegistration(
        displayName = "#CTL_SvgScreenshot"
)
@ActionReferences({
    //only register this action with a keyboard shortcut
    @ActionReference(path = "Shortcuts", name = "D-F10")
})
@Messages("CTL_SvgScreenshot=Svg Screenshot")
public final class SvgScreenshot implements ActionListener {

    public static final String PREF_PAINTMODE = "SvgScreenshot.paintMode";
    public static final String PREF_OUTPUTDIRECTORY = "SvgScreenshot.outputDirectory";
    public static final String PREF_USEGZIP = "SvgScreenshot.useGzip";
    public static final String DEFAULT_OUTPUTDIRECTORY = "NetBeansScreenshots";

    public static enum PaintMode {

        PAINT, PRINT
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        PaintMode paintMode = PaintMode.valueOf(
                NbPreferences.forModule(SvgScreenshot.class).get(
                        PREF_PAINTMODE, PaintMode.PAINT.name()
                )
        );
        File outputDir = new File(
                NbPreferences.forModule(SvgScreenshot.class).get(
                        PREF_OUTPUTDIRECTORY,
                        new File(getProperty("user.home"), DEFAULT_OUTPUTDIRECTORY).getAbsolutePath()
                )
        );
        boolean useGzip = NbPreferences.forModule(SvgScreenshot.class).getBoolean(
                PREF_USEGZIP, false
        );
        //retrieve the active window component
        KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        Window window = keyboardFocusManager.getActiveWindow();
        //set up the svg canvas and xml document
        DOMImplementation impl
                = getDOMImplementation();
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = impl.createDocument(svgNS, "svg", null);
        SVGGeneratorContext ctx = createDefault(document);
        //embed fonts used to render the UI
        ctx.setEmbeddedFontsOn(true);
        //add image handler to embed images (not scale invariant)
        GenericImageHandler ihandler = new CachedImageHandlerBase64Encoder();
        ctx.setGenericImageHandler(ihandler);
        SVGGraphics2D g2d = new SVGGraphics2D(ctx, true);
        g2d.setTransform(getTranslateInstance(0, 0));
        //make sure everything is up to date
        window.invalidate();
        window.revalidate();
        switch (paintMode) {
            case PRINT:
                window.print(g2d);
                break;
            default:
                window.paint(g2d);
        }
        RequestProcessor.getDefault().post(new FileWriter(outputDir, useGzip, g2d));
    }

    private class FileWriter implements Runnable {

        private final File outputDirectory;
        private final boolean useGzip;
        private final SVGGraphics2D g2d;

        FileWriter(File outputDirectory, boolean useGzip, SVGGraphics2D g2d) {
            this.outputDirectory = outputDirectory;
            this.useGzip = useGzip;
            this.g2d = g2d;
        }

        @Override
        public void run() {
            ProgressHandle ph = ProgressHandleFactory.createHandle("Saving svg screenshot");
            try {
                ph.start();
                ph.progress("Writing svg screenshot file...");
                Date d = new Date();
                String fileNamePrefix = new SimpleDateFormat().format(d);
                File f = saveGraphics(g2d, outputDirectory, useGzip, fileNamePrefix);
                ph.progress("Done!");
                StatusDisplayer.getDefault().setStatusText("Wrote svg screenshot to " + f.getAbsolutePath());
            } finally {
                ph.finish();
            }
        }

        private File saveGraphics(SVGGraphics2D g2d, File outputDirectory, boolean useGzip, String fileNamePrefix) {
            outputDirectory.mkdirs();
            if (useGzip) {
                File file = new File(outputDirectory, fileNamePrefix + ".svg.gz");
                Logger.getLogger(SvgScreenshot.class.getName()).log(Level.INFO, "Saving screenshot to {0}", file);
                try (Writer out = new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(file)))) {
                    g2d.stream(out, true);
                    g2d.dispose();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                return file;
            } else {
                File file = new File(outputDirectory, fileNamePrefix + ".svg");
                Logger.getLogger(SvgScreenshot.class.getName()).log(Level.INFO, "Saving screenshot to {0}", file);
                try (Writer out = new OutputStreamWriter(new FileOutputStream(file))) {
                    g2d.stream(out, true);
                    g2d.dispose();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                return file;
            }
        }

    }
}
