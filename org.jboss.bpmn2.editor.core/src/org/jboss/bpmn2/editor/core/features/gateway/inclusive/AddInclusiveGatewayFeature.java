package org.jboss.bpmn2.editor.core.features.gateway.inclusive;

import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.styles.AdaptedGradientColoredAreas;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.PredefinedColoredAreas;
import org.jboss.bpmn2.editor.core.features.StyleUtil;

public class AddInclusiveGatewayFeature extends AbstractAddShapeFeature {
	
	private static final int RADIUS = 25;
	
	public AddInclusiveGatewayFeature(IFeatureProvider fp) {
	    super(fp);
    }

	@Override
    public boolean canAdd(IAddContext context) {
		boolean isInclusiveGateway = context.getNewObject() instanceof InclusiveGateway;
		boolean intoDiagram = context.getTargetContainer() instanceof Diagram;
		return isInclusiveGateway && intoDiagram;
	}

	@Override
    public PictogramElement add(IAddContext context) {
		InclusiveGateway addedGateway = (InclusiveGateway) context.getNewObject();
		Diagram targetDiagram = (Diagram) context.getTargetContainer();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);

		IGaService gaService = Graphiti.getGaService();
		int[] xy = new int[] { 0, RADIUS, RADIUS, 0, 0, -RADIUS, -RADIUS, 0 };
		Polygon diamond = gaService.createPolygon(containerShape, xy);
		diamond.setStyle(StyleUtil.getStyleForClass(getDiagram()));

		AdaptedGradientColoredAreas gradient = PredefinedColoredAreas.getBlueWhiteAdaptions();
		gaService.setRenderingStyle(diamond, gradient);

		gaService.setLocationAndSize(diamond, context.getX(), context.getY(), 2 * RADIUS, 2 * RADIUS);
		
		Ellipse ellipse = gaService.createEllipse(diamond);
		ellipse.setFilled(false);
		ellipse.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
		ellipse.setLineWidth(2);
		
		gaService.setLocationAndSize(ellipse, 12, 12, 27, 27);
		
		if (addedGateway.eResource() == null) {
			getDiagram().eResource().getContents().add(addedGateway);
		}

		link(containerShape, addedGateway);

		peCreateService.createChopboxAnchor(containerShape);
		return containerShape;
    }

}
