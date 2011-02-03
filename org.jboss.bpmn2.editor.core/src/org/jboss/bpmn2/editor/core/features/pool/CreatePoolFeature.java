package org.jboss.bpmn2.editor.core.features.pool;

import java.io.IOException;

import org.eclipse.bpmn2.Participant;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.jboss.bpmn2.editor.core.Activator;
import org.jboss.bpmn2.editor.core.ImageProvider;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.ModelHandlerLocator;

public class CreatePoolFeature extends AbstractCreateFeature {

	public CreatePoolFeature(IFeatureProvider fp) {
	    super(fp, "Pool", "Container for partitioning a set of activities");
    }

	@Override
    public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
    }

	@Override
    public Object[] create(ICreateContext context) {
		Participant p = null;
		try {
	        ModelHandler mh = ModelHandlerLocator.getModelHandler(getDiagram().eResource());
	        p = mh.addCollaborator();
	        p.setName("Pool");
        } catch (IOException e) {
        	Activator.logError(e);
        }
        addGraphicalRepresentation(context, p);
		return new Object[] { p };
    }
	
	@Override
	public String getCreateImageId() {
	    return ImageProvider.IMG_16_PARTICIPANT;
	}
	
	@Override
	public String getCreateLargeImageId() {
	    return getCreateImageId(); // FIXME
	}
}
