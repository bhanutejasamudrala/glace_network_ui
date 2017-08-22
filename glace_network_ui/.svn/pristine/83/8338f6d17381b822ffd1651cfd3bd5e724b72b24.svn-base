package com.glenwood.network.client.ioc;

import com.google.gwt.core.client.GWT;


import java.util.logging.Logger;

public class DesktopGinjectorProvider implements GinjectorProvider {
    public final static Logger logger = Logger.getLogger(DesktopGinjectorProvider.class.getName());

    @Override
    public ClientGinjector get() {
        logger.info("DesktopGinjectorProvider get() DesktopGinjector was used.");
        return GWT.create(DesktopGinjector.class);
    }
}
