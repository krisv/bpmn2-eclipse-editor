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
package org.jboss.bpmn2.editor.core.features.lane;

import java.io.IOException;

import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.LaneSet;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.Process;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.jboss.bpmn2.editor.core.Activator;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.utils.FeatureSupport;

public class MoveFromLaneToParticipantFeature extends MoveLaneFeature {

	public MoveFromLaneToParticipantFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canMoveShape(IMoveShapeContext context) {
		if (getMovedLane(context).getFlowNodeRefs().isEmpty()) {
			return true;
		}

		Participant p = (Participant) getBusinessObjectForPictogramElement(context.getTargetContainer());

		if (p.getProcessRef() == null) {
			return true;
		}

		if (!p.getProcessRef().getLaneSets().isEmpty()) {
			return true;
		}

		return false;
	}

	@Override
	protected void internalMove(IMoveShapeContext context) {
		modifyModelStructure(context);
		FeatureSupport.redraw(context.getTargetContainer());
		FeatureSupport.redraw(context.getSourceContainer());
	}

	private void modifyModelStructure(IMoveShapeContext context) {
		Lane movedLane = getMovedLane(context);
		Participant targetParticipant = (Participant) getBusinessObjectForPictogramElement(context.getTargetContainer());

		try {
			ModelHandler handler = ModelHandler.getInstance(getDiagram());
			handler.moveLane(movedLane, targetParticipant);
		} catch (IOException e) {
			Activator.logError(e);
		}

		Process process = targetParticipant.getProcessRef();
		if (process.getLaneSets().isEmpty()) {
			LaneSet createLaneSet = ModelHandler.FACTORY.createLaneSet();
			createLaneSet.setId(EcoreUtil.generateUUID());
			process.getLaneSets().add(createLaneSet);
		}
		process.getLaneSets().get(0).getLanes().add(movedLane);

		Lane fromLane = (Lane) getBusinessObjectForPictogramElement(context.getSourceContainer());
		fromLane.getChildLaneSet().getLanes().remove(movedLane);
		if (fromLane.getChildLaneSet().getLanes().isEmpty()) {
			fromLane.setChildLaneSet(null);
		}
	}
}
