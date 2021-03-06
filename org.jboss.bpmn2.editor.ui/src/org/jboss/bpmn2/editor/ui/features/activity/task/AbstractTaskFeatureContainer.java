/******************************************************************************* 
 * Copyright (c) 2011 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.bpmn2.editor.ui.features.activity.task;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Task;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.jboss.bpmn2.editor.core.features.AbstractBaseElementUpdateFeature;
import org.jboss.bpmn2.editor.core.features.MultiUpdateFeature;
import org.jboss.bpmn2.editor.core.features.activity.ActivityLayoutFeature;
import org.jboss.bpmn2.editor.core.features.activity.task.DirectEditTaskFeature;
import org.jboss.bpmn2.editor.core.utils.GraphicsUtil;
import org.jboss.bpmn2.editor.ui.features.activity.AbstractActivityFeatureContainer;

public abstract class AbstractTaskFeatureContainer extends AbstractActivityFeatureContainer {

	@Override
	public MultiUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		MultiUpdateFeature multiUpdate = super.getUpdateFeature(fp);
		AbstractBaseElementUpdateFeature nameUpdateFeature = new AbstractBaseElementUpdateFeature(fp) {

			@Override
			public boolean canUpdate(IUpdateContext context) {
				Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
				return bo != null && bo instanceof BaseElement && canApplyTo((BaseElement) bo);
			}
		};
		multiUpdate.addUpdateFeature(nameUpdateFeature);
		return multiUpdate;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return new DirectEditTaskFeature(fp);
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new ActivityLayoutFeature(fp) {
			@Override
			protected boolean layoutHook(Shape shape, GraphicsAlgorithm ga, Object bo, int newWidth, int newHeight) {
				if (bo != null && bo instanceof Task && ga instanceof MultiText) {
					int padding = GraphicsUtil.TASK_IMAGE_SIZE;
					Graphiti.getGaService().setLocationAndSize(ga, 3, padding, newWidth - 6, newHeight - padding);
					return true;
				}
				return false;
			}
		};
	}
}