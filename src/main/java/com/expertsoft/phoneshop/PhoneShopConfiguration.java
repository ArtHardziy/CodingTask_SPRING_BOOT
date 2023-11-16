package com.expertsoft.phoneshop;

import jakarta.servlet.descriptor.JspConfigDescriptor;
import jakarta.servlet.descriptor.JspPropertyGroupDescriptor;
import lombok.AllArgsConstructor;
import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.expertsoft.phoneshop.PhoneShopConstants.*;

@Configuration
@AllArgsConstructor
public class PhoneShopConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(LOGIN_PATH).setViewName(LOGIN_PAGE);
        registry.addViewController(ADMIN_PATH).setViewName(ADMIN_PANEL_PAGE);
        registry.addRedirectViewController(ROOT_PATH, PHONES_PATH);
    }

    @Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory ( ) {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                super.postProcessContext(context);
                JspConfigDescriptor jspConfigDescriptor = context.getJspConfigDescriptor();
                if (jspConfigDescriptor != null) {
                    jspConfigDescriptor.getTaglibs();
                    List<JspPropertyGroupDescriptor> jspPropertyGroups = (List<JspPropertyGroupDescriptor>) jspConfigDescriptor.getJspPropertyGroups();
                    JspPropertyGroupDescriptorImpl jspPropertyGroupDescriptor = getJspPropertyGroupDescriptor();
                    jspPropertyGroups.add(jspPropertyGroupDescriptor);
                    context.setJspConfigDescriptor(new JspConfigDescriptorImpl(jspPropertyGroups, jspConfigDescriptor.getTaglibs()));
                } else {
                    JspPropertyGroupDescriptorImpl jspPropertyGroupDescriptor = getJspPropertyGroupDescriptor();
                    context.setJspConfigDescriptor(new JspConfigDescriptorImpl(Collections.singletonList(jspPropertyGroupDescriptor), Collections.emptyList()));
                }

            }

            private static JspPropertyGroupDescriptorImpl getJspPropertyGroupDescriptor() {
                JspPropertyGroup jspPropertyGroup = new JspPropertyGroup();
                jspPropertyGroup.setScriptingInvalid("false");
                JspPropertyGroupDescriptorImpl jspPropertyGroupDescriptor = new JspPropertyGroupDescriptorImpl(jspPropertyGroup);
                return jspPropertyGroupDescriptor;
            }
        };
    }
}
