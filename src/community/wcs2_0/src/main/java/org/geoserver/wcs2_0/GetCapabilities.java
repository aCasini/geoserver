/* Copyright (c) 2012 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */

package org.geoserver.wcs2_0;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.opengis.ows20.AcceptVersionsType;
import net.opengis.wcs20.GetCapabilitiesType;
import org.geoserver.catalog.Catalog;
import org.geoserver.ows.util.RequestUtils;
import org.geoserver.wcs.WCSInfo;
import org.geoserver.wcs.response.WCSCapsTransformer;
import org.geoserver.wcs2_0.response.WCS20GetCapabilitiesTransformer;
import org.geotools.util.logging.Logging;
import org.geotools.xml.transform.TransformerBase;
import org.vfny.geoserver.wcs.WcsException;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GetCapabilities {

    protected Logger LOGGER = Logging.getLogger(DefaultWebCoverageService20.class);
    
    private WCSInfo wcs;
    private Catalog catalog;

    public static final List<String> PROVIDED_VERSIONS =
            Collections.unmodifiableList(Arrays.asList(
                WCS20Const.V20x,
                WCS20Const.V20,
                WCS20Const.V111,
                WCS20Const.V110));

    public GetCapabilities(WCSInfo wcs, Catalog catalog) {
        this.wcs = wcs;
        this.catalog = catalog;
    }

    TransformerBase run(GetCapabilitiesType request) {
//        String acceptedVersion = request.getAcceptVersions() == null ? null : request.getAcceptVersions().getVersion();
        List<String> acceptedVersions = request.getAcceptVersions() == null ? null : request.getAcceptVersions().getVersion();

        String negotiatedVersion = RequestUtils.getVersionOws20(PROVIDED_VERSIONS, acceptedVersions);

        if (WCS20Const.V110.equals(negotiatedVersion) || WCS20Const.V111.equals(negotiatedVersion)) {
            LOGGER.warning("GetCapa2.0 Dispatching to 1.1"); // next code should be tested a bit
            WCSCapsTransformer capsTransformer = new WCSCapsTransformer(wcs.getGeoServer());
            capsTransformer.setEncoding(Charset.forName((wcs.getGeoServer().getSettings().getCharset())));
            return capsTransformer;
        } else if (WCS20Const.V20.equals(negotiatedVersion) || WCS20Const.V20x.equals(negotiatedVersion)) {
            WCS20GetCapabilitiesTransformer capsTransformer = new WCS20GetCapabilitiesTransformer(wcs.getGeoServer());
            capsTransformer.setEncoding(Charset.forName((wcs.getGeoServer().getSettings().getCharset())));
            return capsTransformer;
        } else {
            throw new WcsException("Internal error: Could not understand version:" + negotiatedVersion );
        }

    }

}
