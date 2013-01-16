package it.phoops.geoserver.ols;


import java.io.IOException;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.catalog.StyleInfo;
import org.geoserver.config.GeoServer;
import org.geoserver.platform.GeoServerExtensions;
import org.geotools.styling.Style;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * A facade providing access to the OLS configuration details
 * 
 */
public class OLS implements ApplicationContextAware {


    private final GeoServer geoserver;

    private static ApplicationContext applicationContext;

    public OLS(GeoServer geoserver) {
        this.geoserver = geoserver;
    }

    public Catalog getCatalog() {
        return geoserver.getCatalog();
    }

    public OLSInfo getServiceInfo() {
        return geoserver.getService(OLSInfo.class);
    }

    public Style getStyleByName(String styleName) throws IOException {
        StyleInfo styleInfo = getCatalog().getStyleByName(styleName);
        return styleInfo == null ? null : styleInfo.getStyle();
    }

    public LayerInfo getLayerByName(String layerName) {
        return getCatalog().getLayerByName(layerName);
    }

    public LayerGroupInfo getLayerGroupByName(String layerGroupName) {
        return getCatalog().getLayerGroupByName(layerGroupName);
    }

    public boolean isEnabled() {
        OLSInfo serviceInfo = getServiceInfo();
        return serviceInfo.isEnabled();
    }


    public GeoServer getGeoServer() {
        return this.geoserver;
    }

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(final ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    public static ApplicationContext getApplicationContext(){
    	return applicationContext;
    }

    public static OLS get() {
        return GeoServerExtensions.bean(OLS.class);
    }
}

