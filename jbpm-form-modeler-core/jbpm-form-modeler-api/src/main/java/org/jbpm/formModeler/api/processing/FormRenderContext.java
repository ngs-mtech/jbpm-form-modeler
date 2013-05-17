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
package org.jbpm.formModeler.api.processing;

import org.jbpm.formModeler.api.model.Form;
import org.jbpm.formModeler.api.processing.FormProcessor;
import org.jbpm.formModeler.api.util.helpers.CDIHelper;

import java.util.Map;

public class FormRenderContext {
    private String UID;
    private Form form;
    private Map<String, Object> bindingData;
    private boolean submit = false;
    private int errors;

    public FormRenderContext(String uid, Form form, Map<String, Object> bindingData) {
        this.UID = uid;
        this.form = form;
        this.bindingData = bindingData;
    }

    public String getUID() {
        return UID;
    }

    public Form getForm() {
        return form;
    }

    public Map<String, Object> getBindingData() {
        return bindingData;
    }

    public FormRenderContextTO getFormRenderingContextTO() {
        FormRenderContextTO formRenderContextTO = new FormRenderContextTO(UID, form.getId(), submit, errors);
        return formRenderContextTO;
    }

    public boolean isSubmit() {
        return submit;
    }

    public void setSubmit(boolean submit) {
        this.submit = submit;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getErrors() {
        return errors;
    }
}
