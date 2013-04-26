/**
 * Copyright (C) 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.formModeler.components.renderer;

import org.apache.commons.lang.StringUtils;
import org.jbpm.formModeler.api.model.Form;
import org.jbpm.formModeler.api.processing.FormProcessor;
import org.jbpm.formModeler.api.processing.FormStatusData;
import org.jbpm.formModeler.api.util.helpers.CDIHelper;
import org.jbpm.formModeler.renderer.FormRenderContext;
import org.jbpm.formModeler.renderer.FormRenderContextManager;
import org.jbpm.formModeler.service.bb.mvc.components.handling.BaseUIComponent;
import org.jbpm.formModeler.service.bb.mvc.controller.CommandRequest;

public class FormRenderingComponent extends BaseUIComponent {
    private String baseComponentJSP = "/formModeler/components/renderer/component.jsp";
    private String componentIncludeJSP = "/formModeler/components/renderer/show.jsp";

    private FormRenderContextManager formRenderContextManager;
    private FormProcessor formProcessor = (FormProcessor) CDIHelper.getBeanByType(FormProcessor.class);
    private String ctxUID;
    private Form form;

    public void doStart(CommandRequest commandRequest) {

        this.ctxUID = commandRequest.getRequestObject().getParameter("ctxUID");

        if (StringUtils.isEmpty(ctxUID)) return;

        if (formRenderContextManager == null) formRenderContextManager = (FormRenderContextManager) CDIHelper.getBeanByType(FormRenderContextManager.class);

        FormRenderContext ctx = formRenderContextManager.getFormRenderContext(ctxUID);

        this.form = ctx.getForm();

    }

    public void actionSubmitForm(CommandRequest request) {

        String ctxUID = request.getRequestObject().getParameter("ctxUID");
        FormRenderContext ctx = formRenderContextManager.getFormRenderContext(ctxUID);
        Form form = ctx.getForm();

        formProcessor.setValues(form, ctxUID, request.getRequestObject().getParameterMap(), request.getFilesByParamName());
        FormStatusData fsd = formProcessor.read(form.getId(), ctxUID);
        if (fsd.isValid()) {
            formProcessor.clear(form.getId(), ctxUID);
            if (ctxUID.equals(this.ctxUID)) {
                this.form = null;
                this.ctxUID = null;
            }
        }
    }

    public String getCtxUID() {
        return ctxUID;
    }

    public void setCtxUID(String ctxUID) {
        this.ctxUID = ctxUID;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public void setBaseComponentJSP(String baseComponentJSP) {
        this.baseComponentJSP = baseComponentJSP;
    }

    public void setComponentIncludeJSP(String componentIncludeJSP) {
        this.componentIncludeJSP = componentIncludeJSP;
    }

    @Override
    public String getBaseComponentJSP() {
        return baseComponentJSP;
    }

    @Override
    public String getComponentIncludeJSP() {
        return componentIncludeJSP;
    }

    public FormProcessor getFormProcessor() {
        return formProcessor;
    }

    public void setFormProcessor(FormProcessor formProcessor) {
        this.formProcessor = formProcessor;
    }
}