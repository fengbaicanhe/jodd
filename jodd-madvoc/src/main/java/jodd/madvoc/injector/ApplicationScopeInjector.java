// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.injector;

import jodd.madvoc.ActionRequest;
import jodd.madvoc.ScopeData;
import jodd.madvoc.ScopeType;
import jodd.madvoc.component.ScopeDataResolver;

import javax.servlet.ServletContext;
import java.util.Enumeration;

/**
 * Servlet context injector.
 */
public class ApplicationScopeInjector extends BaseScopeInjector
		implements Injector, Outjector, ContextInjector<ServletContext> {

	public ApplicationScopeInjector(ScopeDataResolver scopeDataResolver) {
		super(ScopeType.APPLICATION, scopeDataResolver);
		silent = true;
	}

	public void inject(ActionRequest actionRequest) {
		Target[] targets = actionRequest.getTargets();

		ScopeData[] injectData = lookupScopeData(actionRequest);
		if (injectData == null) {
			return;
		}
		ServletContext servletContext = actionRequest.getHttpServletRequest().getSession().getServletContext();

		Enumeration attributeNames = servletContext.getAttributeNames();

		while (attributeNames.hasMoreElements()) {
			String attrName = (String) attributeNames.nextElement();

			for (int i = 0; i < targets.length; i++) {
				Target target = targets[i];
				if (injectData[i] == null) {
					continue;
				}
				ScopeData.In[] scopes = injectData[i].in;
				if (scopes == null) {
					continue;
				}

				for (ScopeData.In in : scopes) {
					String name = getMatchedPropertyName(in, attrName);
					if (name != null) {
						Object attrValue = servletContext.getAttribute(attrName);
						setTargetProperty(target, name, attrValue);
					}
				}
			}
		}
	}

	public void injectContext(Target target, ScopeData[] scopeData, ServletContext servletContext) {
		ScopeData.In[] injectData = lookupInData(scopeData);
		if (injectData == null) {
			return;
		}

		Enumeration attributeNames = servletContext.getAttributeNames();

		while (attributeNames.hasMoreElements()) {
			String attrName = (String) attributeNames.nextElement();
			for (ScopeData.In in : injectData) {
				String name = getMatchedPropertyName(in, attrName);
				if (name != null) {
					Object attrValue = servletContext.getAttribute(attrName);
					setTargetProperty(target, name, attrValue);
				}
			}
		}
	}

	public void outject(ActionRequest actionRequest) {
		ScopeData[] outjectData = lookupScopeData(actionRequest);
		if (outjectData == null) {
			return;
		}

		Target[] targets = actionRequest.getTargets();
		ServletContext context = actionRequest.getHttpServletRequest().getSession().getServletContext();

		for (int i = 0; i < targets.length; i++) {
			Target target = targets[i];
			if (outjectData[i] == null) {
				continue;
			}
			ScopeData.Out[] scopes = outjectData[i].out;
			if (scopes == null) {
				continue;
			}

			for (ScopeData.Out out : scopes) {
				Object value = getTargetProperty(target, out);
				context.setAttribute(out.name, value);
			}
		}
	}
}