package org.geoserver.wps.resource;

import java.awt.image.RenderedImage;

import javax.media.jai.PlanarImage;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.resources.image.ImageUtilities;
import org.opengis.coverage.grid.GridCoverage;

public class CoverageResource implements WPSResource {

    GridCoverage coverage;
    
    public CoverageResource(GridCoverage coverage) {
        this.coverage = coverage;
    }

    @Override
    public void delete() throws Exception {
        if(coverage instanceof GridCoverage2D) {
            final GridCoverage2D gc = (GridCoverage2D) coverage;
            final RenderedImage image = gc.getRenderedImage();
            if(image instanceof PlanarImage) {
                ImageUtilities.disposePlanarImageChain((PlanarImage) image);
            }
            gc.dispose(true);
        }
    }

    @Override
    public String getName() {
        return coverage.toString();
    }

}
