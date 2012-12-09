/* Copyright (c) 2012 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.wfs.xslt.config;

import org.geoserver.catalog.FeatureTypeInfo;

/**
 * Stores the configuration of one XSLT transformation
 * 
 * @author Andrea Aime - GeoSolutions
 */
public class TransformInfo {

    String name;

    String sourceFormat;

    String outputFormat;
    
    String outputMimeType;

    String fileExtension;

    String xslt;

    FeatureTypeInfo featureType;
    
    public TransformInfo() {
        
    }
    
    public TransformInfo(TransformInfo other) {
        this.name = other.name;
        this.sourceFormat = other.sourceFormat;
        this.outputFormat = other.outputFormat;
        this.fileExtension = other.fileExtension;
        this.xslt = other.xslt;
        this.featureType = other.featureType;
    }

    /**
     * The transform name (same as the file used to persist the transform configuration)
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The source format/mime type that the XLST transformation uses as the origin. Must be a form
     * of XML.
     * 
     * @return
     */
    public String getSourceFormat() {
        return sourceFormat;
    }

    public void setSourceFormat(String sourceFormat) {
        this.sourceFormat = sourceFormat;
    }

    /**
     * The mime type of the file generated by the XLST transformation. Normally it's some sort of
     * text file
     * 
     * @param outputFormat
     */
    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * The extension of the file generated by the XSLT transformation (will be used in the HTTP
     * headers of the response)
     * 
     * @return
     */
    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * The XLST file name
     */
    public String getXslt() {
        return xslt;
    }

    public void setXslt(String fileName) {
        this.xslt = fileName;
    }

    /**
     * The eventual specific feature type the XSLT is designed to run against (in this case the
     * sheet will reference specific attributes of the layer)
     * 
     * @return
     */
    public FeatureTypeInfo getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureTypeInfo featureType) {
        this.featureType = featureType;
    }
    
    public String mimeType() {
        if(outputMimeType != null) {
            return outputMimeType;
        } else {
            return outputFormat;
        }
    }

    /**
     * Returns the output mime type
     * @return
     */
    public String getOutputMimeType() {
        return outputMimeType;
    }

    public void setOutputMimeType(String outputMime) {
        this.outputMimeType = outputMime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((featureType == null) ? 0 : featureType.hashCode());
        result = prime * result + ((fileExtension == null) ? 0 : fileExtension.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((outputFormat == null) ? 0 : outputFormat.hashCode());
        result = prime * result + ((outputMimeType == null) ? 0 : outputMimeType.hashCode());
        result = prime * result + ((sourceFormat == null) ? 0 : sourceFormat.hashCode());
        result = prime * result + ((xslt == null) ? 0 : xslt.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TransformInfo other = (TransformInfo) obj;
        if (featureType == null) {
            if (other.featureType != null)
                return false;
        } else if (!featureType.equals(other.featureType))
            return false;
        if (fileExtension == null) {
            if (other.fileExtension != null)
                return false;
        } else if (!fileExtension.equals(other.fileExtension))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (outputFormat == null) {
            if (other.outputFormat != null)
                return false;
        } else if (!outputFormat.equals(other.outputFormat))
            return false;
        if (outputMimeType == null) {
            if (other.outputMimeType != null)
                return false;
        } else if (!outputMimeType.equals(other.outputMimeType))
            return false;
        if (sourceFormat == null) {
            if (other.sourceFormat != null)
                return false;
        } else if (!sourceFormat.equals(other.sourceFormat))
            return false;
        if (xslt == null) {
            if (other.xslt != null)
                return false;
        } else if (!xslt.equals(other.xslt))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TransformInfo [name=" + name + ", sourceFormat=" + sourceFormat + ", outputFormat="
                + outputFormat + ", outputMimeType=" + outputMimeType + ", fileExtension="
                + fileExtension + ", xslt=" + xslt + ", featureType=" + featureType + "]";
    }

    
}
