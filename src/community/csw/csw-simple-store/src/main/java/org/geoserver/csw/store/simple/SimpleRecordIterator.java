package org.geoserver.csw.store.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.opengis.cat.csw20.RecordType;
import net.opengis.cat.csw20.SimpleLiteral;
import net.opengis.ows10.BoundingBoxType;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.geoserver.csw.records.CSWRecordBuilder;
import org.geotools.csw.CSWConfiguration;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.util.logging.Logging;
import org.geotools.xml.Parser;
import org.opengis.feature.Feature;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

class SimpleRecordIterator implements Iterator<Feature> {
    
    static final Logger LOGGER = Logging.getLogger(SimpleRecordIterator.class);
    
    Iterator<File> files;
    RecordType record;
    Parser parser;
    CSWRecordBuilder builder = new CSWRecordBuilder();

    public SimpleRecordIterator(File root) {
        File[] fileArray = root.listFiles((FilenameFilter) new SuffixFileFilter(".xml", IOCase.INSENSITIVE));
        files = Arrays.asList(fileArray).iterator();
        parser = new Parser(new CSWConfiguration());
    }

    @Override
    public boolean hasNext() {
        while(record == null && files.hasNext()) {
            File file = files.next();
            InputStream is = null; 
            try {
                is = new FileInputStream(file);
                record = (RecordType) parser.parse(is);
            } catch(Exception e) {
                LOGGER.log(Level.INFO, "Failed to parse the contents of " + file.getPath() + " as a CSW Record", e);
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
        
        return record != null;
    }

    @Override
    public Feature next() {
        if(!hasNext()) {
            throw new NoSuchElementException("No more records to retrieve");
        }
        
        Feature  next = convertToFeature(record);
        record = null;
        return next;
    }

    private Feature convertToFeature(RecordType r) {
        String id = null;
        
        // add all the elements
        for(SimpleLiteral sl : r.getDCElement()) {
            Object value = sl.getValue();
            String name = sl.getName();
            if(value != null && sl.getName() != null) {
                builder.addElement(name, value.toString());
                if("identifier".equals(name)) {
                    id = value.toString();
                }
            }
        }
        
        // move on to the bounding boxes
        for(BoundingBoxType bbox : r.getBoundingBox()) {
            if(bbox != null) {
                CoordinateReferenceSystem crs = DefaultGeographicCRS.WGS84;
                if(bbox.getCrs() != null) {
                    try {
                        crs = CRS.decode(bbox.getCrs());
                    } catch(Exception e) {
                        LOGGER.log(Level.INFO, "Failed to parse original record bbox");
                    }
                }
                ReferencedEnvelope re = new ReferencedEnvelope((Double) bbox.getLowerCorner().get(0), (Double) bbox.getUpperCorner().get(0), 
                        (Double) bbox.getLowerCorner().get(1), (Double) bbox.getUpperCorner().get(1), crs);
                builder.addBoundingBox(re);
            }
        }
        
        return builder.build(id);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("This iterator is read only");
    }

}
