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
package org.jboss.bpmn2.editor.ui.features.choreography;

import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.jboss.bpmn2.editor.core.features.BaseElementFeatureContainer;
import org.jboss.bpmn2.editor.core.features.MultiUpdateFeature;
import org.jboss.bpmn2.editor.core.features.choreography.ChoreographyLayoutFeature;
import org.jboss.bpmn2.editor.core.features.choreography.ChoreographyUpdateNameFeature;
import org.jboss.bpmn2.editor.ui.features.AbstractDefaultDeleteFeature;

public abstract class AbstractChoreographyFeatureContainer extends BaseElementFeatureContainer {

	@Override
	public MultiUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		MultiUpdateFeature multiUpdate = new MultiUpdateFeature(fp);
		multiUpdate.addUpdateFeature(new ChoreographyUpdateNameFeature(fp));
		multiUpdate.addUpdateFeature(new ChoreographyUpdateParticipantRefsFeature(fp));
		multiUpdate.addUpdateFeature(new ChoreographyUpdateInitiatingParticipantFeature(fp));
		// multiUpdate.addUpdateFeature(new ChoreographyMarkerUpdateFeature(fp)); use it when prop editor supports enums
		return multiUpdate;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new ChoreographyLayoutFeature(fp);
	}

	@Override
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return new ChoreographyMoveFeature(fp);
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return new ChoreographyResizeFeature(fp);
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		return new AbstractDefaultDeleteFeature(fp);
	}
}